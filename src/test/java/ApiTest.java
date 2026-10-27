
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class ApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 7070;
    }

    @Test
    void shouldGetProducts() {
        given()
                .baseUri("http://localhost:7070")
                .when()
                .get("/products")
                .then()
                .statusCode(200);
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
