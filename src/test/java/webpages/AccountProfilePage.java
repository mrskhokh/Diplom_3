package webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountProfilePage {
    public By getNameField() {
        return nameField;
    }

    private final By nameField = By.xpath("//input[@name='Name']");
    private final By constructorLink = By.xpath("//p[text()='Конструктор']");

    public By getLogoutButton() {
        return logoutButton;
    }

    private final By logoutButton = By.xpath("//button[text()='Выход']");

    private final WebDriver driver;

    public AccountProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    @Step("Смотрим что написано в поле Имя")
    public String getNameValue() {
        return driver.findElement(nameField).getAttribute("value");
    }

    @Step("Клик по кнопке 'Конструктор'")
    public void constructorLinkClick() {
        driver.findElement(constructorLink).click();
    }

    @Step("Клик по кнопке 'Выйти'")
    public AuthorizationPage logoutButtonClick() {
        driver.findElement(logoutButton).click();
        return new AuthorizationPage(driver);
    }
}
