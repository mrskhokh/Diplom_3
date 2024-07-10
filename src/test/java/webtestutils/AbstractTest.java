package webtestutils;

import io.qameta.allure.Step;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public abstract class AbstractTest {
    public enum Browser {
        YA,
        CHROME
    }


    public static final String MAIN_PAGE_URL = "https://stellarburgers.nomoreparties.site";
    public static final String REGISTRATION_PAGE_URL = "https://stellarburgers.nomoreparties.site/register";

    public static final String API_REGISTER_URL = "https://stellarburgers.nomoreparties.site/api/auth/register";

    public static final String API_LOGIN_URL = "https://stellarburgers.nomoreparties.site/api/auth/login";
    public static final String API_DELETE_USER_URL = "https://stellarburgers.nomoreparties.site/api/auth/login";

    /**
     * Хром браузер работает только под версию драйвера 124.0.6367.766 поэтому в системных
     * переменных добавлены две версии chromedriver:
     * - 124ая для yandex browser (CHROME_124)
     * - 126ая версия для chrome (CHROME_126)
     */
    private Browser getBrowserType() {
        Properties properties = new Properties();
        Browser result = Browser.CHROME;
        try (InputStream input = PropertiesConfiguration.PropertiesReader.
                class.getClassLoader().getResourceAsStream("my.properties")) {
            if (input == null) {
                System.out.println("Не нашел my.properties");
                return result;
            }

            properties.load(input);

            String testBrowser = properties.getProperty("test.browser");
            result = Browser.valueOf(testBrowser);
        } catch (IOException ex) {
            Assert.fail(ex.getMessage());
        }
        return result;
    }

    @Step("Запуск браузера")
    protected WebDriver getDriver() {
        final WebDriver result;
        final Browser browser = getBrowserType();
        System.out.println("Запускаю браузер: " + browser);
        switch (browser) {
            case YA: {
                System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_124"));
                ChromeOptions options = new ChromeOptions();
                options.setBinary(System.getenv("YA_BROWSER_PATH"));
                options.addArguments(
                        "--no-sandbox",
                        "--headless",
                        "--disable-dev-shm-usage"
                );
                result = new ChromeDriver(options);
                break;
            }
            case CHROME: {
                System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_126"));
                ChromeOptions options = new ChromeOptions();
                //options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
                result = new ChromeDriver(options);
                break;
           }
            default:
                throw new UnsupportedOperationException("Unsupported browser type");
        }
        return result;
    }
}
