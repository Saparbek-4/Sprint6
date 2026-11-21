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
    @MethodSource("testdata.HomeTestData#faqProvider")
    public void checkFaqTest(int index, String answer) {
        HomePage homePage = new HomePage(driver);

        homePage.openFaqQuestion(index);

        String actualAnswer = homePage.getFaqAnswer(index);
        assertEquals(answer, actualAnswer, "The answers didn't match");

    }

    @ParameterizedTest
    @MethodSource("testdata.HomeTestData#logoProvider")
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
}
