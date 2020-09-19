package tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Profile;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CreateProfileTest extends TestBase { //TODO parameterize
    @Test
    public void createProfileTest() {
        ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath("/posts/{postId}/profile")
                .pathParams(Map.of("postId", 3))
                .body(new Profile("Sams", 3))
                .when()
                .post()
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(201);

        Profile actualProfile = validatableResponse.extract().response().getBody().as(Profile.class);
        Profile expectedProfile = new Profile("Sams", 3);
        assertThat(actualProfile, is(expectedProfile));
    }
}