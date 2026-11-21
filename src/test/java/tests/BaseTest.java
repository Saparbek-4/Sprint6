package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class BaseTest {

    private static final String BROWSER_PROPERTY = "browser";
    private static final String CHROME = "chrome";
    private static final String FIREFOX = "firefox";
    protected static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

    protected WebDriver driver;

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty(BROWSER_PROPERTY, CHROME)
                .trim()
                .toLowerCase();

        switch (browser) {
            case CHROME:
                driver = new ChromeDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException(
                        String.format("Unsupported browser: %s. Use %s OR %s.", browser, CHROME, FIREFOX)
                );
        }
        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
