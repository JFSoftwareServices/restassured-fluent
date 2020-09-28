package tests;

import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.Post;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static tests.Authentication.authenticate;
import static tests.DefaultJsonSchemaFactory.buildDefaultJsonSchemaFactory;
import static tests.DefaultRequestSpecification.buildDefaultRequestSpecification;
import static tests.DefaultResponseSpecification.buildDefaultResponseSpecification;

@TestInstance(Lifecycle.PER_CLASS)
class RequestPostTest {
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
    void requestPostTest() {
        final ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpec)
                .basePath("/posts/1")
                .when()
                .get()
                .then()
                .spec(responseSpec)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/post.json"));

        final Post actualPost = validatableResponse.extract().body().as(Post.class);
        final Post expectedPost = Post.builder().id(1).title("Selenium with C#").author("Karthik KK").build();
        assertThat(actualPost, is(equalTo(expectedPost)));
    }

    @Test
    void requestPostsTest() {
        //retrieve posts
        final ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpec)
                .basePath("/posts")
                .when()
                .get()
                .then()
                .spec(responseSpec)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
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

    @AfterAll
    void tearDown() {
        JsonSchemaValidator.reset();
    }
}