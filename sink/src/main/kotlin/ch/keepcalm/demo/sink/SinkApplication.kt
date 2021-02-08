package ch.keepcalm.demo.sink

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import java.util.function.Consumer

@SpringBootApplication
class SinkApplication

fun main(args: Array<String>) {
	runApplication<SinkApplication>(*args)
}

@Configuration
class CoffeeDrinker {
	@Bean
	fun drinkIt() : Consumer<Flux<RetailCoffee>> = Consumer {
		it.subscribe() {
			println(it)
		}
	}
}

data class RetailCoffee(val id: String, val name: String, val state: State) {
	enum class State {
		WHOLE_BEAN,
		GROUND
	}
}
