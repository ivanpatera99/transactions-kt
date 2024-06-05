package com.ivanpatera.mendel

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MendelApplication

fun main(args: Array<String>) {
	runApplication<MendelApplication>(*args)
}
