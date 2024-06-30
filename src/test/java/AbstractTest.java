import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class AbstractTest {
    public enum Browser {
        YA,
        CHROME
    }

    /**
     * Хром браузер работает только под версию драйвера 124.0.6367.766 поэтому в системных
     * переменных добавлены две версии chromedriver:
     * - 124ая для yandex browser (CHROME_124)
     * - 126ая версия для chrome (CHROME_126)
     */
    @Step("Запуск браузера")
    protected WebDriver getDriver(Browser browser) {
        final WebDriver result;
        switch (browser) {
            case YA: {
                System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_124"));
                ChromeOptions options = new ChromeOptions();
                options.setBinary("C:\\Users\\levin\\AppData\\Local\\Yandex\\YandexBrowser\\Application\\browser.exe");
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
                options.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
                result = new ChromeDriver(options);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unsupported browser type");
        }
        return result;
    }
}
