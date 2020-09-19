package tests;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import model.Post;
import org.junit.jupiter.api.Test;
import service.util.RandomIdGenerator;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

//TODO parameterize
class UpdatePostTest extends TestBase {
    @Test
    void updatePostTest() {
        int postId = RandomIdGenerator.generate();
        //create post
        Post post = Post.builder().id(postId).title("Mr").author("Ade").build();
        RestAssured
                .given()
                .spec(requestSpecification)
                .basePath("/posts")
                .body(post)
                .when()
                .post()
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(201);

        //update post
        post = Post.builder().id(postId).title("Master").author("Ade").build();
        RestAssured
                .given()
                .spec(requestSpecification)
                .basePath("/posts" + "/" + postId)
                .body(post)
                .when()
                .put()
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(200);

        //get post
        ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath("/posts" + "/" + postId)
                .body(post)
                .when()
                .get()
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(200)
                .and()
                .assertThat().body(matchesJsonSchemaInClasspath("schemas/post.json").using(jsonSchemaFactory));

        //assert
        Post actualPost = validatableResponse.extract().body().as(Post.class);
        Post expectedPost = Post.builder().id(postId).title("Master").author("Ade").build();
        assertThat(actualPost, is(equalTo(expectedPost)));
    }
}