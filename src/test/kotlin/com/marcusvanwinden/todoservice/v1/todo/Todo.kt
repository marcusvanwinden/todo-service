package com.marcusvanwinden.todoservice.v1.todo

import java.util.UUID
import kotlin.random.Random

fun createTodo(
    id: UUID = UUID.randomUUID(),
    title: String = "Buy groceries",
    completed: Boolean = Random.nextBoolean()
): Todo = Todo(
    id = id,
    title = title,
    completed = completed
)
