package com.marcusvanwinden.todoservice.v1.todo

import com.marcusvanwinden.todoservice.v1.todo.dto.request.CreateTodoRequestDto
import com.marcusvanwinden.todoservice.v1.todo.dto.request.UpdateTodoRequestDto
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class TodoService(private val todoRepository: TodoRepository) {

    fun createTodo(requestBody: CreateTodoRequestDto): Todo {
        val todo = todoRepository.save(
            Todo(
                title = requestBody.title,
            )
        )

        return todo
    }

    fun getTodos(): List<Todo> {
        val todos = todoRepository.findAll()

        return todos
    }

    fun updateTodo(id: UUID, requestBody: UpdateTodoRequestDto): Todo {
        val todo = todoRepository.findByIdOrNull(id) ?: throw TodoNotFoundException(id)
        val updatedTodo = todoRepository.save(
            todo.copy(
                title = requestBody.title ?: todo.title,
                completed = requestBody.completed ?: todo.completed,
            )
        )

        return updatedTodo
    }

    fun deleteTodo(id: UUID) {
        todoRepository.deleteById(id)
    }

}
