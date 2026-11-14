package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OrderStatusPage extends BasePage {
    private final By NOT_FOUND_IMAGE_LOCATOR = By.xpath("//img[@alt='Not found']");

    public OrderStatusPage(WebDriver driver) {
        super(driver);
    }

    // Проверка, что отображается сообщение об отсутствии заказа
    public boolean isNotFoundMessageDisplayed() {
        return isDisplayed(NOT_FOUND_IMAGE_LOCATOR);
    }
}
