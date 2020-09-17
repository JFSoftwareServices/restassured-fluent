package tests;

import com.google.gson.reflect.TypeToken;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import model.Post;
import model.UserLoginDetails;
import org.junit.Test;
import service.AuthenticationService;
import service.RetrievePostService;

import java.lang.reflect.Type;
import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RequestPostTest extends TestBase { //TODO parameterize
    @Test
    public void requestPostTest() {
        //Assemble
        UserLoginDetails userLoginDetails = UserLoginDetails.DEFAULT_AUTHENTICATION_DATA;
        String token = AuthenticationService.authenticate(userLoginDetails);

        //Act
        Response response = RetrievePostService
                .setPath("/posts/1")
                .setToken(token)
                .retrievePost();

        //Assert
        Post post = response.getBody().as(Post.class);
        assertThat(post.getId(), is(equalTo(1)));
        assertThat(post.getTitle(), is(equalTo("Selenium with C#")));
        assertThat(post.getAuthor(), is(equalTo("Karthik KK")));
        assertThat(response.getBody().asString(), matchesJsonSchemaInClasspath("schemas/post.json"));
    }

    @Test
    public void requestPostsTest() {
        // Assemble
        UserLoginDetails userLoginDetails = UserLoginDetails.DEFAULT_AUTHENTICATION_DATA;
        String token = AuthenticationService.authenticate(userLoginDetails);

        //Act
        Response response = RetrievePostService
                .setPath("/posts")
                .setToken(token)
                .retrievePost();

        //Assert
        Type type = new TypeToken<List<Post>>() {
        }.getType();
        List<Post> posts = response.getBody().as(type, ObjectMapperType.GSON);
        assertThat(posts.get(0).getAuthor(), equalTo("Karthik KK"));
        assertThat(posts.get(1).getAuthor(), equalTo("Karthik KK"));
        assertThat(posts.get(0).getTitle(), equalTo("Selenium with C#"));
        assertThat(posts.get(1).getTitle(), equalTo("Appium"));
        assertThat(response.getBody().asString(), matchesJsonSchemaInClasspath("schemas/posts.json"));
    }
}