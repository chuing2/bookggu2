package com.bada.west

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class West01Application

fun main(args: Array<String>) {
    runApplication<West01Application>(*args)
}
