package de.rowi1de.spring.cloud.feign.issue72.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

@FeignClient(name = "a", url = "localhost:8080", configuration = [AConfig::class])
interface ClientA {

    @GetMapping("/a")
    @Throws(AException::class)
    fun someMethod(): String
}