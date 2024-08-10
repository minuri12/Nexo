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

public class UpdateCategoryDatabase {

    private WebDriver driver;
    private WebDriverWait wait;
    private Connection connection;

    @Before
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().clearDriverCache().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Set up the database connection
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mycart", "root", "root");
    }

    @Test
    public void testUpdateCategory() throws Exception {
        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/admin.jsp");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        WebElement categoriesCard = wait.until(ExpectedConditions.elementToBeClickable(By.id("categories")));
        categoriesCard.click();
        wait.until(ExpectedConditions.urlContains("categories.jsp"));

        WebElement updateButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Update')]")));
        updateButton.click();

        WebElement categoryModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("categoryModal")));
        WebElement categoryTitleInput = driver.findElement(By.id("catTitle"));
        categoryTitleInput.clear();
        categoryTitleInput.sendKeys("Updated Category Title");

        WebElement categoryDescInput = driver.findElement(By.id("catDesc"));
        categoryDescInput.clear();
        categoryDescInput.sendKeys("Updated Category Description");

        WebElement submitButton = driver.findElement(By.id("Updateca"));
        submitButton.click();

        wait.until(ExpectedConditions.urlContains("admin.jsp"));

        // Verify the category update in the database
        String query = "SELECT * FROM category WHERE category_title = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "Updated Category Title");
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Assert.assertEquals("Updated Category Title", resultSet.getString("category_title"));
            Assert.assertEquals("Updated Category Description", resultSet.getString("category_description"));
        } else {
            Assert.fail("Updated category not found in the database.");
        }
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
        if (connection != null) {
            connection.close();
        }
    }
}
