package de.rowi1de.spring.cloud.feign.issue72.clients

import feign.Response
import feign.Util
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

class AConfig {
    @Bean
    fun aErrorDecoder(): AErrorDecoder = AErrorDecoder()

    inner class AErrorDecoder : ErrorDecoder {
        private val defaultErrorDecoder = ErrorDecoder.Default()
        override fun decode(methodKey: String?, response: Response?): Exception {
            if (HttpStatus.OK.value() != response?.status()) {
                val body = Util.toString(response!!.body().asReader())
                return AException(body)
            }
            return defaultErrorDecoder.decode(methodKey, response)
        }
    }
}
