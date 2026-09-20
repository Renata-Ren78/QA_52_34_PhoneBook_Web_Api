package manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.WDListener;

import java.lang.reflect.Method;


public class AppManager {
    private WebDriver driver;
    public WebDriver getDriver(){return driver;}

    public Logger logger = LoggerFactory.getLogger(AppManager.class);
    static String browser = System.getProperty("browser", Browser.CHROME.browserName());

    @BeforeMethod(alwaysRun = true)
    public void setup(Method method){
        //driver = new ChromeDriver();
        if(browser.equals(Browser.CHROME.browserName())) {
            driver = new ChromeDriver();
        }
        else if (browser.equals(Browser.FIREFOX.browserName())) {
            driver = new FirefoxDriver();
        }
        else if (browser.equals(Browser.EDGE.browserName())) {
            driver = new EdgeDriver();
        }


        driver.manage().window().maximize();
        logger.info("Start testing with method -->" + method.getName());
        WebDriverListener webDriverListener = new WDListener();
        driver = new EventFiringDecorator<>(webDriverListener).decorate(driver);
    }

    @AfterMethod(alwaysRun = true)  //  enabled = false
    public void tearDown(){
        if(driver != null){
            driver.quit();
        }
    }
}
