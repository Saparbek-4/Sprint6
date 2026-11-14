package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class OrderPage extends BasePage {

    // Локаторы первой страницы ("Для кого самокат")
    private final By NAME_FIELD_LOCATOR = By.xpath("//input[@placeholder='* Имя']");
    private final By SURNAME_FIELD_LOCATOR = By.xpath("//input[@placeholder='* Фамилия']");
    private final By ADDRESS_FIELD_LOCATOR = By.xpath("//input[@placeholder='* Адрес: куда привезти заказ']");
    private final By METRO_FIELD_LOCATOR = By.xpath("//input[@placeholder='* Станция метро']");
    private final By PHONE_FIELD_LOCATOR = By.xpath("//input[@placeholder='* Телефон: на него позвонит курьер']");
    private final By NEXT_BUTTON_LOCATOR = By.xpath("//button[text()='Далее']");

    // Локаторы второй страницы ("Про аренду")
    private final By DATE_FIELD_LOCATOR = By.xpath("//input[@placeholder='* Когда привезти самокат']");
    private final By RENTAL_PERIOD_FIELD_LOCATOR = By.xpath("//div[text()='* Срок аренды']");

    private final String COLOR_PATTERN = "//label[contains(text(), '%s')]/input";

    private final By ORDER_BUTTON_LOCATOR = By.xpath(".//div[contains(@class, 'Order_Buttons')]//button[text()='Заказать']");
    private final By CONFIRM_ORDER_BUTTON_LOCATOR = By.xpath(".//div[contains(@class, 'Order_Buttons')]//button[text()='Да']");

    private final By METRO_ERROR_LOCATOR = By.xpath("//div[contains(@class, 'Order_MetroError')]");
    private final String ERROR_MESSAGE_PATTERN = "//div[input[@placeholder='%s']]//div[contains(@class, 'Input_ErrorMessage')]";

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    public void fillPersonDetails(String name, String surname, String address, String metro, String phone) {
        type(NAME_FIELD_LOCATOR, name);
        type(SURNAME_FIELD_LOCATOR, surname);
        type(ADDRESS_FIELD_LOCATOR, address);
        // using method with input text
        selectDropdownOption(METRO_FIELD_LOCATOR, metro);
        type(PHONE_FIELD_LOCATOR, phone);
    }

    public void fillRentDetails(String bringDate, String period, String color) {
        // Entering date and setting keys to close calendar
        type(DATE_FIELD_LOCATOR, bringDate, Keys.ENTER, Keys.ESCAPE);

        // using method for dropdown where input disabled
        By periodOption = By.xpath(String.format(".//div[@class='Dropdown-menu']//div[text()='%s']", period));
        selectDropdownOption(RENTAL_PERIOD_FIELD_LOCATOR, periodOption);

        if (color != null && !color.isEmpty()) {
            By checkbox = By.xpath(String.format(COLOR_PATTERN, color));
            click(checkbox);
        }
    }

    public void fillOrderForm(String name, String surname, String address, String metro, String phone, String bringDate, String period, String color) {
        fillPersonDetails(name, surname, address, metro, phone);

        click(NEXT_BUTTON_LOCATOR);

        fillRentDetails(bringDate, period, color);

        click(ORDER_BUTTON_LOCATOR);
        click(CONFIRM_ORDER_BUTTON_LOCATOR);

    }

    public void clickNextButton() {
        click(NEXT_BUTTON_LOCATOR);
    }

    public void clickOrderButton() {
        click(ORDER_BUTTON_LOCATOR);
    }

    public void enterInvalidField(By fieldLocator, String value) {
        type(fieldLocator, value, Keys.ENTER, Keys.TAB);
    }

    public void enterInvalidMetro(String metro) {
        type(METRO_FIELD_LOCATOR, metro);
        driver.findElement(METRO_FIELD_LOCATOR).sendKeys(Keys.ESCAPE);
        click(NEXT_BUTTON_LOCATOR);
    }

    public By getLocatorByPlaceholder(String placeholder) {
        String locator = "//input[@placeholder='%s']";
        return By.xpath(String.format(locator, placeholder));
    }

    public String getErrorMessageForField(String placeholder) {
        By locator;
        if (placeholder.contains("метро")) {
            locator = METRO_ERROR_LOCATOR;
        } else {
            locator = By.xpath(String.format(ERROR_MESSAGE_PATTERN, placeholder));
        }

        return getText(locator);
    }

    public boolean isConfirmOrderButtonDisplayed() {
        return isDisplayed(CONFIRM_ORDER_BUTTON_LOCATOR);
    }
}