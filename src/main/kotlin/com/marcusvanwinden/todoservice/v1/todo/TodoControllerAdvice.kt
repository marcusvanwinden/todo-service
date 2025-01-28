package com.marcusvanwinden.todoservice.v1.todo

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TodoControllerAdvice {

    @ExceptionHandler
    fun handle(exception: TodoNotFoundException): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.message)

    @ExceptionHandler
    fun handle(exception: MethodArgumentNotValidException): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(exception.bindingResult.fieldErrors.first().defaultMessage ?: "Validation error")

    @ExceptionHandler
    fun handle(exception: HttpMessageNotReadableException): ResponseEntity<String> =
        ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body("Invalid request body")

}
