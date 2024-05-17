package io.quarkiverse.quarkus.easy.retrofit.it;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EasyRetrofitResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/easy-retrofit")
                .then()
                .statusCode(200)
                .body(is("Hello easy-retrofit"));
    }
}
