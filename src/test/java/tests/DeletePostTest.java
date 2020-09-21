package tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Post;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import service.util.RandomIdGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

final class DeletePostTest extends TestBase {
    @Test
    final void deletePostTest() {
        authenticate();

        final int postId = RandomIdGenerator.generate();
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