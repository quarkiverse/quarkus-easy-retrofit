package io.quarkiverse.retrofit.easy.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.ValidatableResponse;

@QuarkusTest
public class EasyRetrofitResourceTest {

    @Test
    public void testGetContext() {
        given()
                .when().get("/easy-retrofit/context")
                .then()
                .statusCode(200);
    }

    @Test
    public void testHelloEndpoint() {
        ValidatableResponse validatableResponse = given()
                .when().get("/easy-retrofit/hello")
                .then()
                .statusCode(200);

        validatableResponse.body(is("hello retrofit baseApi"));
    }
}
