package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private static final String FAQ_BUTTON_BY_INDEX_PATTERN =
            "(//div[@data-accordion-component='AccordionItemButton'])[%d]";
    private static final String FAQ_ANSWER_BY_INDEX_PATTERN =
            "(//div[@data-accordion-component='AccordionItemPanel'])[%d]";

    private final By TOP_ORDER_BUTTON_LOCATOR = By.xpath(".//div[contains(@class, 'Header_Nav')]//button[text()='Заказать']");
    private final By DOWN_ORDER_BUTTON_LOCATOR = By.xpath(".//div[contains(@class, 'Home_FinishButton')]//button[text()='Заказать']");

    private final By STATUS_BUTTON_LOCATOR = By.xpath("//button[text()='Статус заказа']");
    private final By STATUS_INPUT_LOCATOR = By.xpath("//input[@placeholder='Введите номер заказа']");
    private final By GO_BUTTON_LOCATOR = By.xpath("//button[text()='Go!']");

    private final By SCOOTER_LOGO_LOCATOR = By.xpath("//img[@alt='Scooter']");
    private final By YANDEX_LOGO_LOCATOR = By.xpath("//img[@alt='Yandex']");

    private static final By COOKIE_ACCEPT_BUTTON_LOCATOR = By.id("rcc-confirm-button");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void acceptCookiesIfVisible() {
        if (isDisplayed(COOKIE_ACCEPT_BUTTON_LOCATOR, BasePage.SHORT_TIMEOUT)) {
            click(COOKIE_ACCEPT_BUTTON_LOCATOR);
        }
    }

    public void openFaqQuestion(int index) {
        acceptCookiesIfVisible();
        By button = By.xpath(String.format(FAQ_BUTTON_BY_INDEX_PATTERN, index + 1));
        scrollToElement(button);
        click(button);
    }

    public String getFaqAnswer(int index) {
        By answerPanel = By.xpath(String.format(FAQ_ANSWER_BY_INDEX_PATTERN, index + 1));
        return getText(answerPanel);
    }

    public void clickOrderButton(String placement) {
        acceptCookiesIfVisible();
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
        if ("Scooter".equals(name)) {
            click(SCOOTER_LOGO_LOCATOR);
        } else if ("Yandex".equals(name)) {
            click(YANDEX_LOGO_LOCATOR);
        } else {
            throw new IllegalArgumentException("Unsupported logo name: " + name);
        }
    }
}
