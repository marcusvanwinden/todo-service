package com.marcusvanwinden.todoservice.v1.todo.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateTodoRequestDto(

    @field:NotBlank( message = "Title cannot be blank")
    val title: String

)
