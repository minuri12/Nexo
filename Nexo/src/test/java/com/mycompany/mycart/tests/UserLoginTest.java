package com.mycompany.mycart.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserLoginTest extends BaseTest {

    @Test
    public void testUserLogin() {
        driver.get("http://localhost:8080/login");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));

        usernameField.sendKeys("user");
        passwordField.sendKeys("password");
        loginButton.click();

        // Validate successful login
        WebElement userProfile = driver.findElement(By.id("userProfile"));
        Assert.assertNotNull(userProfile);
    }
}
