package com.example.demo.integration;

import com.example.demo.Constants;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Feature("Регистрация")
@Tag("api")
public class RegistrationApiTest extends BaseApiTest {

    @Test
    @Story("Негативный сценарий")
    @DisplayName("Регистрация с отправкой неверных заголовков")
    @Description("Проверка поведения системы при отсутствии правильно указанного Content-Type")
    public void shouldReturnUnsupportedMediaTypeWhenContentTypeIsInvalid() {
        given()
                .body("{\"username\": \"" + "123" + "\", " +
                        "\"password\": \"" + "123" + "\", " +
                        "\"role\": \"" + "ROLE_USER" + "\"}")
                .log().params()
                .when()
                .post(Constants.REGISTRATION_PAGE_ENDPOINT)
                .then()
                .statusCode(415)
                .contentType(ContentType.JSON)
                .body("timestamp", notNullValue())
                .body("status", equalTo(415))
                .body("error", equalTo("Unsupported Media Type"))
                .body("requestId", notNullValue())
                .log().ifValidationFails();
    }
}