package com.example.Controller

import com.example.services.TaskService
import com.example.dtos.TaskDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import javax.inject.Inject
import javax.validation.Valid

@Controller("/tasks")
open class TaskController {
    @Inject
    lateinit var taskService: TaskService


    @Get("/all")
    fun all(): List<TaskDto> {
        return taskService.findAll()
    }

    @Post
    fun saveTask(@Body taskDto: @Valid TaskDto): HttpResponse<TaskDto> {
        return if (
            taskService.findByName(taskDto.name!!) != null
        ) {
            HttpResponse.badRequest()
        } else
            taskService.createTask(taskDto)
                .let { task -> HttpResponse.created(taskDto) }
                ?: HttpResponse.badRequest()
    }
}