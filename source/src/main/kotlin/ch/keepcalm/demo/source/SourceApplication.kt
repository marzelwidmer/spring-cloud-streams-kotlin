package ch.keepcalm.demo.source

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.beans
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.*
import java.util.function.Supplier
import kotlin.random.Random
import org.springframework.beans.factory.annotation.Qualifier

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.cloud.stream.messaging.Source
import org.springframework.http.HttpStatus
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@SpringBootApplication
class SourceApplication

fun main(args: Array<String>) {
    runApplication<SourceApplication>(*args) {
        addInitializers(
            beans {
                bean {
                    router {
                        "".nest {
                            val generator = ref<CoffeeGenerator>()
                            val sendCoffee = ref<StreamBridge>()
                            GET("/") {
                                val coffee = generator.generate()
                                sendCoffee.send("sendCoffee-out-0", coffee)
                                ok().body(BodyInserters.fromValue(coffee))
                            }
                        }
                    }
                }
            }
        )
    }
}

//@Configuration
//class CoffeeGrower(private val generator: CoffeeGenerator) {
//
//    @Bean
////    fun sendCoffee(): () -> Flux<WholesaleCoffee> = {
//    fun sendCoffee(): Supplier<Flux<WholesaleCoffee>> = Supplier {
//        Flux.interval(Duration.ofSeconds(1))
//            .onBackpressureDrop()
//            .map {
//                generator.generate()
//            }
//    }
//}

@Component
class CoffeeGenerator {
    val names = listOf("Baresso", "Merrild", "Peter Larsen", "Kaldi")
    val rnd = Random
    fun generate() = WholesaleCoffee(id = UUID.randomUUID().toString(), name = names[rnd.nextInt(names.size)])
}

data class WholesaleCoffee(val id: String, val name: String)
