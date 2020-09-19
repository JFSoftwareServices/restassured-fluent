package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import model.Post;
import org.junit.Test;
import service.util.RandomIdGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

//TODO parameterize
public class DeletePostTest extends TestBase {
    @Test
    public void deletePostTest() {
        int postId = RandomIdGenerator.generate();
        //create post
        Post post = Post.builder().id(postId).title("Mr").author("Ade").build();
        RestAssured
                .given()
                .spec(requestSpecification)
                .basePath("/posts")
                .body(post)
                .when()
                .post();

        //delete post
        post = Post.builder().id(postId).title("Master").author("Ade").build();
        RestAssured
                .given()
                .spec(requestSpecification)
                .basePath("/posts" + "/" + postId)
                .body(post)
                .when()
                .delete();

        //get post
        ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath("/posts" + "/" + postId)
                .when()
                .get()
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(404);

        //assert
        post = validatableResponse.extract().response().getBody().as(Post.class);
        assertThat(post.getId(), is(equalTo(0)));
        assertThat(post.getTitle(), is(equalTo(null)));
        assertThat(post.getAuthor(), is(equalTo(null)));
    }
}