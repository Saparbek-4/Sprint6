package tests;

import org.example.HomePage;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePageTest extends BaseTest {
    @ParameterizedTest
    @MethodSource("faqProvider")
    public void checkFaqTest(String question, String answer) {
        HomePage homePage = new HomePage(driver);

        homePage.clickByText(question);

        String actualAnswer = homePage.getFaqAnswer(answer);
        assertEquals(answer, actualAnswer, "The answers didn't match");

    }

    @ParameterizedTest
    @MethodSource("logoProvider")
    public void shouldLocateToExpectedWhenClickByLogo(String logoName, String expectedUrl, boolean opensNewTab) {
        HomePage homePage = new HomePage(driver);

        if (opensNewTab) {
            homePage.switchToNewTab(()  -> homePage.clickLogo(logoName));
        } else {
            homePage.clickLogo(logoName);
        }

        homePage.waitForUrlContains(expectedUrl);

        assertTrue(driver.getCurrentUrl().contains(expectedUrl));

    }


    private static Stream<Arguments> faqProvider() {
        return Stream.of(
                Arguments.of("Сколько это стоит? И как оплатить?", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."),
                Arguments.of("Хочу сразу несколько самокатов! Так можно?", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."),
                Arguments.of("Как рассчитывается время аренды?", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."),
                Arguments.of("Можно ли заказать самокат прямо на сегодня?", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."),
                Arguments.of("Можно ли продлить заказ или вернуть самокат раньше?", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."),
                Arguments.of("Вы привозите зарядку вместе с самокатом?", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."),
                Arguments.of("Можно ли отменить заказ?", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."),
                Arguments.of("Я жизу за МКАДом, привезёте?", "Да, обязательно. Всем самокатов! И Москве, и Московской области.")
        );
    }

    private static Stream<Arguments> logoProvider() {
        return Stream.of(
                Arguments.of("Yandex", "yandex.ru", true),
                Arguments.of("Scooter", "https://qa-scooter.praktikum-services.ru/", false)
        );
    }
}
