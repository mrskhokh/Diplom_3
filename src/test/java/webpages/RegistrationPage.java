package webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage {
    private final By name = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By email = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By password = By.xpath("//input[@name='Пароль']");
    private final By registrationButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By wrongPasswordMessage = By.xpath("//p[contains(@class, 'input__error') and contains(@class, 'text_type_main-default')]");

    private final By loginLink = By.xpath("//a[text()='Войти']");
    private final WebDriver driver;


    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Заполняем имя")
    public void setName(String value) {
        driver.findElement(name).sendKeys(value);
    }
    @Step("Заполняем email")
    public void setEmail(String value) {
        driver.findElement(email).sendKeys(value);
    }

    @Step("Заполняем пароль")
    public void setPassword(String value) {
        driver.findElement(password).sendKeys(value);
    }

    @Step("Клик по кнопке 'Зарегистрироваться'")
    public void registrationButtonClick() {
        driver.findElement(registrationButton).click();
    }

    @Step("Получаем текст из сообщения о неверном пароле")
    public String wrongPasswordMessageGetText() {
        return driver.findElement(wrongPasswordMessage).getText();
    }

    @Step("Клик по ссылке 'Войти'")
    public AuthorizationPage loginLinkClick(){
        driver.findElement(loginLink).click();
        return new AuthorizationPage(driver);
    }
}

