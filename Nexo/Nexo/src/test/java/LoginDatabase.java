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

public class LoginDatabase{

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
public void testUserLoginAndDatabaseEntry() throws Exception {
    // Step 1: Register a new user
        // Log in with the newly registered user credentials
        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/login.jsp");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail"))).sendKeys("testuser@example.com");
        driver.findElement(By.id("userPassword")).sendKeys("password123");
        driver.findElement(By.id("loginButton")).click();

    // Step 2: Verify the user details in the database
    String query = "SELECT * FROM user WHERE user_email = ?";
    PreparedStatement statement = connection.prepareStatement(query);
    statement.setString(1, "testuser@example.com");
    ResultSet resultSet = statement.executeQuery();

    // Assert that the user is found in the database
    if (resultSet.next()) {
       
        System.out.println("Retrieved Password: " + resultSet.getString("user_password"));
       
        
        assertEquals("TestUser", resultSet.getString("user_name").trim());
        assertEquals("password123", resultSet.getString("user_password").trim());
      
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
