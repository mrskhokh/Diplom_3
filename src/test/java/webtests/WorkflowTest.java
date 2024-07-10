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
import webpages.AccountProfilePage;
import webpages.AuthorizationPage;
import webpages.MainPage;
import webtestutils.AbstractTest;
import webtestutils.TestUtils;
import webtestutils.User;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class WorkflowTest extends AbstractTest {
    private final String email;

    private final String password;
    private final String name;
    WebDriver driver;

    public WorkflowTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru",
                        "12345678", "Иванов Иван"}}
        );
    }

    @DisplayName("Проверка ссылки конструктора")
    @Test
    public void constructorLinkTest() {
        User.create(email, password, name);
        driver = getDriver();
        // переход на страницу регистрации приложения
        driver.get(AbstractTest.mainPageUrl);
        MainPage mainpage = new MainPage(driver);
        AuthorizationPage authorizationPage = mainpage.profileCabinetLinkClick();

        MainPage mainPage = authorizationPage.authorization(email, password);
//        try {
//            Thread.sleep(1000); // Пауза на секунду
//        } catch (InterruptedException e) {
//            Assert.fail(e.getMessage());
//        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Указываем максимальное время ожидания в секундах

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(mainPage.getProfileCabinetLink())); // Замените "elementId" на идентификатор элемента, который ожидается
        } catch (TimeoutException e) {
            Assert.fail("Элемент не появился за отведенное время");
        }

        AccountProfilePage accountProfilePage = mainpage.authorizedProfileCabinetLinkClick();
        accountProfilePage.constructorLinkClick();
        assertEquals(mainPage.constructorNameGetText(), "Соберите бургер");
        String token = User.login(email, password);
        User.delete(token);
    }

    @DisplayName("Проверка секции ингредиентов")
    @Test
    public void ingredientsSectionTest() {

        driver = getDriver();
        // переход на страницу регистрации приложения
        driver.get(AbstractTest.mainPageUrl);
        MainPage mainpage = new MainPage(driver);

        assertEquals(mainpage.bunsSectionNameIsVisible(), true);

        mainpage.fillingsLinkClick();
        assertEquals(mainpage.fillingsSectionNameIsVisible(), true);

        mainpage.saucesLinkClick();
        assertEquals(mainpage.saucesSectionNameVisible(), true);
    }

    @DisplayName("Проверка осной ссылки StellarBurger на главную страницу")
    @Test
    public void mainLinkTest() {
        driver = getDriver();
        // переход на страницу регистрации приложения
        driver.get(AbstractTest.mainPageUrl);

        MainPage mainpage = new MainPage(driver);
        AuthorizationPage authorizationPage = mainpage.profileCabinetLinkClick();
        MainPage mainPage = authorizationPage.stellarBurgerLinkClick();
        assertEquals(mainPage.constructorNameGetText(), "Соберите бургер");
    }


    @After
    public void tearDown() {
        // Закрываем браузер
        driver.quit();
    }
}

