package theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class NegativeTests {
    @Test
    public void negativeUsernameTest() {
        System.out.println("Starting negativeUsernameTest");
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
        username.sendKeys("incorrectUsername");
        //enter password
        WebElement password = driver.findElement(By.name("password"));
        password.sendKeys("SuperSecretPassword!");
        //click login button
        WebElement logInButton = driver.findElement(By.tagName("button"));
        logInButton.click();
        //verifications:
        //successful login message
        WebElement errorMessage = driver.findElement(By.id("flash"));
        String expectedErrorMessage = "Your username is invalid!";
        String actualErrorMessage = errorMessage.getText();
        Assert.assertTrue
                (actualErrorMessage.contains(expectedErrorMessage),
                        "Actual message does not contains expected message." +
                                "\nActual message: " + actualErrorMessage +
                                "\nExpected message: " + expectedErrorMessage);
        //Close browser
        driver.quit();

    }

    @Test
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

    }

}
