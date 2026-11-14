package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private final String FAQ_BUTTON_PATTERN = "//div[@data-accordion-component='AccordionItemButton' and contains(text(), '%s')]";
    private final String FAQ_ANSWER_PATTERN = "//div[@data-accordion-component='AccordionItemPanel']//p[contains(text(), '%s')]";

    private final By TOP_ORDER_BUTTON_LOCATOR = By.xpath(".//div[contains(@class, 'Header_Nav')]//button[text()='Заказать']");
    private final By DOWN_ORDER_BUTTON_LOCATOR = By.xpath(".//div[contains(@class, 'Home_FinishButton')]//button[text()='Заказать']");

    private final By STATUS_BUTTON_LOCATOR = By.xpath("//button[text()='Статус заказа']");
    private final By STATUS_INPUT_LOCATOR = By.xpath("//input[@placeholder='Введите номер заказа']");
    private final By GO_BUTTON_LOCATOR = By.xpath("//button[text()='Go!']");

    private final By SCOOTER_LOGO_LOCATOR = By.xpath("//img[@alt='Scooter']");
    private final By YANDEX_LOGO_LOCATOR = By.xpath("//img[@alt='Yandex']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void clickByText(String text) {
        By button = By.xpath(String.format(FAQ_BUTTON_PATTERN, text));
        scrollToElement(button);
        click(button);
    }

    public String getFaqAnswer(String text) {
        By answer = By.xpath(String.format(FAQ_ANSWER_PATTERN, text));
        scrollToElement(answer);
        return getText(answer);
    }

    public void clickOrderButton(String placement) {
        if (placement.equals("UP")) {
            click(TOP_ORDER_BUTTON_LOCATOR);
        } else if (placement.equals("DOWN")) {
            click(DOWN_ORDER_BUTTON_LOCATOR);
        } else {
            throw new IllegalArgumentException("Unsupported button placement " + placement);
        }
    }

    public void enterOrderNumberAndCheck(String orderNumber) {
        click(STATUS_BUTTON_LOCATOR);
        type(STATUS_INPUT_LOCATOR, orderNumber);
        click(GO_BUTTON_LOCATOR);
    }

    public void clickLogo(String name) {
        if (name.equals("Scooter"))  {
            click(SCOOTER_LOGO_LOCATOR);
        } else {
            click(YANDEX_LOGO_LOCATOR);
        }
    }
}
