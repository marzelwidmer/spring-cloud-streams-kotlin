package ch.keepcalm.demo

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import java.util.*
import java.util.function.Function
import java.util.function.Supplier


@Configuration
class MyFunctions {

    private val log = LoggerFactory.getLogger(javaClass)

    @Bean
    fun uppercase(): Function<String, String> = Function<String, String> { message ->
        val toUppercase: String = message.uppercase(Locale.getDefault())
        log.info("Converting {} to uppercase: {}", message, toUppercase)
        toUppercase
    }

    @Bean
    fun reverse(): Function<String, String> {
        return Function<String, String> { message ->
            val toReversed = message.reversed()
            log.info("Converting {} to reverse: {}", message, toReversed)
            toReversed
        }
    }

    @Bean
    fun reverseReactive(): Function<Flux<String>, Flux<String>> = Function<Flux<String>, Flux<String>> { flux ->
        flux.map {
            log.info("-----------------------------> reverseReactive : ---> : {}", it)
            it.reversed()
        }
    }

    @Bean
    fun reverseWitMessage() = Function<Message, String> { message ->
        val toReversed = message.content.reversed()
        log.info("Converting {} to reverse: {}", message.content, toReversed)
        toReversed
    }

    @Bean
    fun supplyLoan(): Supplier<Loan> {
        return Supplier<Loan> {
            val loan = Loan(UUID.randomUUID().toString(), "Ben", amount = 10000L)
            log.info("{} for \${} for {}", loan.uuid, loan.amount, loan.name)
            loan
        }
    }

    @Bean
    fun echoString(): Function<String, String> = Function<String, String> { value ->
        log.info("-----------------------------> ECHO String : ---> : {}", value)
        value
    }

    @Bean
    fun echoFlux(): Function<Flux<String>, Flux<String>> = Function<Flux<String>, Flux<String>> { flux ->
        flux.map { value ->
            log.info("-----------------------------> ECHO Flux: ---> : {}", value)
        }
        flux
    }



    @Bean
    fun echoPerson(): Function<Person, String> = Function<Person, String> { value ->
        log.info("-----------------------------> ECHO : ---> : {}", value.name)
        value.name
    }
}

data class Loan(val uuid: String, val name: String, val amount: Long)
data class Person(val name: String?)
