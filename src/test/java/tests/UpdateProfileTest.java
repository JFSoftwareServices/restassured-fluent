package tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Profile;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class UpdateProfileTest extends TestBase {
    @Test
    void updateProfileTest() {
        ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpecification)
                .with()
                .basePath("/posts/2/profile")
                .and()
                .body(Profile.builder().name("Tom").postId(2).build())
                .when()
                .post()
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(201);

        Profile actualProfile = validatableResponse.extract().body().as(Profile.class);
        Profile expectedProfile = Profile.builder().name("Tom").postId(2).build();
        assertThat(actualProfile, is(expectedProfile));
    }
}