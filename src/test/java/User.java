import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class User {

    @Step("Создание пользователя по API")
    public static void create(String email, String password, String name) {

        String json = "{\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"password\": \"" + password + "\",\n" +
                "\"name\": \"" + name + "\"\n" +
                "}";

        given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/auth/register");

    }

    @Step("Вход поль зователя по API")
    public static String login(String email, String password) {
        String json = "{\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"password\": \"" + password + "\"\n" +
                "}";
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .when()
                .post("https://stellarburgers.nomoreparties.site/api/auth/login");

        return response.getBody()
                .jsonPath()
                .getString("accessToken")
                .replace("Bearer", "")
                .trim();
    }


    @Step("Удаление пользователя по API")
    public static void delete(String accessCode) {
        given()
                .auth().oauth2(accessCode)
                .when()
                .delete("https://stellarburgers.nomoreparties.site/api/auth/user");

    }
}


