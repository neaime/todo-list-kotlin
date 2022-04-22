package com.example.dtos

import io.micronaut.core.annotation.Introspected

@Introspected
class TaskDto(val name: String?, val username: String?) {
    constructor() : this( null, null)
}