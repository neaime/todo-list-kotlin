package com.example

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank

@Introspected
data class TaskUpdateCommand(
    val id: Long,
    @field:NotBlank val name: String,
    val userName: String
)
