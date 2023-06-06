import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class LoginTest {

    public String url = "http://18.141.16.103:5000";
    static public WebDriver driver;
    String driverpath = "/usr/bin/chromedriver";

    @BeforeClass
    public void openLoginPage() {
        System.out.println("Launching the Web application");
        System.setProperty("webdriver.gecko.driver", driverpath);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.get(url);
        driver.findElement(By.linkText("Login")).sendKeys(Keys.ENTER);
    }

    @Test
    public void loginTest() throws InterruptedException {
        String expectedUrl = "http://18.141.16.103:5000/cars";
        driver.findElement(By.xpath("//input[@placeholder='Enter your email']")).sendKeys("beenish");
        driver.findElement(By.xpath("//input[@placeholder='Enter your password']")).sendKeys("12345");
        driver.findElement(By.xpath("//button[.='Sign In']")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);
    }

    @Test(dependsOnMethods = "loginTest")
    public void carCreationTest() throws InterruptedException {
        driver.get(url);
        String expectedResponse = "Car added";
        driver.findElement(By.linkText("Add Cars")).sendKeys(Keys.ENTER);
        driver.findElement(By.id("carName")).sendKeys("Test car");
        driver.findElement(By.id("model")).sendKeys("Test model");
        driver.findElement(By.id("price")).sendKeys("123456");
        driver.findElement(By.id("automatic")).sendKeys("Yes");
        driver.findElement(By.id("speed")).sendKeys("Full");
        driver.findElement(By.id("imgUrl")).sendKeys("https://imageio.forbes.com/specials-images/imageserve/5d35eacaf1176b0008974b54/2020-Chevrolet-Corvette-Stingray/0x0.jpg");
        driver.findElement(By.id("details")).sendKeys("This is a test car added by Selenium");
//        Thread.sleep(20000);
        driver.findElement(By.xpath("//button[text()='Add Car']")).sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        String actualResponse = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        Assert.assertEquals(actualResponse, expectedResponse);
        Thread.sleep(1000);
    }

    @Test(dependsOnMethods = "carCreationTest")
    public void bookingCreationTest() throws InterruptedException {
        driver.findElement(By.linkText("Cars")).sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        driver.findElement(By.linkText("Rent")).sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        driver.findElement(By.xpath("//input[@placeholder='First Name']")).sendKeys("Test First name");
        driver.findElement(By.xpath("//input[@placeholder='Last Name']")).sendKeys("Test Last name");
        driver.findElement(By.xpath("//input[@placeholder='Email']")).sendKeys("Test Email");
        driver.findElement(By.xpath("//input[@placeholder='Phone Number']")).sendKeys("000000000000");
        driver.findElement(By.xpath("//input[@placeholder='From']")).sendKeys("0406" + Keys.TAB + "2023");
        driver.findElement(By.xpath("//input[@placeholder='To']")).sendKeys("2506" + Keys.TAB + "2023");
        driver.findElement(By.xpath("//input[@placeholder='Address']")).sendKeys("Test address");
        driver.findElement(By.xpath("//textarea[@placeholder='Write Notes']")).sendKeys("Test notes");
        driver.findElement(By.xpath("//button[text()='Create']")).sendKeys(Keys.ENTER);
        String expectedResponse = "Booking has been added";
        Thread.sleep(1000);
        String actualResponse = driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();
        Assert.assertEquals(actualResponse, expectedResponse);
        Thread.sleep(1000);
    }

    @Test(dependsOnMethods = "bookingCreationTest")
    public void bookingDeletionTest() throws InterruptedException {
        driver.get(url);
        driver.findElement(By.linkText("Bookings")).sendKeys(Keys.ENTER);
        Thread.sleep(5000);
        List<WebElement> bookings = driver.findElements(By.xpath("//span[contains(text(), 'Delete')]"));
        int totalBookings = bookings.size();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", bookings.get(0));
        Thread.sleep(1000);
        driver.switchTo().alert().accept();
        Thread.sleep(5000);
        int newBookings = driver.findElements(By.xpath("//span[text()='Delete']")).size();
        Assert.assertNotEquals(newBookings, totalBookings);
    }

    @AfterClass
    public void terminateSession() {
        driver.close();
    }

}
