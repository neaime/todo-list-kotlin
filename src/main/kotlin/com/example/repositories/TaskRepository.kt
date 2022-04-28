package com.example.repositories

import com.example.domain.Task
import io.micronaut.context.annotation.Executable
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository
import java.util.*


@JdbcRepository(dialect = Dialect.MYSQL)
interface TaskRepository : CrudRepository<Task, Long> {
    @Executable
    fun findByUserName(userName: String): Task?
    fun findByName(name: String?): Task?

    fun getTaskById(id: Long): Task
}