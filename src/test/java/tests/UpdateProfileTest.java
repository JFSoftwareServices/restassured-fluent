package tests;

import io.restassured.response.Response;
import model.Profile;
import model.UserLoginDetails;
import org.junit.Test;
import service.AuthenticationService;
import service.UpdateProfileService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class UpdateProfileTest extends TestBase {
    @Test
    public void updateProfileTest() {
        //Arrange
        UserLoginDetails userLoginDetails = UserLoginDetails.DEFAULT_AUTHENTICATION_DATA;
        String token = AuthenticationService.authenticate(userLoginDetails);

        //Act
        Profile updatedProfile = new Profile("Tom", 2);
        Response response = UpdateProfileService
                .setPath("/posts/2/profile")
                .setToken(token)
                .setProfile(updatedProfile)
                .updateProfile();

        //Assert
        Profile profile = response.getBody().as(Profile.class);
        assertThat(profile.getName(), is(equalTo("Tom")));
    }
}