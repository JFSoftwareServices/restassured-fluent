package tests;

import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.Post;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static tests.Authentication.authenticate;
import static tests.DefaultJsonSchemaFactory.buildDefaultJsonSchemaFactory;
import static tests.DefaultRequestSpecification.buildDefaultRequestSpecification;
import static tests.DefaultResponseSpecification.buildDefaultResponseSpecification;

@TestInstance(Lifecycle.PER_CLASS)
class UpdatePostTest {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
    private JsonSchemaFactory jsonSchemaFactory;

    @BeforeAll
    void setUp() {
        requestSpec = buildDefaultRequestSpecification();
        responseSpec = buildDefaultResponseSpecification();
        authenticate(requestSpec, responseSpec);

        jsonSchemaFactory = buildDefaultJsonSchemaFactory();
    }

    @Test
    void updatePostTest() {
        final int postId = RandomUtils.nextInt();
        //create post
        final Post createPostRequestBody = Post.builder().id(postId).title("Mr").author("Ade").build();
        RestAssured
                .given()
                .spec(requestSpec)
                .basePath("/posts")
                .body(createPostRequestBody)
                .when()
                .post()
                .then()
                .spec(responseSpec)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);

        //update post
        final Post updatePostRequestBody = Post.builder().id(postId).title("Master").author("Ade").build();
        RestAssured
                .given()
                .spec(requestSpec)
                .basePath("/posts" + "/" + postId)
                .body(updatePostRequestBody)
                .when()
                .put()
                .then()
                .spec(responseSpec)
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

        //retrieve post
        final ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpec)
                .basePath("/posts" + "/" + postId)
                .when()
                .get()
                .then()
                .spec(responseSpec)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/post.json")
                        .using(jsonSchemaFactory));

        //assert post
        final Post actualPost = validatableResponse.extract().body().as(Post.class);
        final Post expectedPost = Post.builder().id(postId).title("Master").author("Ade").build();
        assertThat(actualPost, is(equalTo(expectedPost)));
    }

    @AfterAll
    void tearDown() {
        JsonSchemaValidator.reset();
    }
}