package webtests;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import webpages.AuthorizationPage;
import webpages.RegistrationPage;
import webtestutils.BrowserChose;
import webtestutils.TestConstants;
import webtestutils.TestUtils;
import webtestutils.User;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RegistrationTest {
    private final String name;
    private final String email;
    private final String password;
    private final Boolean success;

    WebDriver driver;

    public RegistrationTest(String name, String email, String password, Boolean success) {
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
        driver = BrowserChose.getDriver();
        // переход на страницу регистрации приложения
        driver.get(TestConstants.REGISTRATION_PAGE_URL);
        //Создаем объект страницы
        RegistrationPage registrationPage = new RegistrationPage(driver);
        registrationPage.setName(name);
        registrationPage.setEmail(email);
        registrationPage.setPassword(password);
        if (success) {
            AuthorizationPage authorizationPage =  registrationPage.registrationButtonClick();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            try {
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(authorizationPage.getEnterScript())); // Замените "elementId" на идентификатор элемента, который ожидается
            } catch (TimeoutException e) {
                Assert.fail("Элемент не появился за отведенное время");
            }
            driver.findElement(authorizationPage.getEnterScript());
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
