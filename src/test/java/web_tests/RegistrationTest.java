package web_tests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import web_pages.RegistrationPage;
import web_test_utils.AbstractTest;
import web_test_utils.TestUtils;
import web_test_utils.User;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RegistrationTest extends AbstractTest {
    private final String name;
    private final String email;
    private final String password;
    private final Boolean success;

    WebDriver driver;

    public RegistrationTest( String name, String email, String password, Boolean success) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.success = success;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                { "Иванов Иван",
                        TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru", "12345678", true},
                { "Иванов Иван",
                        TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru", "123", false}
        });
    }
    @DisplayName("Тест регистрации")
    @Test
    public void registrationTestCases() {
        driver = getDriver();
        // переход на страницу регистрации приложения
        driver.get(AbstractTest.registrationPageUrl);
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
