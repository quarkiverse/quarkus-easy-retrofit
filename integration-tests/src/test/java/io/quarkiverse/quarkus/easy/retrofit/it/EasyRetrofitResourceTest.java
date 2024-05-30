package io.quarkiverse.quarkus.easy.retrofit.it;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EasyRetrofitResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .when().get("/easy-retrofit/context")
                .then()
                .statusCode(200);
    }
}
