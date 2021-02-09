package ch.keepcalm.demo.producer


import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.function.StreamBridge
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.beans
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.function.Consumer

@SpringBootApplication
class ProducerApplication

fun main(args: Array<String>) {
    runApplication<ProducerApplication>(*args){
        addInitializers(

        )
    }
}

@RestController
@RequestMapping(value = ["/producer"])
class ProducerController(private val producerMessagingService: ProducerMessagingService) {

    private val log = LoggerFactory.getLogger(javaClass)

    @PostMapping(value = ["{message}"])
    fun message(@PathVariable message: String) {
        log.info("------------> Receive message {} <-------------", message)
        producerMessagingService.sendMessage(message)
        producerMessagingService.kotlinConsumer().accept(message)
    }
}

@Service
class ProducerMessagingService(private val producerSource: ProducerSource, private val streamBridge: StreamBridge) {
    fun sendMessage(message: String) = producerSource.producerChannel().send(MessageBuilder.withPayload(message).build())

    @Bean
    fun kotlinConsumer(): Consumer<String> = Consumer {
        val message = it.toUpperCase()
        println(message)
        streamBridge.send("producerOutput", MessageBuilder.withPayload(message).build())
    }
}

interface ProducerSource {
    @Output(value = "producerOutput")
    fun producerChannel(): MessageChannel
}
@Configuration
@EnableBinding(value = [ProducerSource::class])
class MessagingConfig
