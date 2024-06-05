package com.ivanpatera.mendel

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {

    @GetMapping("/")
    fun home(): String {
        return "Hello from Spring Boot and Kotlin!"
    }
}