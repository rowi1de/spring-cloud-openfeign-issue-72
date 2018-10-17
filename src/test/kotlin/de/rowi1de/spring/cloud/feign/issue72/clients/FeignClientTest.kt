package de.rowi1de.spring.cloud.feign.issue72.clients

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import com.github.tomakehurst.wiremock.client.WireMock.exactly
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor
import com.github.tomakehurst.wiremock.client.WireMock.serverError
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import com.github.tomakehurst.wiremock.client.WireMock.verify
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig
import de.rowi1de.spring.cloud.feign.issue72.Application
import feign.FeignException
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    classes = [Application::class,
        ClientA::class,
        ClientB::class]
)
class FeignClientTest {

    private lateinit var wireMockServer: WireMockServer

    @Autowired
    private lateinit var clientA: ClientA

    @Autowired
    private lateinit var clientB: ClientB

    @BeforeEach
    private fun initWireMock() {
        wireMockServer = WireMockServer(wireMockConfig().port(8080))
        wireMockServer.start()
        configureFor("localhost", 8080)
        stubFor(get(urlMatching("/.*")).willReturn(serverError()))
    }

    @AfterEach
    private fun stopWireMock() {
        wireMockServer.stop()
    }

    @Test
    fun `Test Client A 500 throws AException`() {
        Assertions.assertThrows(
            AException::class.java
        ) {
            clientA.someMethod()
        }
        verify(exactly(1), getRequestedFor(urlEqualTo("/a")))
    }

    @Test
    fun `Test Client B 500 throws No AException`() {
        Assertions.assertThrows(
            FeignException::class.java
        ) {
            clientB.someMethod()
        }
        verify(exactly(1), getRequestedFor(urlEqualTo("/b")))
    }
}