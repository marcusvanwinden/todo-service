package com.marcusvanwinden.todoservice.v1.todo

import com.marcusvanwinden.todoservice.v1.todo.dto.request.CreateTodoRequestDto
import com.marcusvanwinden.todoservice.v1.todo.dto.request.UpdateTodoRequestDto
import com.marcusvanwinden.todoservice.v1.todo.dto.response.TodoResponseDto
import com.marcusvanwinden.todoservice.v1.todo.dto.response.toResponseDto
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/todos")
class TodoController(private val todoService: TodoService) {

    @PostMapping
    fun createTodo(@RequestBody requestBody: CreateTodoRequestDto): ResponseEntity<TodoResponseDto> {
        val todo = todoService.createTodo(requestBody)

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(todo.toResponseDto())
    }

    @GetMapping
    fun getTodos(): ResponseEntity<List<TodoResponseDto>> {
        val todos = todoService.getTodos()

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(todos.map { it.toResponseDto() })
    }

    @PatchMapping("/{id}")
    fun updateTodo(
        @PathVariable id: UUID,
        @RequestBody requestBody: UpdateTodoRequestDto
    ): ResponseEntity<TodoResponseDto> {
        val updatedTodo = todoService.updateTodo(id, requestBody)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(updatedTodo.toResponseDto())
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: UUID): ResponseEntity<Unit> {
        todoService.deleteTodo(id)

        return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .build()
    }

}
