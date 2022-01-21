package theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class LoginTests {

    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    private void setUp() {
        //Create driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        //maximize driver window
        driver.manage().window().maximize();
    }


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
        //click login button
        WebElement logInButton = driver.findElement(By.tagName("button"));
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
        //Close browser
        driver.quit();

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
