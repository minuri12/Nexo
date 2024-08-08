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
import java.time.Duration;

public class AdminProductAdd {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        // Automatically set up the correct ChromeDriver version
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }

    @Test
    public void testUserRegistrationLoginAndAddProduct() {
        // Navigate to the login page
        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/login.jsp");
        
        // Log in with the admin credentials
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail"))).sendKeys("admin@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("admin");
        driver.findElement(By.id("loginButton")).click();

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
        
        // Ensure that options are available and select one
        selectCategory.selectByVisibleText("Toys");

        WebElement productQuantityInput = driver.findElement(By.name("pQuantity"));
        productQuantityInput.sendKeys("100");

        // Submit the form
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Add Product']"));
        submitButton.click();

        // Add assertions to verify successful addition (e.g., checking for a success message or product list update)
    }

    @After
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
