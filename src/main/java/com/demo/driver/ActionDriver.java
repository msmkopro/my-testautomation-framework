package com.demo.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

// Used to store re-usable methods to handle browser interactions/actions
public class ActionDriver {


    private WebDriver driver;
    private WebDriverWait wait;

    public ActionDriver(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    //action to click on an element
    public void click(By by){
        try {
            driver.findElement(by).click();
        } catch (Exception e) {
            System.out.println("unable to click element"+ e.getMessage());
        }
    }

    //Entering text into an input field
    public void enterText(By by, String text){
            driver.findElement(by).clear();
            driver.findElement(by).sendKeys(text);
    }

    //wait for element to be clickable
    public void waitForElementToBeClickable(By by){
        try {
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Exception e) {
            System.out.println("unable to click element " + e.getMessage());
        }
    }

    //wait for element to be visible
    public void waitForElementToBeVisible(By by){
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Exception e) {
            System.out.println("element not visible" + e.getMessage());
        }
    }


}
