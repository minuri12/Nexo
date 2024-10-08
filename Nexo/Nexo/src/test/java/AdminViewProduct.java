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

public class AdminViewProduct {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testUserRegistrationLoginAndPurchase() {
        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/login.jsp");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail"))).sendKeys("admin@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("admin");
        driver.findElement(By.id("loginButton")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        WebElement productCard = wait.until(ExpectedConditions.elementToBeClickable(By.id("Products")));
        productCard.click();

        wait.until(ExpectedConditions.urlContains("products.jsp"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("The products.jsp page did not load successfully.", currentUrl.contains("products.jsp"));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
