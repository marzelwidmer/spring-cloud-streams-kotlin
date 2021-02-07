package ch.keepcalm.demo.processor

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import java.util.function.Function
import kotlin.random.Random

@SpringBootApplication
class ProcessorApplication

fun main(args: Array<String>) {
    runApplication<ProcessorApplication>(*args)
}

@Configuration
class CoffeeRoaster {
    val rnd = Random

    // Convert WholesaleCoffee to RetailCoffee
    @Bean
    fun processIt(): Function<Flux<WholesaleCoffee>, Flux<RetailCoffee>> = Function {
        it.map {
            val rCoffee = RetailCoffee(
                it.id,
                it.name,
                if (rnd.nextInt(2) == 0)
                    RetailCoffee.State.WHOLE_BEAN
                else
                    RetailCoffee.State.GROUND
            )
            println(rCoffee)
            rCoffee
        }
    }
}

data class RetailCoffee(val id: String, val name: String, val state: State) {
    enum class State {
        WHOLE_BEAN,
        GROUND
    }
}

data class WholesaleCoffee(val id: String, val name: String)
