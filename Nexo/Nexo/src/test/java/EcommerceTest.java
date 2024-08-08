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
import java.time.Duration;
import org.openqa.selenium.Alert;

public class EcommerceTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        // Automatically set up the correct ChromeDriver version
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testUserRegistrationLoginAndPurchase() {
        // Open the registration page
        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/user_register.jsp");

        // Fill in user details
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userName"))).sendKeys("Test User");
        driver.findElement(By.id("userEmail")).sendKeys("testuser@example.com");
        driver.findElement(By.id("userPassword")).sendKeys("password123");
        driver.findElement(By.id("userPhone")).sendKeys("1234567890");
        driver.findElement(By.id("userAddress")).sendKeys("123 Test Street");
        driver.findElement(By.id("registerButton")).click();

        // Log in with the newly registered user credentials
        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/login.jsp");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail"))).sendKeys("testuser@example.com");
        driver.findElement(By.id("userPassword")).sendKeys("password123");
        driver.findElement(By.id("loginButton")).click();



        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addItemButton"))).click();


          // Handle the alert after adding an item to the cart
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (Exception e) {
            System.out.println("No alert present");
        }


        // Click on the cart icon to open the cart modal
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@src='img/shopping-cart_1.png']"))).click();

       
       // Wait for the cart modal to be displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cartmodel")));

        // Proceed to checkout
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkoutbtn"))).click();


       

    }

    @After
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}


