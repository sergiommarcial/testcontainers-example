package com.smar.examples.testcontainers.respository

import com.smar.examples.testcontainers.domain.ExampleObject
import org.springframework.data.repository.CrudRepository

interface ExampleRepository : CrudRepository<ExampleObject, Long>
