package tests;

import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;
import model.Post;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

final class RequestPostTest extends TestBase {
    @Test
    void requestPostTest() {
        final ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath("/posts/1")
                .when()
                .get()
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(200)
                .and()
                .assertThat().body(matchesJsonSchemaInClasspath("schemas/post.json"));

        final Post actualPost = validatableResponse.extract().body().as(Post.class);
        final Post expectedPost = Post.builder().id(1).title("Selenium with C#").author("Karthik KK").build();
        assertThat(actualPost, is(equalTo(expectedPost)));
    }

    @Test
    void requestPostsTest() {
        //retrieve posts
        final ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath("/posts")
                .when()
                .get()
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(200)
                .and()
                .assertThat().body(matchesJsonSchemaInClasspath("schemas/posts.json").using(jsonSchemaFactory));

        //assert post
        final Type type = new TypeToken<List<Post>>() {
        }.getType();
        final List<Post> posts = validatableResponse.extract().body().as(type, ObjectMapperType.GSON);
        assertThat(posts.get(0).getAuthor(), equalTo("Karthik KK"));
        assertThat(posts.get(1).getAuthor(), equalTo("Karthik KK"));
        assertThat(posts.get(0).getTitle(), equalTo("Selenium with C#"));
        assertThat(posts.get(1).getTitle(), equalTo("Appium"));
    }
}