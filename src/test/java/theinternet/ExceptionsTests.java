package theinternet;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ExceptionsTests {

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
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test(priority = 1)
    public void notVisibleTest() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        WebElement startButton = driver.findElement(By.xpath("//button[text()='Start']"));
        startButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement finishText = driver.findElement(By.id("finish"));
        wait.until(ExpectedConditions.visibilityOf(finishText));
        String actualFinishText = finishText.getText();
        String expectedFinishText = "Hello World!";
        Assert.assertEquals(actualFinishText, expectedFinishText, "" + actualFinishText + " " + expectedFinishText);

    }

    @Test(priority = 2)
    public void timeoutTest() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/1");
        WebElement startButton = driver.findElement(By.xpath("//button[text()='Start']"));
        startButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        WebElement finishText = driver.findElement(By.id("finish"));
        try {
            wait.until(ExpectedConditions.visibilityOf(finishText));
        } catch (TimeoutException exception) {
            System.out.println("Exception caught" + exception.getMessage());
            sleep(3000);
        }
        String actualFinishText = finishText.getText();
        String expectedFinishText = "Hello World!";
        Assert.assertEquals(actualFinishText, expectedFinishText, "" + actualFinishText + " " + expectedFinishText);

    }

    @Test(priority = 3)
    public void noSuchElementTest() {
        driver.get("https://the-internet.herokuapp.com/dynamic_loading/2");
        WebElement startButton = driver.findElement(By.xpath("//button[text()='Start']"));
        startButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Assert.assertTrue(
                wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("finish"), "Hello World!")),
                "Could not verify expected text 'Hello World!' ");

        /*WebElement finishText = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("finish")));
        String actualFinishText = finishText.getText();
        String expectedFinishText = "Hello World!";
        Assert.assertEquals(actualFinishText, expectedFinishText, "" + actualFinishText + " " + expectedFinishText);
        */
    }

    @Test
    public void staleElementTest() {
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        WebElement checkbox = driver.findElement(By.id("checkbox"));
        WebElement removeButton = driver.findElement(By.xpath("//button[contains(text(),'Remove')]"));

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        removeButton.click();

        /*wait.until(ExpectedConditions.invisibilityOf(checkbox));
        Assert.assertFalse(checkbox.isDisplayed());*/

        /*Assert.assertTrue(wait.until(ExpectedConditions.invisibilityOf(checkbox)),
                "Checkbox is still visible, but shouldn't`t be");*/

        Assert.assertTrue(wait.until(ExpectedConditions.stalenessOf(checkbox)),
                "Checkbox is still visible, but shouldn't`t be");

        WebElement addButton = driver.findElement(By.xpath("//button[contains(text(),'Add')]"));
        addButton.click();
        checkbox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("checkbox")));
        Assert.assertTrue(checkbox.isDisplayed(), "Checkbox is not visible, but it should be");
    }

    @Test
    public void disabledElementTest() {
        driver.get("https://the-internet.herokuapp.com/dynamic_controls");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement textField = driver.findElement(By.xpath("//input[@type='text']"));
        WebElement enableButton = driver.findElement(By.xpath("//button[text()='Enable']"));
        //WebElement disableButton = driver.findElement(By.xpath("//button[text()='Disable']"));

        enableButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(textField));

        textField.sendKeys("Some text");

        String actualText = textField.getAttribute("value");
        String expectedText = "Some text";
        Assert.assertEquals(actualText,expectedText);


    }

    @AfterMethod(alwaysRun = true)
    private void tearDown() {
        driver.quit();
    }

    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
