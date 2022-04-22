package com.example.services

import com.example.domain.Task
import com.example.dtos.TaskDto
import com.example.mappers.TaskMapper
import com.example.repositories.TaskRepository
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

    fun createTask(task: TaskDto): Task {
        return taskRepository.save(taskMapper.toEntity(task))
    }
}