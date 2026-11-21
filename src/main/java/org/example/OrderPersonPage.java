package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class OrderPersonPage extends BasePage {

    private static final String INPUT_BY_PLACEHOLDER_PATTERN = "//input[@placeholder='%s']";
    private static final String ERROR_MESSAGE_PATTERN =
            "//div[input[@placeholder='%s']]//div[contains(@class, 'Input_ErrorMessage')]";

    private final By nameFieldLocator = By.xpath("//input[@placeholder='* Имя']");
    private final By surnameFieldLocator = By.xpath("//input[@placeholder='* Фамилия']");
    private final By addressFieldLocator = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By metroFieldLocator = By.xpath("//input[@placeholder='* Станция метро']");
    private final By phoneFieldLocator = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By nextButtonLocator = By.xpath("//button[text()='Далее']");
    private final By metroErrorLocator = By.xpath("//div[contains(@class, 'Order_MetroError')]");

    public OrderPersonPage(WebDriver driver) {
        super(driver);
    }

    public void fillPersonDetails(String name, String surname, String address, String metro, String phone) {
        type(nameFieldLocator, name);
        type(surnameFieldLocator, surname);
        type(addressFieldLocator, address);
        selectDropdownOption(metroFieldLocator, metro);
        type(phoneFieldLocator, phone);
    }

    public OrderRentPage clickNextButton() {
        click(nextButtonLocator);
        return new OrderRentPage(driver);
    }

    public void enterInvalidField(String placeholder, String value) {
        if (placeholder.contains("метро")) {
            enterInvalidMetro(value);
            return;
        }
        By fieldLocator = By.xpath(String.format(INPUT_BY_PLACEHOLDER_PATTERN, placeholder));
        type(fieldLocator, value, Keys.ENTER, Keys.TAB);
    }

    private void enterInvalidMetro(String metro) {
        type(metroFieldLocator, metro);
        driver.findElement(metroFieldLocator).sendKeys(Keys.ESCAPE);
        click(nextButtonLocator);
    }

    public String getErrorMessageForField(String placeholder) {
        By locator = placeholder.contains("метро")
                ? metroErrorLocator
                : By.xpath(String.format(ERROR_MESSAGE_PATTERN, placeholder));
        return getText(locator);
    }
}