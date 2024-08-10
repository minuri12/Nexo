import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

public class ProductDatabase {

    private WebDriver driver;
    private WebDriverWait wait;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        // Automatically set up the correct ChromeDriver version
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Set up the database connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mycart", "root", "root");
    }

    @Test
    public void testUserRegistrationLoginAndAddCategory() throws Exception {
        // Log in with the admin credentials
        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/admin.jsp");
 

        // Wait for the page to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        // Click on the "Add New Category" button
        WebElement addNewCategoryButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("AddnewCategory")));
        addNewCategoryButton.click();

        // Wait until the modal is visible
        WebElement categoryModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("staticBackdrop")));

        // Fill out the category form
        WebElement categoryTitleInput = driver.findElement(By.name("catTitle"));
        WebElement categoryDescriptionInput = driver.findElement(By.name("catDesc"));
        categoryTitleInput.sendKeys("Test Category");
        categoryDescriptionInput.sendKeys("This is a test category description.");

        // Submit the form
        WebElement addCategoryButton = driver.findElement(By.cssSelector("button[type='submit']"));
        addCategoryButton.click();

        // Verify the category in the database
        String query = "SELECT * FROM category WHERE category_title = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "Test Category");
        ResultSet resultSet = statement.executeQuery();

        // Assert that the category is found in the database
        if (resultSet.next()) {
            Assert.assertEquals("Test Category", resultSet.getString("category_title"));
            Assert.assertEquals("This is a test category description.", resultSet.getString("category_description"));
        } else {
            Assert.fail("Category not found in the database.");
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
