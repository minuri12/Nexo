package com.mycompany.mycart.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddCategoryTest extends BaseTest {

    @Test
    public void testAddCategory() {
        driver.get("http://localhost:8080/admin-login");
        // Login as Admin
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));
        usernameField.sendKeys("admin");
        passwordField.sendKeys("adminpassword");
        loginButton.click();

        // Navigate to Category Page
        driver.get("http://localhost:8080/admin/categories");

        WebElement addCategoryButton = driver.findElement(By.id("addCategoryButton"));
        addCategoryButton.click();

        WebElement categoryNameField = driver.findElement(By.name("categoryName"));
        WebElement submitButton = driver.findElement(By.id("submitButton"));

        categoryNameField.sendKeys("New Category");
        submitButton.click();

        // Validate category was added
        WebElement categoryList = driver.findElement(By.id("categoryList"));
        Assert.assertTrue(categoryList.getText().contains("New Category"));
    }
}
