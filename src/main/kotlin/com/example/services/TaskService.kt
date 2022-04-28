package com.example.services

import com.example.domain.Task
import com.example.dtos.TaskDto
import com.example.mappers.TaskMapper
import com.example.repositories.TaskRepository
import io.micronaut.http.HttpResponse
import java.util.*
import javax.inject.Inject

class TaskService {
    @Inject
    lateinit var taskRepository: TaskRepository
    @Inject
    lateinit var taskMapper: TaskMapper

    fun findAll(): List<TaskDto> {
        return taskRepository.findAll().map { task -> taskMapper.toDto(task) }
    }

    fun findByName(name: String): TaskDto? {
        return taskRepository.findByName(name)?.let { task -> taskMapper.toDto(task) }
    }

    fun taskExist(id: Long): Boolean {
        return taskRepository.existsById(id)
    }

    fun findTaskById(id: Long) : Task {
        return taskRepository.getTaskById(id)
    }

    fun createTask(task: TaskDto) : Task {
        return taskRepository.save(taskMapper.toEntity(task))
    }

    fun updateTask(id: Long, task: TaskDto) {
        task.let {
            val newTask = taskMapper.toEntity(task).copy(id = id, name = task.name, userName = task.username)
            taskRepository.update(newTask)
        }
    }

    fun deleteTask(task: Task) {
        taskRepository.delete(task)
    }
}