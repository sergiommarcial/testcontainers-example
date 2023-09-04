package com.smar.examples.testcontainers.controller

import com.smar.examples.testcontainers.App
import com.smar.examples.testcontainers.config.TestConfig
import com.smar.examples.testcontainers.domain.ExampleObject
import com.smar.examples.testcontainers.respository.ExampleRepository
import org.awaitility.kotlin.await
import org.awaitility.kotlin.untilAsserted
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.context.annotation.Import
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@Suppress("MagicNumber")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    classes = [App::class],
)
@Import(TestConfig::class)
class ExampleControllerItTest {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var exampleRepository: ExampleRepository

    @LocalServerPort
    private var port: Int = 0

    @ParameterizedTest(name = "Scenario {0}")
    @MethodSource("getDataScenario")
    fun test(
        scenarioName: String,
        exampleObject: ExampleObject,
        expectedHttpResponse: Int,
        expectedRecordsSaved: Int,
    ) {
        logger.info("Running scenario: {}", scenarioName)

        val testUrl = "http://localhost:$port/test-example/v1/create"

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val request: HttpEntity<ExampleObject> = HttpEntity<ExampleObject>(exampleObject, headers)

        val response: ResponseEntity<ExampleObject> =
            restTemplate.postForEntity(testUrl, request, ExampleObject::class.java)
        assertEquals(expectedHttpResponse, response.statusCode.value())

        await untilAsserted {
            assertEquals(expectedRecordsSaved, exampleRepository.findAll().toList().size)
        }
    }

    companion object {

        @JvmStatic
        fun getDataScenario() = listOf(
            Arguments.of(
                "Happy path",
                ExampleObject(
                    message = "test message",
                ),
                200,
                1,
            ),
        )
    }
}

private val logger: Logger = LoggerFactory.getLogger(ExampleController::class.java)
