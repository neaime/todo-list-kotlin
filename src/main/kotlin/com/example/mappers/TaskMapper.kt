package com.example.mappers

import com.example.domain.Task
import com.example.dtos.TaskDto
import java.util.Optional
import javax.inject.Singleton

@Singleton
class TaskMapper {

    fun toDto(task: Task): TaskDto {
        return TaskDto(task.id, task.name, task.userName)
    }

    fun toEntity(taskDto: TaskDto): Task {
        return Task(taskDto.id, taskDto.name!!, taskDto.username!!)
    }
}