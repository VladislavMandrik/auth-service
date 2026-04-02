package com.example.demo.integration;

import com.example.demo.Constants;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Feature("Аутентификация и Авторизация")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("api")
public class AuthApiTests extends BaseApiTest {

    @Test
    @Order(1)
    @Story("Позитивный сценарий")
    @DisplayName("Успешное получение JWT токена")
    @Description("Проверка получения токена зарегистрированного пользователя")
    public void shouldSuccessfulGet() {
        getAuthToken(userUsername, userPassword)
                .body("token", notNullValue())
                .body("token", matchesPattern(Constants.JWT_TOKEN_PATTERN))
                .log().ifValidationFails();
    }

    @Test
    @Order(2)
    @Story("Позитивный сценарий")
    @DisplayName("Успешное получение списка зарегистрированных пользователей")
    @Description("Проверка получения списка для роли ADMIN")
    public void shouldSuccessfulGetUsers() {
        String token = getAuthToken(adminUsername, adminPassword)
                .extract()
                .response().jsonPath().getString("token");
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .log().params()
                .when()
                .get(Constants.USERS_PAGE_ENDPOINT)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("content", not(empty()))
                .body("content.id", everyItem(notNullValue()))
                .body("content.id", everyItem(greaterThan(0)))
                .body("content.username", everyItem(notNullValue()))
                .body("content.username", everyItem(not(emptyString())))
                .body("content.role", everyItem(notNullValue()))
                .log().ifValidationFails();
    }

    @Test
    @Order(3)
    @Story("Негативный сценарий")
    @DisplayName("Нет прав на получение списка зарегистрированных пользователей")
    @Description("Проверка получения списка для роли USER")
    public void shouldNotSuccessfulGetUsers() {
        String token = getAuthToken(userUsername, userPassword)
                .extract()
                .response().jsonPath().getString("token");
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .log().params()
                .when()
                .get(Constants.USERS_PAGE_ENDPOINT)
                .then()
                .statusCode(403)
                .log().ifValidationFails();
    }
}