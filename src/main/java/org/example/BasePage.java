package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    protected static final Duration SHORT_TIMEOUT = Duration.ofSeconds(5);

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, DEFAULT_TIMEOUT);
    }

    protected List<WebElement> findAll(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    public void type(By locator, String text, Keys... keysToSend) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);

        if (keysToSend != null && keysToSend.length > 0) {
            element.sendKeys(keysToSend);
        }
    }

    protected void scrollToElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
    }

    protected boolean isDisplayed(By locator) {
        return isDisplayed(locator, DEFAULT_TIMEOUT);
    }

    protected boolean isDisplayed(By locator, Duration timeout) {
        try {
            new WebDriverWait(driver, timeout)
                    .until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected void selectDropdownOption(By inputLocator, String optionText) {
        type(inputLocator, optionText);
        By optionLocator = By.xpath(String.format(".//div[text()='%s']", optionText));
        click(optionLocator);
    }

    protected void selectDropdownOption(By dropdownLocator, By optionLocator) {
        click(dropdownLocator);
        click(optionLocator);
    }

    public void switchToNewTab(Runnable clickAction) {
        String originalWindow = driver.getWindowHandle();
        clickAction.run();

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public void waitForUrlContains(String expectedUrlPart) {
        wait.until(ExpectedConditions.urlContains(expectedUrlPart));
    }
}