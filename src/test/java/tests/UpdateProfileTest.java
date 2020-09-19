package tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Profile;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UpdateProfileTest extends TestBase {
    @Test
    public void updateProfileTest() {
        ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpecification)
                .with()
                .basePath("/posts/2/profile")
                .and()
                .body(new Profile("Tom", 2))
                .when()
                .post()
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(201);

        Profile actualProfile = validatableResponse.extract().body().as(Profile.class);
        Profile expectedProfile = new Profile("Tom", 2);
        assertThat(actualProfile, is(expectedProfile));
    }
}