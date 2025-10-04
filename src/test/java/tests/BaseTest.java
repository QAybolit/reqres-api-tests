package tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    public static RequestSpecification requestSpec;

    @BeforeAll
    public static void setUp() {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in")
                .addHeader("x-api-key", "reqres-free-v1")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured());
        requestSpec = builder.build();
    }
}
