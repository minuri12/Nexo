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
import org.openqa.selenium.Alert;

public class DeleteproductDatabase {

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
    public void testDeleteProduct() throws Exception {
       
        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/admin.jsp");
  
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        // Navigate to the Products page
        WebElement productCard = wait.until(ExpectedConditions.elementToBeClickable(By.id("Products")));
        productCard.click();

        // Verify that the products.jsp page is loaded
        wait.until(ExpectedConditions.urlContains("products.jsp"));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue("The products.jsp page did not load successfully.", currentUrl.contains("products.jsp"));

        // Click on the delete button for a specific product
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("Removepro")));
        deleteButton.click();

        // Handle any confirmation alerts
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (Exception e) {
            System.out.println("No alert present for removal confirmation.");
        }

        // Verify the product is removed from the database
        String productId = "123"; // Replace with the actual product ID you are deleting
        String query = "SELECT * FROM product WHERE product_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, productId);
        ResultSet resultSet = statement.executeQuery();

        Assert.assertFalse("Product still exists in the database after deletion.", resultSet.next());
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
