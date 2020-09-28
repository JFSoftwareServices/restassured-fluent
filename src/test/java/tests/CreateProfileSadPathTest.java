package tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.Profile;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Map;

import static tests.DefaultRequestSpecification.buildDefaultRequestSpecification;
import static tests.DefaultResponseSpecification.buildDefaultResponseSpecification;

@TestInstance(Lifecycle.PER_CLASS)
class CreateProfileSadPathTest {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @BeforeAll
    void setUp() {
        requestSpec = buildDefaultRequestSpecification();
        responseSpec = buildDefaultResponseSpecification();
    }

    @Test
    void createProfileWithNoAuthenticationTest() {
        //create profile
        RestAssured
                .given()
                .spec(requestSpec)
                .basePath("/posts/{postId}/profile")
                .pathParams(Map.of("postId", 3))
                .body(Profile.builder().name("Sams").postId(3).build())
                .when()
                .post()
                .then()
                .spec(responseSpec)
                .assertThat()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);
    }
}