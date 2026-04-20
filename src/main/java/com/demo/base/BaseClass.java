package com.demo.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/*
This class is the backbone of the framework
it contains the setup and tear down logic that is shared across all test cases
initializing browser
loading config.properties file
managing wait times
 */
public class BaseClass {

    //properties and driver object is static to be carried through TestNG Suite
    protected static Properties prop;
    protected static WebDriver driver;


    @BeforeSuite
    public void loadConfig() throws IOException {
        //loading configuration file
        prop = new Properties();
        FileInputStream file = new FileInputStream("src/main/resources/config.properties");
        prop.load(file);
    }

    @BeforeMethod
    public void setUp() throws IOException {
        System.out.println("Setting up WebDriver for:" + this.getClass().getSimpleName());
        browserLaunch();
        configBrowser();
        staticWait(2);
    }

    private void browserLaunch() {
        //initialize WebDriver based on browser defined in Copnf.properties file
        String browser = prop.getProperty("browser");

        if(browser.equalsIgnoreCase("chrome")){
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Browser Not Found" + browser);
        }
    }

    //Configure browser settings; waits, maximize browser, url navigation
    private void configBrowser(){

        //implementing Implicit Wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //Maximize the driver
        driver.manage().window().maximize();

        //navigate to the url
        //handling exception for failure
        try {
            driver.get(prop.getProperty("url"));
        } catch (Exception e) {
            System.out.println("URL Not Found" + e.getMessage());
        }
    }


    @AfterMethod
    public void tearDown() {
        if(driver != null){
            try {
                driver.quit();
            } catch (Exception e) {
                System.out.println("Unable To Quit Driver" + e.getMessage());
            }
        }
    }

    //static wait method for pausing
    //using LockSupport instead of Thread.sleep
    public void staticWait(int seconds) {
        LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));
    }
}
