package com.smar.examples.testcontainers.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import java.util.*

@Entity(name = "example")
data class ExampleObject(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "example_seq")
    @SequenceGenerator(name = "example_seq", sequenceName = "example_seq", allocationSize = 1)
    val id: Long? = null,
    val message: String,
    val createdTimestamp: Date? = null,
)
