package com.marcusvanwinden.todoservice.v1.todo.dto.request

data class UpdateTodoRequestDto(

    val title: String? = null,

    val completed: Boolean? = null,
)
