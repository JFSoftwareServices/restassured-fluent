package tests;

import io.restassured.response.Response;
import model.Profile;
import model.UserLoginDetails;
import org.junit.Test;
import service.AuthenticationService;
import service.CreateProfileService;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateProfileTest extends TestBase { //TODO parameterize
    @Test
    public void createProfileTest() {
        //Arrange
        UserLoginDetails userLoginDetails = UserLoginDetails.DEFAULT_AUTHENTICATION_DATA;
        String token = AuthenticationService.authenticate(userLoginDetails);

        //Act
        Profile profile = new Profile("Sams", 3);
        Map<String, Integer> pathParams = Map.of("postId", 3);
        Response response = CreateProfileService
                .setPath("/posts/{postId}/profile")
                .setToken(token)
                .setPathParams(pathParams)
                .setProfile(profile)
                .createProfile();

        //Assert
        profile = response.getBody().as(Profile.class);
        assertThat(profile.getName(), is(equalTo("Sams")));
    }
}