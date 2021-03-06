package tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.Profile;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static tests.Authentication.authenticate;
import static tests.DefaultRequestSpecification.buildDefaultRequestSpecification;
import static tests.DefaultResponseSpecification.buildDefaultResponseSpecification;

@TestInstance(Lifecycle.PER_CLASS)
class UpdateProfileTest {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @BeforeAll
    void setUp() {
        requestSpec = buildDefaultRequestSpecification();
        responseSpec = buildDefaultResponseSpecification();
        authenticate(requestSpec, responseSpec);
    }

    @Test
    void updateProfileTest() {
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