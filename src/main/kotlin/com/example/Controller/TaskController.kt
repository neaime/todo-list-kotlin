package com.example.Controller

import com.example.services.TaskService
import com.example.dtos.TaskDto
import io.micronaut.data.annotation.Id
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
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

    @Put("/{id}")
    fun alterTask(@Id id: @Valid Long, @Body newTask: TaskDto) {
        if (taskService.taskExist(id)) {
            taskService.updateTask(id, newTask)
        }
    }

    @Delete("/{id}")
    fun deleteTask(@Id id: Long) {
        if (taskService.taskExist(id)) {
            val task = taskService.findTaskById(id)
            taskService.deleteTask(task)
        }
    }
}