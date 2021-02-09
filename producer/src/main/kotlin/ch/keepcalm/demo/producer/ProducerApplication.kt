package ch.keepcalm.demo.producer

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Output
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicBoolean
import java.util.function.Consumer
import java.util.function.Supplier
import reactor.core.publisher.Flux
import java.time.Duration


@SpringBootApplication
class ProducerApplication {
//    private val log = LoggerFactory.getLogger(javaClass)
//    private val semaphore = AtomicBoolean(true)
//    @Bean
//    fun testSource(): Supplier<String>? {
//        return Supplier { if (semaphore.getAndSet(!semaphore.get())) "foo" else "bar" }
//    }
}

fun main(args: Array<String>) {
    runApplication<ProducerApplication>(*args)
}

@RestController
@RequestMapping(value = ["/producer"])
class ProducerController(private val producerMessagingService: ProducerMessagingService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping(value = ["{message}"])
    fun message(@PathVariable message: String) {
        log.info("------------> Receive message {} <-------------", message)
        producerMessagingService.sendMessage(message)
//        producerMessagingService.supplier()
    }
}

@Service
open class ProducerMessagingService(private val producerSource: ProducerSource) {

//    @Bean
//    fun uppercase(): Function<String?, String?>? {
//        return Function<String, String> { value -> value.toUpperCase() }
//    }

    @Bean
    fun kotlinConsumer(): (String) -> Unit {
        return {
            println(it.toUpperCase())
        }
    }

    @Bean
    fun kotlinConsumer2(): Consumer<String> = Consumer {
        println("hello :${it}")
    }

    @Bean
    fun supplier(): () -> String {
        return  { "Hello Kotlin" }
    }


    @Bean
    fun transform(): (String) -> String {
        return { "How are you ".plus(it) }
    }

    @Bean
    fun toUpperCase(): (String) -> String {
        return { it.toUpperCase() }
    }

//    @Bean
//    fun toUpperCase(message: String): Supplier<String>? {
//        return Supplier { message}
//    }

//    private val semaphore = AtomicBoolean(true)
//
//    @Bean
//    fun testSource(message: String ): Supplier<String>? {
//        return Supplier {
//            println(message)
//            message
//        }
//    }


    fun sendMessage(message: String) = producerSource.producerChannel().send(MessageBuilder.withPayload(message).build())


}

interface ProducerSource {
    @Output(value = "producerOutput")
    fun producerChannel(): MessageChannel
}
@Configuration
@EnableBinding(value = [ProducerSource::class])
class MessagingConfig
