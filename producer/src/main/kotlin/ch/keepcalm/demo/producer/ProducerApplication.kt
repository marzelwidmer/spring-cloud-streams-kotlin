package ch.keepcalm.demo.producer


import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Output
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ProducerApplication

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
    }
}

@Service
class ProducerMessagingService(private val producerSource: ProducerSource) {
    fun sendMessage(message: String) = producerSource.producerChannel().send(MessageBuilder.withPayload(message).build())
}

interface ProducerSource {
    @Output(value = "producerOutput")
    fun producerChannel(): MessageChannel
}
@Configuration
@EnableBinding(value = [ProducerSource::class])
class MessagingConfig
