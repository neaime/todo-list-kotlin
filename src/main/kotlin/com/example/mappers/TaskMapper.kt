package com.example.mappers

import com.example.domain.Task
import com.example.dtos.TaskDto
import javax.inject.Singleton

@Singleton
class TaskMapper {

    fun toDto(task: Task): TaskDto {
        return TaskDto(task.name, task.userName)
    }

    fun toEntity(taskDto: TaskDto): Task {
        return Task(taskDto.name!!, taskDto.username!!)
    }
}