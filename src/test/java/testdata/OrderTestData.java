package testdata;

import org.junit.jupiter.params.provider.Arguments;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public final class OrderTestData {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static final String DEFAULT_ORDER_BUTTON = "UP";
    public static final String DEFAULT_NAME = "Иван";
    public static final String DEFAULT_SURNAME = "Иванов";
    public static final String DEFAULT_ADDRESS = "Москва, ул. Ленина, 1";
    public static final String DEFAULT_METRO = "Черкизовская";
    public static final String DEFAULT_PHONE = "+79991112233";
    public static final String DEFAULT_PERIOD = "сутки";
    public static final String DEFAULT_COLOR = "чёрный жемчуг";

    private OrderTestData() {
    }

    public static Stream<Arguments> positiveOrderProvider() {
        return Stream.of(
                Arguments.of(DEFAULT_ORDER_BUTTON, DEFAULT_NAME, DEFAULT_SURNAME, DEFAULT_ADDRESS, DEFAULT_METRO, DEFAULT_PHONE,
                        futureDate(15), DEFAULT_PERIOD, DEFAULT_COLOR),
                Arguments.of("DOWN", "Пётр", "Петров", "Москва, ул. Пушкина, 10", "Сокольники", "+79992223344",
                        futureDate(90), "двое суток", "серая безысходность")
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

    public static String yesterdayDate() {
        return LocalDate.now().minusDays(1).format(DATE_FORMATTER);
    }

    public static String futureDate(int daysToAdd) {
        return LocalDate.now().plusDays(daysToAdd).format(DATE_FORMATTER);
    }
}