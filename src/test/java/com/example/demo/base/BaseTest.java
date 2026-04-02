package com.example.demo.base;

import com.example.demo.Constants;
import com.example.demo.manager.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = DriverManager.getDriver();
        wait = DriverManager.getWait();
        driver.get(Constants.BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        DriverManager.quitDriver();
    }
}