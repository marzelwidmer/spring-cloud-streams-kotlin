package ch.keepcalm.demo

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import reactor.core.publisher.Flux
import java.util.*
import java.util.function.Function


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
            it.reversed()
        }
    }

    @Bean
    fun reverseWitMessage() = Function<Message, String> { message ->
        val toReversed = message.content.reversed()
        log.info("Converting {} to reverse: {}", message.content, toReversed)
        toReversed
    }
}



