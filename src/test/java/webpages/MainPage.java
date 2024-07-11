package webpages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage {


    private final By profileCabinetLink = By.xpath("//a[@class='AppHeader_header__link__3D_hX' and p[contains(text(),'Личный Кабинет')]]");
    private final By accountEnterButton = By.xpath("//button[contains(text(),'Войти в аккаунт') and contains(@class, 'button_button__33qZ0')]");

    private final By bunsLink = By.xpath("//div[contains(@class, 'tab_tab') and .//span[contains(@class, 'text') and text()='Булки']]");

    private final By saucesLink = By.xpath("//div[contains(@class, 'tab_tab') and .//span[contains(@class, 'text') and text()='Соусы']]");

    private final By fillingsLink = By.xpath("//div[contains(@class, 'tab_tab') and .//span[contains(@class, 'text') and text()='Начинки']]");

    private final By bunsSectionName = By.xpath("//h2[contains(text(), 'Булки') and contains(@class, 'text text_type_main-medium mb-6 mt-10')]");

    private final By saucesSectionName = By.xpath("//h2[text()='Соусы']");

    private final By fillingsSectionName = By.xpath("//h2[text()='Начинки']");

    private final WebDriver driver;

    private final By constructorName = By.xpath("//h1[text()='Соберите бургер']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }



    public By getProfileCabinetLink() {
        return profileCabinetLink;
    }

    @Step("Клик по кнопке 'Личный кабинет'")
    public AccountProfilePage authorizedProfileCabinetLinkClick() {
        driver.findElement(profileCabinetLink).click();
        return new AccountProfilePage(driver);
    }

    @Step("Клик по кнопке 'Личный кабинет'")
    public AuthorizationPage profileCabinetLinkClick() {
        driver.findElement(profileCabinetLink).click();
        return new AuthorizationPage(driver);
    }

    @Step("Клик по кнопке 'Войти в аккаунт'")
    public AuthorizationPage accountEnterButtonClick() {
        driver.findElement(accountEnterButton).click();
        return new AuthorizationPage(driver);
    }

    @Step("Получаем текст из элемента Конструктор")
    public String constructorNameGetText() {
        return driver.findElement(constructorName).getText();
    }


    @Step("Клик по 'Соусы'")
    public void saucesLinkClick() {
        driver.findElement(saucesLink).click();
    }

    @Step("Клик по 'Начинки'")
    public void fillingsLinkClick() {
        driver.findElement(fillingsLink).click();
    }

    @Step("Клик по 'Булки'")
    public void bunsLinkClick() {
        driver.findElement(bunsLink).click();
    }

    @Step("Проверяем наличие секции Начинки")
    public Boolean fillingsSectionNameIsVisible() {
        return driver.findElement(fillingsSectionName).isDisplayed();
    }

    @Step("Проверяем наличие секции Соусы")
    public Boolean saucesSectionNameVisible() {
        return driver.findElement(saucesSectionName).isDisplayed();
    }

    @Step("Проверяем наличие секции Булки")
    public Boolean bunsSectionNameIsVisible() {
        return driver.findElement(bunsSectionName).isDisplayed();
    }
}


