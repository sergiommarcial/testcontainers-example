package com.smar.examples.testcontainers.config

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import java.time.Duration

@TestConfiguration(proxyBeanMethods = false)
class TestConfig {

    @Bean
    fun restTemplate() = RestTemplate()

    @Bean
    fun objectMapper() = jacksonObjectMapper()

    @Bean
    @ServiceConnection
    fun postgresSqlContainer(): PostgreSQLContainer<*> =
        PostgreSQLContainer(
            DockerImageName.parse("postgres:latest"),
        ).also {
            it.setWaitStrategy(
                Wait.defaultWaitStrategy()
                    .withStartupTimeout(Duration.ofSeconds(30)),
            )
        }.run {
            this.start()
            this
        }
}
