package tests;

import io.restassured.response.Response;
import model.Post;
import model.UserLoginDetails;
import org.junit.Test;
import service.AuthenticationService;
import service.CreatePostService;
import service.RetrievePostService;
import service.UpdatePostService;
import service.util.RandomIdGenerator;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

//TODO parameterize
public class UpdatePostTest extends TestBase {
    @Test
    public void updatePostTest() {
        //Assemble
        UserLoginDetails userLoginDetails = UserLoginDetails.DEFAULT_AUTHENTICATION_DATA;
        String token = AuthenticationService.authenticate(userLoginDetails);
        int postId = RandomIdGenerator.generate();
        Post post = Post.builder().id(postId).title("Mr").author("Ade").build();
        CreatePostService
                .setPath("/posts")
                .setToken(token)
                .setPost(post)
                .createPost();

        //Act
        post = Post.builder().id(postId).title("Master").author("Ade").build();
        UpdatePostService
                .setPath("/posts/" + postId)
                .setToken(token)
                .setPost(post)
                .updatePost();
        Response response = RetrievePostService
                .setPath("/posts/" + postId)
                .setToken(token)
                .retrievePost();

        //Assert
        post = response.getBody().as(Post.class);
        assertThat(post.getId(), is(equalTo(postId)));
        assertThat(post.getTitle(), is(equalTo("Master")));
        assertThat(post.getAuthor(), is(equalTo("Ade")));
        assertThat(response.getBody().asString(), matchesJsonSchemaInClasspath("schemas/post.json"));
    }
}