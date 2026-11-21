package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class OrderRentPage extends BasePage {

    private static final String RENTAL_PERIOD_OPTION_PATTERN =
            ".//div[@class='Dropdown-menu']//div[text()='%s']";
    private static final String COLOR_CHECKBOX_PATTERN =
            "//label[contains(text(), '%s')]/input";

    private final By dateFieldLocator = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By rentalPeriodFieldLocator = By.xpath("//div[text()='* Срок аренды']");
    private final By orderButtonLocator =
            By.xpath(".//div[contains(@class, 'Order_Buttons')]//button[text()='Заказать']");
    private final By confirmOrderButtonLocator =
            By.xpath(".//div[contains(@class, 'Order_Buttons')]//button[text()='Да']");
    private final By orderSuccessHeaderLocator =
            By.xpath("//div[contains(@class, 'Order_ModalHeader__') and contains(text(), 'Заказ оформлен')]");

    public OrderRentPage(WebDriver driver) {
        super(driver);
    }

    public void fillRentDetails(String bringDate, String period, String color) {
        type(dateFieldLocator, bringDate, Keys.ENTER, Keys.ESCAPE);

        By periodOption = By.xpath(String.format(RENTAL_PERIOD_OPTION_PATTERN, period));
        selectDropdownOption(rentalPeriodFieldLocator, periodOption);

        if (color != null && !color.isEmpty()) {
            By checkbox = By.xpath(String.format(COLOR_CHECKBOX_PATTERN, color));
            click(checkbox);
        }
    }

    // Returns the value of the date field in dd.MM.yyyy format
    public String getBringDateValue() {
        return driver.findElement(dateFieldLocator).getDomProperty("value");
    }

    public void clickOrderButton() {
        click(orderButtonLocator);
    }

    public void confirmOrder() {
        click(confirmOrderButtonLocator);
    }

    public boolean isConfirmOrderButtonDisplayed() {
        return isDisplayed(confirmOrderButtonLocator);
    }

    public boolean isOrderSuccessModalDisplayed() {
        return isDisplayed(orderSuccessHeaderLocator);
    }
}