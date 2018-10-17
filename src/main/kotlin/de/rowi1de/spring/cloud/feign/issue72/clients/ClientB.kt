package de.rowi1de.spring.cloud.feign.issue72.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "b", url = "localhost:8080")
interface ClientB {

    @GetMapping("/b")
    fun someMethod(): String
}