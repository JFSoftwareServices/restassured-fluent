package tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Profile;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

final class CreateProfileTest extends TestBase {
    @Test
    void createProfileTest() {
        final ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath("/posts/{postId}/profile")
                .pathParams(Map.of("postId", 3))
                .body(Profile.builder().name("Sams").postId(3).build())
                .when()
                .post()
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(201);

        //assert post
        final Profile actualProfile = validatableResponse.extract().response().getBody().as(Profile.class);
        final Profile expectedProfile = Profile.builder().name("Sams").postId(3).build();
        assertThat(actualProfile, is(expectedProfile));
    }
}