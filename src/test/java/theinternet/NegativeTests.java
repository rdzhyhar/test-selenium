package theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class NegativeTests {
    @Parameters({"username","password","expectedMessage"})
    @Test(priority = 1, groups = {"negativeTests"})
    public void negativeLoginTest(String username, String password, String expectedMessage) {
        System.out.println("Starting negativeLoginTest with " + username + " and " + password);
        //Create driver
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        //maximize driver window
        driver.manage().window().maximize();
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
        //Close browser
        driver.quit();

    }

    /*@Test(priority = 2,groups = {"negativeTests"})
    public void negativePasswordTest() {
        System.out.println("Starting negativePasswordTest");
        //Create driver
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        //maximize driver window
        driver.manage().window().maximize();
        //open test page
        String url = "https://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");
        //enter username
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");
        //enter password
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("incorrectPassword");
        //click login button
        WebElement logInButton = driver.findElement(By.tagName("button"));
        logInButton.click();
        //verifications:
        //successful login message
        WebElement errorMessage = driver.findElement(By.id("flash"));
        String expectedErrorMessage = "Your password is invalid!";
        String actualErrorMessage = errorMessage.getText();
        Assert.assertTrue
                (actualErrorMessage.contains(expectedErrorMessage),
                        "Actual message does not contains expected message." +
                                "\nActual message: " + actualErrorMessage +
                                "\nExpected message: " + expectedErrorMessage);
        //Close browser
        driver.quit();

    }*/

}
