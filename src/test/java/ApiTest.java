
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class ApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7070;
    }

    @Test
    void productsWithoutTokenShouldReturn401() {
        given()
                .when()
                .get("/api/products")
                .then()
                .statusCode(401);
    }

    @Test
    void unknownEndpointShouldReturn404() {
        given()
                .when()
                .get("/this-endpoint-does-not-exist")
                .then()
                .statusCode(404);
    }
}
