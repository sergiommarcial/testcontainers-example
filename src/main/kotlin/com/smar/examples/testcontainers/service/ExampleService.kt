package com.smar.examples.testcontainers.service

import com.smar.examples.testcontainers.domain.ExampleObject
import com.smar.examples.testcontainers.exception.ExampleServiceException
import com.smar.examples.testcontainers.respository.ExampleRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Suppress("TooGenericExceptionCaught")
@Service
class ExampleService(
    private val exampleRepository: ExampleRepository,
) {

    fun create(example: ExampleObject): ExampleObject =
        try {
            logger.info("Creating example")
            exampleRepository.save(example)
        } catch (e: Exception) {
            logger.error("Error saving example {}", example, e)
            throw ExampleServiceException("Error saving example", e)
        }
}

private val logger: Logger = LoggerFactory.getLogger(ExampleService::class.java)
