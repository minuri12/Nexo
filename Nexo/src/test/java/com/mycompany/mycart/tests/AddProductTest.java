package com.mycompany.mycart.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AddProductTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up ChromeDriver
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver"); // Update this path
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Optional: run in headless mode
        driver = new ChromeDriver(options);

        // Open the e-commerce application
        driver.get("http://localhost:8080/Nexo"); // Update with your application URL
    }

    @Test
    public void testAddProduct() {
        // Log in as admin
        WebElement adminLoginButton = driver.findElement(By.id("adminLoginButton"));
        adminLoginButton.click();

        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("loginButton"));

        usernameField.sendKeys("admin");
        passwordField.sendKeys("adminpassword"); // Use appropriate admin credentials
        loginButton.click();

        // Navigate to add product page
        WebElement addProductButton = driver.findElement(By.id("addProductButton"));
        addProductButton.click();

        // Fill in product details
        WebElement productNameField = driver.findElement(By.id("productName"));
        WebElement productCategoryField = driver.findElement(By.id("productCategory"));
        WebElement productPriceField = driver.findElement(By.id("productPrice"));
        WebElement productDescriptionField = driver.findElement(By.id("productDescription"));
        WebElement saveProductButton = driver.findElement(By.id("saveProductButton"));

        productNameField.sendKeys("New Product");
        productCategoryField.sendKeys("Electronics");
        productPriceField.sendKeys("99.99");
        productDescriptionField.sendKeys("Description of the new product");
        saveProductButton.click();

        // Verify that the product was added
        WebElement successMessage = driver.findElement(By.id("successMessage"));
        Assert.assertEquals(successMessage.getText(), "Product added successfully");

        // Optionally, check the product list to confirm it appears
        WebElement productList = driver.findElement(By.id("productList"));
        Assert.assertTrue(productList.getText().contains("New Product"));
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
