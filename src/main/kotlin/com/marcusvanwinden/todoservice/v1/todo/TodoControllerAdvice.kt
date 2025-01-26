package com.marcusvanwinden.todoservice.v1.todo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TodoControllerAdvice {

    @ExceptionHandler
    fun handle(exception: TodoNotFoundException): ResponseEntity<String> =
        ResponseEntity
            .status(404)
            .body(exception.message)

}
