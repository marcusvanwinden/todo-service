package com.marcusvanwinden.todoservice.v1.todo

import com.marcusvanwinden.todoservice.v1.todo.dto.request.CreateTodoRequestDto
import com.marcusvanwinden.todoservice.v1.todo.dto.request.UpdateTodoRequestDto
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import java.util.UUID
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
class TodoServiceTests {

    @MockK
    private lateinit var todoRepository: TodoRepository

    @InjectMockKs
    private lateinit var todoService: TodoService

    @Nested
    inner class CreateTodo {

        @Test
        fun `should create a todo`() {
            every { todoRepository.save(any()) } returns createTodo()

            val result = todoService.createTodo(CreateTodoRequestDto(title = "Do laundry"))

            assert(result.title == "Buy groceries")
        }

        @Test
        fun `should trim the title`() {
            every { todoRepository.save(any()) } returnsArgument 0

            val result = todoService.createTodo(CreateTodoRequestDto(title = "  Do laundry  "))

            assertEquals("Do laundry", result.title)
        }

    }

    @Nested
    inner class GetTodos {

        @Test
        fun `should get all todos`() {
            every { todoRepository.findAll() } returns listOf(
                createTodo(title = "Buy groceries"),
                createTodo(title = "Do laundry")
            )

            val result = todoService.getTodos()

            assert(result.size == 2)
            assert(result[0].title == "Buy groceries")
            assert(result[1].title == "Do laundry")
        }

    }

    @Nested
    inner class UpdateTodo {

        @Test
        fun `should update a todo`() {
            val todo = createTodo(completed = false)
            every { todoRepository.findByIdOrNull(any()) } returns todo
            every { todoRepository.save(any()) } returnsArgument 0

            val result = todoService.updateTodo(
                todo.id!!,
                UpdateTodoRequestDto(completed = true)
            )

            assert(result.completed)
        }

        @Test
        fun `should throw an exception when updating a non-existent todo`() {
            every { todoRepository.findByIdOrNull(any()) } returns null

            assertThrows<TodoNotFoundException> {
                todoService.updateTodo(
                    UUID.randomUUID(),
                    UpdateTodoRequestDto(completed = true)
                )
            }
        }

    }

    @Nested
    inner class DeleteTodo {

        @Test
        fun `should delete a todo`() {
            every { todoRepository.deleteById(any()) } returns Unit

            todoService.deleteTodo(createTodo().id!!)
        }

    }

}
