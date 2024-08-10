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
import org.openqa.selenium.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.Duration;

public class RemoveCategoryDatabase {

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
    public void testRemoveCategory() throws Exception {
        // Log in as admin
        driver.get("http://localhost:8080/Nexo/Web%20Pages/index.html/login.jsp");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userEmail"))).sendKeys("admin@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("admin");
        driver.findElement(By.id("loginButton")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("body")));

        // Navigate to the categories page
        WebElement categoriesCard = wait.until(ExpectedConditions.elementToBeClickable(By.id("categories")));
        categoriesCard.click();
        wait.until(ExpectedConditions.urlContains("categories.jsp"));

        // Locate and click the remove button for a specific category
        WebElement removeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Remove')]")));
        removeButton.click();

        // Handle any confirmation dialog
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (Exception e) {
            System.out.println("No alert present for removal confirmation.");
        }

        // Verify the category removal in the database
        String query = "SELECT * FROM category WHERE category_title = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "Category Title to Remove"); // Replace with actual category title
        ResultSet resultSet = statement.executeQuery();

        Assert.assertFalse("Category was not removed from the database.", resultSet.next());
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
