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

public class AdminTest {

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
      
        // Log in with the newly registered user credentials
        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/login.jsp");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail"))).sendKeys("admin@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("admin");
        driver.findElement(By.id("loginButton")).click();

       wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

// Click on the "Add New Category" button
        WebElement addNewCategoryButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("AddnewCategory")));
        addNewCategoryButton.click();

// Wait until the modal is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("staticBackdrop")));

 // Fill out the category form
        WebElement categoryTitleInput = driver.findElement(By.name("catTitle"));
        WebElement categoryDescriptionInput = driver.findElement(By.name("catDesc"));


        categoryTitleInput.sendKeys("Test Category");
        categoryDescriptionInput.sendKeys("This is a test category description.");


        // Submit the form
        WebElement addCategoryButton = driver.findElement(By.cssSelector("button[type='submit']"));
        addCategoryButton.click();




       
    }

    @After
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}


