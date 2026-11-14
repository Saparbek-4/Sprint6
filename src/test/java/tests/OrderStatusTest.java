package tests;

import org.example.HomePage;
import org.example.OrderStatusPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderStatusTest extends BaseTest {
    @Test
    public void shouldShowErrorWhenOrderNumberIsInvalid() {
        HomePage homePage = new HomePage(driver);

        String invalidOrderNumber = "99999999";
        homePage.enterOrderNumberAndCheck(invalidOrderNumber);

        OrderStatusPage orderStatusPage = new OrderStatusPage(driver);

        assertTrue(orderStatusPage.isNotFoundMessageDisplayed(),
                "Сообщение 'Такого заказа нет' не отображается для неверного номера: " + invalidOrderNumber);
    }
}
