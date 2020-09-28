package tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.Post;
import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static tests.Authentication.authenticate;
import static tests.DefaultRequestSpecification.buildDefaultRequestSpecification;
import static tests.DefaultResponseSpecification.buildDefaultResponseSpecification;

@TestInstance(Lifecycle.PER_CLASS)
class DeletePostTest {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @BeforeAll
    void setUp() {
        requestSpec = buildDefaultRequestSpecification();
        responseSpec = buildDefaultResponseSpecification();
        authenticate(requestSpec, responseSpec);
    }

    @Test
    final void deletePostTest() {

        final int postId = RandomUtils.nextInt();
        //create post
        final Post postRequestBody = Post.builder().id(postId).title("Mr").author("Ade").build();
        RestAssured
                .given()
                .spec(requestSpec)
                .basePath("/posts")
                .body(postRequestBody)
                .when()
                .post()
                .then()
                .spec(responseSpec)
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
        //delete post
        RestAssured
                .given()
                .spec(requestSpec)
                .basePath("/posts" + "/" + postId)
                .when()
                .delete()
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
                .statusCode(HttpStatus.SC_NOT_FOUND);

        //assert retrieved post
        final Post post = validatableResponse.extract().response().getBody().as(Post.class);
        assertThat(post.getId(), is(equalTo(0)));
        assertThat(post.getTitle(), is(equalTo(null)));
        assertThat(post.getAuthor(), is(equalTo(null)));
    }
}