import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;

public class RegistrationandDatabase{

    private WebDriver driver;
    private WebDriverWait wait;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Set up the database connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mycart", "root", "root");
    }

  @Test
public void testUserRegistrationAndDatabaseEntry() throws Exception {
    // Step 1: Register a new user
    driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/user_register.jsp");
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName"))).sendKeys("TestUser");
    driver.findElement(By.id("userEmail")).sendKeys("testuser@example.com");
    driver.findElement(By.id("userPassword")).sendKeys("password123");
    driver.findElement(By.id("userPhone")).sendKeys("1234567890");
    driver.findElement(By.id("userAddress")).sendKeys("123 Test Street");
    driver.findElement(By.id("registerButton")).click();

    // Step 2: Verify the user details in the database
    String query = "SELECT * FROM user WHERE user_email = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setString(1, "testuser@example.com");
    ResultSet resultSet = statement.executeQuery();

    // Assert that the user is found in the database
    if (resultSet.next()) {
        System.out.println("Retrieved Name: " + resultSet.getString("user_name"));
        System.out.println("Retrieved Password: " + resultSet.getString("user_password"));
        System.out.println("Retrieved Phone: " + resultSet.getString("user_phone"));
        System.out.println("Retrieved Address: " + resultSet.getString("user_address"));
        
        assertEquals("TestUser", resultSet.getString("user_name").trim());
        assertEquals("password123", resultSet.getString("user_password").trim());
        assertEquals("1234567890", resultSet.getString("user_phone").trim());
        assertEquals("123 Test Street", resultSet.getString("user_address").trim());
    } else {
        throw new AssertionError("User not found in the database.");
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
