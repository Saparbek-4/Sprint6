package tests;

import org.example.HomePage;
import org.example.OrderPersonPage;
import org.example.OrderRentPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import testdata.OrderTestData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("testdata.OrderTestData#positiveOrderProvider")
    public void shouldCompleteOrderSuccessfully(String buttonPlacement, String name, String surname,
                                                String address, String metro, String phone,
                                                String bringDate, String period, String color) {
        HomePage homePage = new HomePage(driver);
        homePage.clickOrderButton(buttonPlacement);

        OrderPersonPage orderPersonPage = new OrderPersonPage(driver);
        orderPersonPage.fillPersonDetails(name, surname, address, metro, phone);

        OrderRentPage orderRentPage = orderPersonPage.clickNextButton();
        orderRentPage.fillRentDetails(bringDate, period, color);

        assertEquals(bringDate, orderRentPage.getBringDateValue(),
                "В поле даты доставки должно быть записано значение в формате dd.MM.yyyy");

        orderRentPage.clickOrderButton();
        orderRentPage.confirmOrder();

        assertTrue(orderRentPage.isOrderSuccessModalDisplayed(),
                "Окно об успешном создании заказа не отобразилось");
    }

    @ParameterizedTest
    @MethodSource("testdata.OrderTestData#negativePersonDetailsProvider")
    public void shouldShowValidationErrorsWhenPersonDetailFieldsIsInvalid(String placeholder, String input,
                                                                          String expectedErrorMessage) {
        HomePage homePage = new HomePage(driver);
        homePage.clickOrderButton(OrderTestData.DEFAULT_ORDER_BUTTON);

        OrderPersonPage orderPersonPage = new OrderPersonPage(driver);
        orderPersonPage.enterInvalidField(placeholder, input);

        String actual = orderPersonPage.getErrorMessageForField(placeholder);
        assertEquals(expectedErrorMessage, actual);
    }

    @Test
    // Известный баг приложения: форма позволяет оформить заказ с прошедшей датой
    public void shouldNotAllowOrderWithPastDeliveryDate() {
        OrderRentPage orderRentPage = getOrderRentPage();
        String yesterdayDate = OrderTestData.yesterdayDate();
        orderRentPage.fillRentDetails(yesterdayDate, OrderTestData.DEFAULT_PERIOD, OrderTestData.DEFAULT_COLOR);

        assertEquals(yesterdayDate, orderRentPage.getBringDateValue(),
                "В поле даты доставки должно быть записано вчерашнее число в формате dd.MM.yyyy");

        orderRentPage.clickOrderButton();

        assertFalse(orderRentPage.isConfirmOrderButtonDisplayed(),
                "Заказ не должен оформляться при указании прошедшей даты доставки: " + yesterdayDate);
    }

    private OrderRentPage getOrderRentPage() {
        HomePage homePage = new HomePage(driver);
        homePage.clickOrderButton(OrderTestData.DEFAULT_ORDER_BUTTON);

        OrderPersonPage orderPersonPage = new OrderPersonPage(driver);
        orderPersonPage.fillPersonDetails(
                OrderTestData.DEFAULT_NAME,
                OrderTestData.DEFAULT_SURNAME,
                OrderTestData.DEFAULT_ADDRESS,
                OrderTestData.DEFAULT_METRO,
                OrderTestData.DEFAULT_PHONE
        );

        return orderPersonPage.clickNextButton();
    }
}