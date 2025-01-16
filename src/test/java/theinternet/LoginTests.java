package theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LoginTests {

    private WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod(alwaysRun = true)
    private void setUp(@Optional("chrome") String browser) {
        //Create driver
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
                driver = new FirefoxDriver();
                break;
            default:
                System.out.println("Do not know how to start " + browser + ", starting chrome instead");
                System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
                driver = new ChromeDriver();
                break;
        }
        //maximize driver window
        driver.manage().window().maximize();
        //implicitWait
        //a1
    }
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    //b1


    @Test(priority = 1, groups = {"positiveTests", "smokeTests"})
    public void positiveLoginTest() {
        System.out.println("Starting loginTest");
        //open test page
        String url = "https://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");
        //enter username
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");
        //enter password
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("SuperSecretPassword!");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        //click login button
        WebElement logInButton = driver.findElement(By.tagName("button"));
        wait.until(ExpectedConditions.elementToBeClickable(logInButton));
        logInButton.click();
        //verifications:
        //new url
        String expectedUrl = "https://the-internet.herokuapp.com/secure";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectedUrl, "Actual page url is not the same as expected");
        //logout button is visible
        WebElement logOutButton = driver.findElement(By.xpath("//a[@class='button secondary radius']"));
        Assert.assertTrue(logOutButton.isDisplayed(), "Log out button is not visible");
        //successful login message
        WebElement successMessage = driver.findElement(By.id("flash"));
        String expectedSuccessMessage = "You logged into a secure area!";
        String actualSuccessMessage = successMessage.getText();
        Assert.assertTrue
                (actualSuccessMessage.contains(expectedSuccessMessage),
                        "Actual message does not contains expected message." +
                                "\nActual message: " + actualSuccessMessage +
                                "\nExpected message: " + expectedSuccessMessage);
    }

    @Parameters({"username", "password", "expectedMessage"})
    @Test(priority = 2, groups = {"negativeTests", "smokeTests"})
    public void negativeLoginTest(String username, String password, String expectedMessage) {
        System.out.println("Starting negativeLoginTest with " + username + " and " + password);
        //open test page
        String url = "https://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");
        //enter username
        WebElement usernameElement = driver.findElement(By.id("username"));
        usernameElement.sendKeys(username);
        //enter password
        WebElement passwordElement = driver.findElement(By.name("password"));
        passwordElement.sendKeys(password);
        //click login button
        WebElement logInButton = driver.findElement(By.tagName("button"));
        logInButton.click();
        //verifications:
        //successful login message
        WebElement errorMessageElement = driver.findElement(By.id("flash"));
        String actualErrorMessage = errorMessageElement.getText();
        Assert.assertTrue
                (actualErrorMessage.contains(expectedMessage),
                        "Actual message does not contains expected message." +
                                "\nActual message: " + actualErrorMessage +
                                "\nExpected message: " + expectedMessage);
    }

    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        driver.quit();
    }


}
