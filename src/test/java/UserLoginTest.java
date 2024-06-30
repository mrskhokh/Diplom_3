import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import stellarburgers.yandex.AccountProfilePage;
import stellarburgers.yandex.AuthorizationPage;
import stellarburgers.yandex.MainPage;
import stellarburgers.yandex.RegistrationPage;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UserLoginTest extends AbstractTest {

    private final Browser browser;
    private final String email;
    private final String password;
    private final String name;
    WebDriver driver;

    public UserLoginTest(Browser browser, String email, String password, String name) {
        this.browser = browser;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {Browser.CHROME, TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru",
                                "12345678", "Иванов Иван"},
                        {Browser.YA, TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru",
                                "12345678", "Иванов Иван"}
                });
    }

    @Test
    public void mainPageProfileCabenetLoginTest() {
        User.create(email, password, name);
        driver = getDriver(browser);

        // переход на страницу регистрации приложения
        String url = "https://stellarburgers.nomoreparties.site";
        driver.get(url);
        MainPage mainpage = new MainPage(driver);
        AuthorizationPage authorizationPage = mainpage.accountEnterButtonClick();
        authorizationPage.setEmail(email);
        authorizationPage.setPassword(password);

        MainPage mainPage = authorizationPage.loginButtonClick();
        AccountProfilePage accountProfilePage = mainPage.authorizedProfileCabinetLinkClick();
        try {
            Thread.sleep(1000); // Пауза на секунду
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        assertEquals(accountProfilePage.getNameValue(), name);
    }

    @Test
    public void mainPageProfileCabinetLoginTest() {
        User.create(email, password, name);
        driver = getDriver(browser);
        String url = "https://stellarburgers.nomoreparties.site";
        driver.get(url);
        MainPage mainpage = new MainPage(driver);
        AuthorizationPage authorizationPage = mainpage.profileCabinetLinkClick();
        authorizationPage.setEmail(email);
        authorizationPage.setPassword(password);

        mainpage = authorizationPage.loginButtonClick();
        AccountProfilePage accountProfilePage = mainpage.authorizedProfileCabinetLinkClick();
        try {
            Thread.sleep(1000); // Пауза на секунду
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        assertEquals(accountProfilePage.getNameValue(), name);
    }

    @Test
    public void registrationPageLoginTest() {
        User.create(email, password, name);
        driver = getDriver(browser);
        String url = "https://stellarburgers.nomoreparties.site/register";
        driver.get(url);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        AuthorizationPage authorizationPage = registrationPage.loginLinkClick();
        authorizationPage.setEmail(email);
        authorizationPage.setPassword(password);

        MainPage mainpage = authorizationPage.loginButtonClick();
        AccountProfilePage accountProfilePage = mainpage.authorizedProfileCabinetLinkClick();
        try {
            Thread.sleep(1000); // Пауза на секунду
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        assertEquals(accountProfilePage.getNameValue(), name);
    }

    @Test
    public void userLogoutTest() {
        User.create(email, password, name);
        driver = getDriver(browser);
        String url = "https://stellarburgers.nomoreparties.site";
        driver.get(url);
        MainPage mainpage = new MainPage(driver);
        AuthorizationPage authorizationPage = mainpage.profileCabinetLinkClick();
        authorizationPage.setEmail(email);
        authorizationPage.setPassword(password);

        mainpage = authorizationPage.loginButtonClick();
        AccountProfilePage accountProfilePage = mainpage.authorizedProfileCabinetLinkClick();
        try {
            Thread.sleep(1000); // Пауза на секунду
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }

        authorizationPage = accountProfilePage.logoutButtonClick();
        try {
            Thread.sleep(1000); // Пауза на секунду
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        assertEquals(authorizationPage.enterScriptGetText(), "Вход");
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        try {
            //Удаляем пользователя
            String token = User.login(email, password);
            User.delete(token);
        }
        finally {
            // Закрой браузер
            driver.quit();
        }
    }
}