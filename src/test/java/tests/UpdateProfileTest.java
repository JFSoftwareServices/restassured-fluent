package tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Profile;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

final class UpdateProfileTest extends TestBase {
    @Test
    void updateProfileTest() {
        authenticate();

        //update post
        final ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpec)
                .with()
                .basePath("/posts/2/profile")
                .and()
                .body(Profile.builder().name("Tom").postId(2).build())
                .when()
                .post()
                .then()
                .spec(responseSpec)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);

        //assert post
        final Profile actualProfile = validatableResponse.extract().body().as(Profile.class);
        final Profile expectedProfile = Profile.builder().name("Tom").postId(2).build();
        assertThat(actualProfile, is(expectedProfile));
    }
}