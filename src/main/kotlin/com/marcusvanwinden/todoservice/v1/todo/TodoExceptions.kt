package com.marcusvanwinden.todoservice.v1.todo

import java.util.UUID

class TodoNotFoundException(id: UUID) : RuntimeException("Could not find todo with id: $id")
