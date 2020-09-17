package tests;

import io.restassured.response.Response;
import model.Post;
import model.UserLoginDetails;
import org.junit.Test;
import service.AuthenticationService;
import service.CreatePostService;
import service.DeletePostService;
import service.RetrievePostService;
import service.util.RandomIdGenerator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

//TODO parameterize
public class DeletePostTest extends TestBase {
    @Test
    public void deletePostTest() {
        //Arrange
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
        DeletePostService
                .setPath("/posts/" + postId)
                .setToken(token)
                .deletePost();
        Response response = RetrievePostService
                .setPath("/posts/" + postId)
                .setToken(token)
                .retrievePost();

        //Assert
        post = response.getBody().as(Post.class);
        assertThat(post.getId(), is(equalTo(0)));
        assertThat(post.getTitle(), is(equalTo(null)));
        assertThat(post.getAuthor(), is(equalTo(null)));
    }
}