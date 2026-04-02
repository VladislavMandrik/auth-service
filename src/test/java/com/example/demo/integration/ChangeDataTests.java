package com.example.demo.integration;

import com.example.demo.Constants;
import com.example.demo.exception.ExceptionMessage;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Feature("Изменение данных")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Tag("api")
public class ChangeDataTests extends BaseApiTest {

    @Test
    @Order(1)
    @Story("Позитивный сценарий")
    @DisplayName("Успешное изменение данных пользователя")
    @Description("Проверка поведения системы при изменении данных пользователя")
    public void shouldSuccessfulChangeData() {
        String token = getAuthToken(adminUsername, adminPassword)
                .extract()
                .response().jsonPath().getString("token");
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body("{\"password\": \"" + user1Password + "\", " +
                        "\"role\": \"" + "ROLE_USER" + "\"}")
                .when()
                .put(Constants.USER_ID4_PAGE_ENDPOINT)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", equalTo(4))
                .body("password", equalTo(user1Password))
                .body("role", equalTo("ROLE_USER"))
                .log().ifValidationFails();
    }

    @Test
    @Order(2)
    @Story("Негативный сценарий")
    @DisplayName("Нет прав на изменения данных полозователей, кроме своих")
    @Description("Проверка возможности изменения данных для роли USER")
    public void shouldNotSuccessfulChangeData() {
        String token = getAuthToken(userUsername, userPassword)
                .extract()
                .response().jsonPath().getString("token");
        given()
                .contentType(ContentType.JSON)
                .auth().oauth2(token)
                .body("{\"password\": \"" + "123" + "\", " +
                        "\"role\": \"" + "ROLE_USER" + "\"}")
                .when()
                .put(Constants.USER_ID4_PAGE_ENDPOINT)
                .then()
                .statusCode(403)
                .body("message", equalTo(ExceptionMessage.YOU_CAN_CHANGE_ONLY_INFORMATION_ABOUT_YOURSELF.toString()))
                .body("time", notNullValue())
                .body("time", not(emptyString()))
                .log().ifValidationFails();
    }
}