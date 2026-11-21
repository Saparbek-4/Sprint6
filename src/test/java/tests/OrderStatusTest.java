package tests;

import org.example.HomePage;
import org.example.OrderStatusPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderStatusTest extends BaseTest {
    private static final String NON_EXISTENT_ORDER_NUMBER = "999999";
    @Test
    public void shouldShowErrorWhenOrderNumberIsInvalid() {
        HomePage homePage = new HomePage(driver);

        homePage.enterOrderNumberAndCheck(NON_EXISTENT_ORDER_NUMBER);

        OrderStatusPage orderStatusPage = new OrderStatusPage(driver);

        assertTrue(orderStatusPage.isNotFoundMessageDisplayed(),
                "Сообщение 'Такого заказа нет' не отображается для неверного номера: " + NON_EXISTENT_ORDER_NUMBER);
    }
}
