import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;

public class CategoryDatabase {

    private WebDriver driver;
    private WebDriverWait wait;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        // Set up WebDriver and database connection
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();

        // Set up the database connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mycart", "root", "root");
    }

    @Test
    public void testUserRegistrationLoginAndAddProduct() throws Exception {
        // Navigate to the login page
        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/admin.jsp");
        
       

        // Ensure the body has loaded before proceeding
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        // Click on "Add New Product" button
        WebElement addProductButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("AddnewProduct")));
        addProductButton.click();

        // Wait for the product form modal to be visible
        WebElement productModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("productModal")));

        // Fill out the product details form
        WebElement productNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("pName")));
        productNameInput.sendKeys("New Product");

        WebElement productDescInput = driver.findElement(By.name("pDesc"));
        productDescInput.sendKeys("Description of the new product");

        WebElement productPriceInput = driver.findElement(By.name("pPrice"));
        productPriceInput.sendKeys("99.99");

        WebElement productDiscountInput = driver.findElement(By.name("pDiscount"));
        productDiscountInput.sendKeys("10");

        // Select a category from the dropdown
        WebElement categoryDropdown = driver.findElement(By.name("categoryId"));
        Select selectCategory = new Select(categoryDropdown);
        selectCategory.selectByVisibleText("Toys");

        WebElement productQuantityInput = driver.findElement(By.name("pQuantity"));
        productQuantityInput.sendKeys("100");

        // Submit the form
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Add Product']"));
        submitButton.click();

       

        // Verify product in the database
        String query = "SELECT * FROM product WHERE p_name = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "New Product");
        ResultSet resultSet = statement.executeQuery();

        // Assert that the product is found in the database
        if (resultSet.next()) {
            Assert.assertEquals("New Product", resultSet.getString("p_name"));
            Assert.assertEquals("Description of the new product", resultSet.getString("p_description"));
            Assert.assertEquals("99.99", resultSet.getString("p_price"));
            Assert.assertEquals("10", resultSet.getString("p_discount"));
            Assert.assertEquals("100", resultSet.getString("p_quantity"));
        } else {
            Assert.fail("Product not found in the database.");
        }
    }

    @After
    public void tearDown() throws Exception {
        // Close the browser and database connection
        if (driver != null) {
            driver.quit();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
