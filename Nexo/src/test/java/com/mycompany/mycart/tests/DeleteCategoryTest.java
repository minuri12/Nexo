package com.mycompany.mycart.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DeleteCategoryTest extends BaseTest {

    @Test
    public void testDeleteCategory() {
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

        WebElement deleteButton = driver.findElement(By.id("deleteCategoryButton"));
        deleteButton.click();

        // Validate category was deleted
        WebElement categoryList = driver.findElement(By.id("categoryList"));
        Assert.assertFalse(categoryList.getText().contains("Category To Delete"));
    }
}
