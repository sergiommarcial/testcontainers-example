package com.smar.examples.testcontainers.controller

import com.smar.examples.testcontainers.domain.ExampleObject
import com.smar.examples.testcontainers.service.ExampleService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@Suppress("TooGenericExceptionCaught")
@RestController
class ExampleController(
    private val exampleService: ExampleService,
) {

    @PostMapping("create")
    fun create(
        @RequestBody exampleObject: ExampleObject,
    ): ResponseEntity<Any> =
        try {
            logger.info("Requesting create example")
            ResponseEntity.ok().body(exampleService.create(exampleObject))
        } catch (e: Exception) {
            logger.error("Error requesting example creation", e)
            ResponseEntity.internalServerError().build()
        }
}

private val logger: Logger = LoggerFactory.getLogger(ExampleController::class.java)
