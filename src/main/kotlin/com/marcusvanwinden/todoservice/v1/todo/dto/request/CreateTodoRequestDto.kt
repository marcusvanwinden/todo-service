package com.marcusvanwinden.todoservice.v1.todo.dto.request

import org.jetbrains.annotations.NotNull

data class CreateTodoRequestDto(

    @field:NotNull
    val title: String

)
