package com.demo.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

/*
This class is the backbone of the framework
it contains the setup and tear down logic that is shared across all test cases
initializing browser
loading config.properties file
managing wait times
 */
public class BaseClass {

    protected Properties prop;
    protected WebDriver driver;

    public void setUp() throws IOException {
        //loading configuration file
        prop = new Properties();
        FileInputStream file = new FileInputStream("src/main/resoources/config.properties");
        prop.load(file);
        
        //initialize WebDriver based on browser defined in Copnf.properties file
        String  browser = prop.getProperty("browser");
        
        if(browser.equalsIgnoreCase("chrome")){
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else {
            throw new IllegalArgumentException("Browser Not Found" + browser);
        }

        //implementing Implicit Wait
        int implicitWait = Integer.parseInt(prop.getProperty("implicitWait"));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        //Maximize the driver
        driver.manage().window().maximize();

        //navigate to the url
        driver.get(prop.getProperty("url"));
    }
}
