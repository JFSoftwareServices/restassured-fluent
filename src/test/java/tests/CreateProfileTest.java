package tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Profile;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

final class CreateProfileTest extends TestBase {
    @Test
    void createProfileWithAuthenticationTest() {
        authenticate();

        //create profile
        final ValidatableResponse validatableResponse = RestAssured
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
                .statusCode(HttpStatus.SC_CREATED);

        //assert retrieved post
        final Profile actualProfile = validatableResponse.extract().response().getBody().as(Profile.class);
        final Profile expectedProfile = Profile.builder().name("Sams").postId(3).build();
        assertThat(actualProfile, is(expectedProfile));
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