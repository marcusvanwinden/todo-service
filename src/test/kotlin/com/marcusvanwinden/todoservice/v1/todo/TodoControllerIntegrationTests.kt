package com.marcusvanwinden.todoservice.v1.todo

import com.marcusvanwinden.todoservice.v1.todo.dto.request.CreateTodoRequestDto
import com.marcusvanwinden.todoservice.v1.todo.dto.request.UpdateTodoRequestDto
import com.marcusvanwinden.todoservice.v1.todo.dto.response.TodoResponseDto
import io.github.cdimascio.dotenv.Dotenv
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class TodoControllerIntegrationTests {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var todoRepository: TodoRepository

    private lateinit var initialTodo: Todo

    companion object {
        @JvmStatic
        @BeforeAll
        fun beforeAll() {
            val dotenv = Dotenv.configure().load()

            PostgreSQLContainer(dotenv["POSTGRES_DOCKER_IMAGE"]).apply {
                withDatabaseName("test")
                withUsername("test")
                withPassword("test")
                start()
            }.also { System.setProperty("spring.datasource.url", it.jdbcUrl) }
        }
    }

    @BeforeEach
    fun beforeEach() {
        todoRepository.deleteAll()
        initialTodo = todoRepository.save(Todo(title = "Initial todo"))
    }

    @Nested
    inner class CreateTodo {

        @Test
        fun `should respond with the newly created todo and status 201`() {
            val response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(CreateTodoRequestDto(title = "Buy groceries"))
                .post("/v1/todos")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .body()
                .`as`(TodoResponseDto::class.java)

            assertThat(response)
                .isInstanceOf(TodoResponseDto::class.java)
                .hasFieldOrPropertyWithValue("title", "Buy groceries")
        }

        @ParameterizedTest
        @ValueSource(strings = ["", " ", "\t", "\n", "     \n     "])
        fun `should respond with status 400 when title is empty`(invalidTitle: String) {
            given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(CreateTodoRequestDto(invalidTitle))
                .post("/v1/todos")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body(equalTo("Title cannot be blank"))
        }

    }

    @Nested
    inner class GetTodos {

        @Test
        fun `should respond with a list and status 200`() {
            val response = given()
                .port(port)
                .get("/v1/todos")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .`as`(Array<TodoResponseDto>::class.java)

            assertThat(response)
                .isNotEmpty
                .allSatisfy { todo -> assertThat(todo).isInstanceOf(TodoResponseDto::class.java) }
        }

    }

    @Nested
    inner class UpdateTodo {

        @Test
        fun `should respond with the updated todo and status 200`() {
            val response = given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(UpdateTodoRequestDto(title = "Updated todo"))
                .patch("/v1/todos/${initialTodo.id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .body()
                .`as`(TodoResponseDto::class.java)

            assertThat(response)
                .isInstanceOf(TodoResponseDto::class.java)
                .hasFieldOrPropertyWithValue("title", "Updated todo")
        }

    }

    @Nested
    inner class DeleteTodo {

        @Test
        fun `should respond with status 204`() {
            given()
                .port(port)
                .delete("/v1/todos/${initialTodo.id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
        }

    }

}
