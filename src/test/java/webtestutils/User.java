package webtestutils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class User  {


    @Step("Создание пользователя по API")
    public static void create(String email, String password, String name) {

        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);
        requestBody.put("name", name);

        given()
                .header("Content-type", "application/json")
                .body(requestBody.toString())
                .when()
                .post(TestConstants.API_REGISTER_URL);
    }

    @Step("Вход поль зователя по API")
    public static String login(String email, String password) {
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", email);
        requestBody.put("password", password);
        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody.toString())
                .when()
                .post(TestConstants.API_LOGIN_URL);

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
                .delete(TestConstants.API_DELETE_USER_URL);

    }
}


