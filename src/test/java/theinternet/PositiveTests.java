package theinternet;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
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
        String url = "http://the-internet.herokuapp.com/login";
        driver.get(url);
        System.out.println("Page is opened");
        //enter username

        //enter password

        //clock login button

        //verification:
        //new url
        //logout button is visible
        //successful login message

        //Close browser
        driver.quit();

    }


}
