package ch.keepcalm.demo.consumer

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class ConsumerApplication

fun main(args: Array<String>) {
    runApplication<ConsumerApplication>(*args)
}

interface ReceiverSink {
    @Input("producerInput")
    fun receiverChannel (): SubscribableChannel
}

@Configuration
@EnableBinding(ReceiverSink::class)
class MessagingConfig

@Service
class ReceiverService {

    private val log = LoggerFactory.getLogger(javaClass)

    @StreamListener("producerInput")
    fun handleMessage(message: String){
        log.info("Receive Message from RabbitMQ: {}", message)
    }
}
