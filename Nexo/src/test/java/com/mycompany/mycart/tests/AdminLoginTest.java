package com.mycompany.mycart.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdminLoginTest extends BaseTest {

    @Test
    public void testAdminLogin() {
        driver.get("http://localhost:8080/admin-login");

        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));

        usernameField.sendKeys("admin");
        passwordField.sendKeys("adminpassword");
        loginButton.click();

        // Validate successful login
        WebElement adminDashboard = driver.findElement(By.id("adminDashboard"));
        Assert.assertNotNull(adminDashboard);
    }
}
