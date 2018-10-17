package de.rowi1de.spring.cloud.feign.issue72

import de.rowi1de.spring.cloud.feign.issue72.clients.ClientA
import de.rowi1de.spring.cloud.feign.issue72.clients.ClientB
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class Service(val clientA: ClientA, val clientB: ClientB) {

    var logger = LoggerFactory.getLogger(Service::class.java)

    @PostConstruct
    fun clientARequest() {
        try {
            clientA.someMethod()
        } catch (e: Exception) {
            logger.error("clientA", e)
        }
    }

    @PostConstruct
    fun clientBRequest() {
        try {
            clientB.someMethod()
        } catch (e: Exception) {
            logger.error("clientB", e)
        }
    }
}