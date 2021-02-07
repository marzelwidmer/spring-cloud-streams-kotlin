package ch.keepcalm.demo.source

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.time.Duration
import java.util.*
import java.util.function.Supplier
import kotlin.random.Random

@SpringBootApplication
class SourceApplication

fun main(args: Array<String>) {
    runApplication<SourceApplication>(*args)
}

@Configuration
class CoffeeGrower(private val generator: CoffeeGenerator) {
    @Bean
//    fun sendCoffee(): () -> Flux<WholesaleCoffee> = {
    fun sendCoffee(): Supplier <Flux<WholesaleCoffee>> = Supplier {
        Flux.interval(Duration.ofSeconds(1))
            .onBackpressureDrop()
            .map {
                generator.generate()
            }
    }
}

@Component
class CoffeeGenerator {
    val names = listOf("Baresso", "Merrild", "Peter Larsen", "Kaldi")
    val rnd = Random
    fun generate() = WholesaleCoffee(id = UUID.randomUUID().toString(), name = names[rnd.nextInt(names.size)])
}

data class WholesaleCoffee(val id: String, val name: String)
