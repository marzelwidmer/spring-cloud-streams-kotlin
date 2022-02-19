package ch.keepcalm.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringCloudStreamsKotlinApplication

fun main(args: Array<String>) {
	runApplication<SpringCloudStreamsKotlinApplication>(*args)
}
