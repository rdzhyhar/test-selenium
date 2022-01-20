package theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PositiveTests {

    @Test
    public void loginTest() {
        System.out.println("Starting loginTest");
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


}
