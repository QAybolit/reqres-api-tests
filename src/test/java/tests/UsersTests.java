package tests;

import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Owner("Dina")
@Story("Проверка функциональности API для управления пользователями")
public class UsersTests extends BaseTest {

    @Test
    @DisplayName("Получение списка пользователей")
    public void getUsersTest() {
        given()
                .spec(requestSpec)
                .queryParam("page", 2)
                .when()
                .get("/api/users")
                .then()
                .log().body()
                .statusCode(200)
                .body("total", is(12))
                .body("data.size()", is(6));
    }

    @Test
    @DisplayName("Создание пользователя")
    public void createUserTest() {
        String str = """
                {
                  "email": "eve.holt@reqres.in",
                  "password": "cityslicka"
                }
                """;

        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body(str)
                .when()
                .post("/api/register")
                .then()
                .log().body()
                .statusCode(200)
                .body("token", equalTo("QpwL5tke4Pnpja7X4"));
    }

    @Test
    @DisplayName("Получение пользователя по ID")
    public void getUserByIdTest() {
        int userId = 4;

        given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .when()
                .get("/api/users/{id}")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.name", equalTo("aqua sky"))
                .body("data.year", is(2003))
                .body("data.color", equalTo("#7BC4C4"));
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    public void updateUserInfoTest() {
        int userId = 3;

        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .pathParam("id", userId)
                .body("{}")
                .when()
                .put("/api/users/{id}")
                .then()
                .log().body()
                .statusCode(200)
                .body("updatedAt", notNullValue());
    }

    @Test
    @DisplayName("Частичное обновление данных пользователя")
    public void partialUpdateUserInfoTest() {
        int userId = 2;

        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .pathParam("id", userId)
                .body("{}")
                .when()
                .patch("/api/users/{id}")
                .then()
                .log().body()
                .statusCode(200)
                .body("updatedAt", notNullValue());
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void deleteUserTest() {
        int userId = 2;

        given()
                .spec(requestSpec)
                .pathParam("id", userId)
                .when()
                .delete("/api/users/{id}")
                .then()
                .log().body()
                .statusCode(204);
    }
}
