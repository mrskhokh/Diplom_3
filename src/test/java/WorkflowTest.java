import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import stellarburgers.yandex.AccountProfilePage;
import stellarburgers.yandex.AuthorizationPage;
import stellarburgers.yandex.MainPage;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class WorkflowTest extends AbstractTest {
    private final String email;

    private final Browser browser;
    private final String password;
    private final String name;
    WebDriver driver;

    public WorkflowTest(Browser browser, String email, String password, String name) {
        this.browser = browser;
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Browser.CHROME, TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru",
                        "12345678", "Иванов Иван"},
                {Browser.YA, TestUtils.generateRandomNumbers(4) + "mrskhokhkhokh@ya.ru",
                        "12345678", "Иванов Иван"}});
    }

    @Test
    public void constructorLinkTest() {
        User.create(email, password, name);
        driver = getDriver(browser);
        // переход на страницу регистрации приложения
        String url = "https://stellarburgers.nomoreparties.site";
        driver.get(url);
        MainPage mainpage = new MainPage(driver);
        AuthorizationPage authorizationPage = mainpage.profileCabinetLinkClick();

        MainPage mainPage = authorizationPage.authorization(email, password);
        try {
            Thread.sleep(1000); // Пауза на секунду
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
        AccountProfilePage accountProfilePage = mainpage.authorizedProfileCabinetLinkClick();
        accountProfilePage.constructorLinkClick();
        assertEquals(mainPage.constructorNameGetText(), "Соберите бургер");
        String token = User.login(email, password);
        User.delete(token);
    }

    @Test
    public void ingredientsSectionTest() {
        driver = getDriver(browser);
        // переход на страницу регистрации приложения
        String url = "https://stellarburgers.nomoreparties.site";
        driver.get(url);
        MainPage mainpage = new MainPage(driver);

        mainpage.fillingsLinkClick();
        assertEquals(mainpage.fillingsSectionNameIsVisible(), true);
        try {
            Thread.sleep(1000); // Пауза на секунду
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }

        mainpage.saucesLinkClick();
        assertEquals(mainpage.saucesSectionNameVisible(), true);
        try {
            Thread.sleep(1000); // Пауза на секунду
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }

        mainpage.bunsLinkClick();
        assertEquals(mainpage.bunsSectionNameIsVisible(), true);
    }

    @Test
    public void mainLinkTest() {
        driver = getDriver(browser);
        // переход на страницу регистрации приложения
        String url = "https://stellarburgers.nomoreparties.site";
        driver.get(url);

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

