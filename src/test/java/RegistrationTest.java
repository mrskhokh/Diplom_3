import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import stellarburgers.yandex.RegistrationPage;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RegistrationTest extends AbstractTest {
    private final Browser browser;
    private final String name;
    private final String email;
    private final String password;
    private final Boolean success;

    WebDriver driver;

    public RegistrationTest(Browser browser, String name, String email, String password, Boolean success) {
        this.browser = browser;
        this.name = name;
        this.email = email;
        this.password = password;
        this.success = success;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Browser.CHROME, "Иванов Иван",
                        TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru", "12345678", true},
                {Browser.CHROME, "Иванов Иван",
                        TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru", "123", false},
                {Browser.YA, "Иванов Иван",
                        TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru", "12345678", true},
                {Browser.YA, "Иванов Иван",
                        TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru", "123", false}
        });
    }

    @Test
    public void registrationTestCases() {
        driver = getDriver(browser);
        // переход на страницу регистрации приложения
        String url = "https://stellarburgers.nomoreparties.site/register";
        driver.get(url);
        //Создаем объект страницы
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        if (success) {
            registrationPage.registrationButtonClick();
            try {
                Thread.sleep(1000); // Пауза на секунду
            } catch (InterruptedException e) {
                Assert.fail(e.getMessage());
            }
            driver.findElement(By.xpath("//h2[text()='Вход']"));
            System.out.println("Пользователь успешно зарегистрирован");
            String token = User.login(email, password);

            User.delete(token);
        } else {
            registrationPage.registrationButtonClick();

            String messageText = registrationPage.wrongPasswordMessageGetText();
            Assert.assertNotNull(messageText);
        }
    }


    @After
    public void tearDown() {
        // Закрой браузер
        driver.quit();
    }
}
