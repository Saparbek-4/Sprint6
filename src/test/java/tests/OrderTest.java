package tests;

import org.example.HomePage;
import org.example.OrderPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class OrderTest extends BaseTest {
    public static Stream<Arguments> positiveOrderProvider() {
        return Stream.of(
                Arguments.of("UP", "Иван", "Иванов", "Москва, ул. Ленина, 1", "Черкизовская", "+79991112233", "2027-06-01", "сутки", "чёрный жемчуг"),
                Arguments.of("DOWN", "Пётр", "Петров", "Москва, ул. Пушкина, 10", "Сокольники", "+79992223344", "2028-12-12", "двое суток", "серая безысходность")
        );
    }

    public static Stream<Arguments> negativePersonDetailsProvider() {
        return Stream.of(
                Arguments.of("* Имя", "1", "Введите корректное имя"),
                Arguments.of("* Фамилия", "2", "Введите корректную фамилию"),
                Arguments.of("* Адрес: куда привезти заказ", "3", "Введите корректный адрес"),
                Arguments.of("* Станция метро", "", "Выберите станцию"),
                Arguments.of("* Телефон: на него позвонит курьер", "5", "Введите корректный номер")
        );
    }

    @ParameterizedTest
    @MethodSource("positiveOrderProvider")
    public void shouldCompleteOrderSuccessfully(String buttonPlacement, String name, String surname,
                                                String address, String metro, String phone,
                                                String bringDate, String period, String color) {
        HomePage homePage = new HomePage(driver);
        homePage.clickOrderButton(buttonPlacement);

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillOrderForm(name, surname, address, metro, phone, bringDate, period, color);
    }

    @ParameterizedTest
    @MethodSource("negativePersonDetailsProvider")
    public void shouldShowValidationErrorsWhenPersonDetailFieldsIsInvalid(String placeholder, String input, String expectedErrorMessage) {
        // Шаги негативного теста валидации
        HomePage homePage = new HomePage(driver);
        homePage.clickOrderButton("UP");

        OrderPage orderPage = new OrderPage(driver);
        // для метро свой метод, для остальных общий
        if (placeholder.contains("метро")) {
            orderPage.enterInvalidMetro(input);
        } else {
            orderPage.enterInvalidField(orderPage.getLocatorByPlaceholder(placeholder), input);
        }

        String actual = orderPage.getErrorMessageForField(placeholder);

        assertEquals(expectedErrorMessage, actual);

    }

    @Test
    // Couldn't be passed. Because it allows outdated dates
    public void shouldNotAllowOrderWithPastDeliveryDate() {
        // Шаги негативного теста валидации
        HomePage homePage = new HomePage(driver);
        homePage.clickOrderButton("UP");

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillPersonDetails("Иван", "Иванов", "Москва, ул. Ленина, 1", "Черкизовская", "+79991112233");
        orderPage.clickNextButton();

        // Generating passed date
        String yesterdayDate = java.time.LocalDate.now()
                .minusDays(1)
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy.MM.dd"));


        orderPage.fillRentDetails(yesterdayDate, "сутки", "чёрный жемчуг");
        orderPage.clickOrderButton();

        // Confirm order button shouldn't be displayed
        assertFalse(orderPage.isConfirmOrderButtonDisplayed(),
                "Заказ не должен оформляться при указании прошедшей даты доставки: " + yesterdayDate);
    }

}
