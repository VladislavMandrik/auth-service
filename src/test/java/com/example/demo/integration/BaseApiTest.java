package com.example.demo.integration;

import com.example.demo.Constants;
import io.restassured.RestAssured;
import io.restassured.config.ConnectionConfig;
import io.restassured.config.HttpClientConfig;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest
public abstract class BaseApiTest {
    @Value("${admin.username}")
    protected String adminUsername;
    @Value("${admin.password}")
    protected String adminPassword;
    @Value("${user.username}")
    protected String userUsername;
    @Value("${user.password}")
    protected String userPassword;
    @Value("${user1.username}")
    protected String user1Username;
    @Value("${user1.password}")
    protected String user1Password;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = Constants.BASE_URL;
        RestAssured.filters(
//                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new ErrorLoggingFilter());

        RestAssured.config = RestAssured.config()
                .connectionConfig(ConnectionConfig.connectionConfig()
                        .closeIdleConnectionsAfterEachResponse())
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.conn-manager.max-per-route", 100)
                        .setParam("http.conn-manager.max-total", 400)
                        .setParam("http.connection.timeout", 5000)
                        .setParam("http.socket.timeout", 10000));

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    protected ValidatableResponse getAuthToken(String username, String password) {
        return given()
                .contentType(ContentType.JSON)
                .body("{\"username\": \"" + username + "\", " +
                        "\"password\": \"" + password + "\"}")
                .log().params()
                .when()
                .post(Constants.LOGIN_PAGE_ENDPOINT)
                .then()
                .statusCode(200);
    }

    @AfterEach
    void tearDown() {
        RestAssured.reset();
    }
}