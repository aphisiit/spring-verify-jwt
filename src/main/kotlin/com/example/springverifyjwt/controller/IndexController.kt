package com.example.springverifyjwt.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {

    @GetMapping
    fun index(@RequestParam(value = "name", defaultValue = "World", required = false) name: String): String {
        return "Hello $name"
    }
}