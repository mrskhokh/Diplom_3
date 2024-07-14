package webtests;

import io.qameta.allure.Step;
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
import webpages.AccountProfilePage;
import webpages.AuthorizationPage;
import webpages.MainPage;
import webpages.RegistrationPage;
import webtestutils.TestConstants;
import webtestutils.TestUtils;
import webtestutils.User;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static webtestutils.BrowserChose.getDriver;

@RunWith(Parameterized.class)
public class UserLoginTest {

    private final String email;
    private final String password;
    private final String name;
    WebDriver driver;

    public UserLoginTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][]{
                        {TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru",
                                "12345678", "Иванов Иван"}
                });
    }

    @DisplayName("Тест входа в личный кабинет с главный страницы")
    @Test
    public void mainPageProfileCabenetLoginTest() {
        User.create(email, password, name);
        driver = getDriver();

        // переход на страницу регистрации приложения
        driver.get(TestConstants.MAIN_PAGE_URL);
        MainPage mainpage = new MainPage(driver);
        AuthorizationPage authorizationPage = mainpage.accountEnterButtonClick();
        authorizationPage.setEmail(email);
        authorizationPage.setPassword(password);

        MainPage mainPage = authorizationPage.loginButtonClick();
        AccountProfilePage accountProfilePage = mainPage.authorizedProfileCabinetLinkClick();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(accountProfilePage.getNameField())); // Замените "elementId" на идентификатор элемента, который ожидается
        } catch (TimeoutException e) {
            Assert.fail("Элемент не появился за отведенное время");
        }
        assertEquals(name,accountProfilePage.getNameValue());
    }

    @DisplayName("Тест входа в личный кабинет с главный страницы")
    @Test
    public void mainPageProfileCabinetLoginTest() {
        User.create(email, password, name);
        driver = getDriver();
        driver.get(TestConstants.MAIN_PAGE_URL);
        MainPage mainpage = new MainPage(driver);
        AuthorizationPage authorizationPage = mainpage.profileCabinetLinkClick();
        authorizationPage.setEmail(email);
        authorizationPage.setPassword(password);

        mainpage = authorizationPage.loginButtonClick();
        AccountProfilePage accountProfilePage = mainpage.authorizedProfileCabinetLinkClick();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(accountProfilePage.getNameField())); // Замените "elementId" на идентификатор элемента, который ожидается
        } catch (TimeoutException e) {
            Assert.fail("Элемент не появился за отведенное время");
        }
        assertEquals(name,accountProfilePage.getNameValue());
    }


    @DisplayName("Тест входа в личный кабинет со страницы регистрации")
    @Test
    public void registrationPageLoginTest() {
        User.create(email, password, name);
        driver = getDriver();
        driver.get(TestConstants.REGISTRATION_PAGE_URL);
        RegistrationPage registrationPage = new RegistrationPage(driver);
        AuthorizationPage authorizationPage = registrationPage.loginLinkClick();
        authorizationPage.setEmail(email);
        authorizationPage.setPassword(password);

        MainPage mainpage = authorizationPage.loginButtonClick();
        AccountProfilePage accountProfilePage = mainpage.authorizedProfileCabinetLinkClick();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(accountProfilePage.getNameField())); // Замените "elementId" на идентификатор элемента, который ожидается
        } catch (TimeoutException e) {
            Assert.fail("Элемент не появился за отведенное время");
        }
        assertEquals(name,accountProfilePage.getNameValue());
    }

    @Test
    @DisplayName("Тест выхода пользователя")
    public void userLogoutTest() {
        User.create(email, password, name);
        driver = getDriver();
        driver.get(TestConstants.MAIN_PAGE_URL);
        MainPage mainpage = new MainPage(driver);
        AuthorizationPage authorizationPage = mainpage.profileCabinetLinkClick();
        authorizationPage.setEmail(email);
        authorizationPage.setPassword(password);

        mainpage = authorizationPage.loginButtonClick();
        AccountProfilePage accountProfilePage = mainpage.authorizedProfileCabinetLinkClick();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(accountProfilePage.getLogoutButton())); // Замените "elementId" на идентификатор элемента, который ожидается
        } catch (TimeoutException e) {
            Assert.fail("Элемент не появился за отведенное время");
        }

        authorizationPage = accountProfilePage.logoutButtonClick();
        try {
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(authorizationPage.getEnterScript())); // Замените "elementId" на идентификатор элемента, который ожидается
        } catch (TimeoutException e) {
            Assert.fail("Элемент не появился за отведенное время");
        }
        assertEquals("Вход",authorizationPage.enterScriptGetText());
    }

    @After
    @Step("Закрытие браузера")
    public void tearDown() {
        try {
            //Удаляем пользователя
            String token = User.login(email, password);
            User.delete(token);
        } finally {
            // Закрой браузер
            driver.quit();
        }
    }
}