package tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Profile;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class CreateProfileTest extends TestBase { //TODO parameterize
    @Test
    void createProfileTest() {
        ValidatableResponse validatableResponse = RestAssured
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

        Profile actualProfile = validatableResponse.extract().response().getBody().as(Profile.class);
        Profile expectedProfile = Profile.builder().name("Sams").postId(3).build();
        assertThat(actualProfile, is(expectedProfile));
    }
}