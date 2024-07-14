package webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthorizationPage {
    private final By emailField = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordFiled = By.xpath("//label[text()='Пароль']/following-sibling::input");
    private final By loginButton = By.xpath("//button[text()='Войти']");

    public By getEnterScript() {
        return enterScript;
    }

    private final By enterScript = By.xpath("//h2[text()='Вход']");
    private final By slellarBurgerLink = By.xpath("//a[@class='AppHeader_header__link__3D_hX' and @href='/']");

    private final By enterInscript = By.xpath("//h2[text()='Вход']");
    private final WebDriver driver;

    public By getEnterInscript() {
        return enterInscript;
    }


    public AuthorizationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Вытаскиваем название из элемент Вход")
    public String enterScriptGetText() {
        return driver.findElement(enterScript).getText();
    }

    @Step("Запоняем email")
    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    @Step("Заполняем пароль")
    public void setPassword(String password) {
        driver.findElement(passwordFiled).sendKeys(password);
    }

    @Step("Нажимаем 'Войти'")
    public MainPage loginButtonClick() {
        driver.findElement(loginButton).click();
        return new MainPage(driver);
    }

    @Step("Клик по основной ссылке StellarBurgers")
    public MainPage stellarBurgerLinkClick() {
        driver.findElement(slellarBurgerLink).click();
        return new MainPage(driver);
    }

    @Step("Выполняется авторизация")
    public MainPage authorization(String email, String password) {
        setEmail(email);
        setPassword(password);
        return loginButtonClick();
    }
}