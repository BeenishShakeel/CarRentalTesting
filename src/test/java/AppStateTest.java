import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppStateTest {

    public String url = "http://18.141.16.103:5000";
    public WebDriver driver;
    String driverpath = "/usr/bin/chromedriver";

    @BeforeTest
    public void LaunchBrowser() {
        System.out.println("Launching the Web application");
        System.setProperty("webdriver.gecko.driver", driverpath);
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage"); //to disable limit
        driver = new ChromeDriver(options);
        driver.get(url);
    }

    @Test
    public void verifyAppWorking() {
        String expectedUrl = "http://18.141.16.103:5000/home";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl);
    }

    @AfterTest
    public void closeBrowser() {
        driver.close();
    }

}
