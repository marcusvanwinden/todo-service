package com.marcusvanwinden.todoservice.v1.todo.dto.response

import com.marcusvanwinden.todoservice.v1.todo.Todo
import java.util.UUID

data class TodoResponseDto(

    val id: UUID,

    val title: String,

    val completed: Boolean,
)

fun Todo.toResponseDto() = TodoResponseDto(
    id = this.id!!,
    title = this.title,
    completed = this.completed,
)
