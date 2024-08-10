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

public class AdminView {

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

   // Step 2: Click on the "CATEGORIES" card
        WebElement categoriesCard = wait.until(ExpectedConditions.elementToBeClickable(By.id("categories")));
        categoriesCard.click();

   // Step 3: Verify that the categories.jsp page is loaded
        wait.until(ExpectedConditions.urlContains("categories.jsp"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("The categories.jsp page did not load successfully.", currentUrl.contains("categories.jsp"));

     

       
    }

    @After
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}


