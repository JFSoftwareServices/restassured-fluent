package tests;

import com.google.gson.reflect.TypeToken;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import model.Address;
import model.Location;
import model.UserLoginDetails;
import org.junit.Test;
import service.AuthenticationService;
import service.RetrieveLocationService;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RequestLocationTest extends TestBase {
    @Test
    public void requestLocationTest() {
        //Assemble
        UserLoginDetails userLoginDetails = UserLoginDetails.DEFAULT_AUTHENTICATION_DATA;
        String token = AuthenticationService.authenticate(userLoginDetails);

        //Act
        Response response = RetrieveLocationService
                .setPath("/location")
                .setQueryParams(Map.of("id", 1))
                .setToken(token)
                .retrieveLocation();

        //Assert
        Type type = new TypeToken<List<Location>>() {
        }.getType();
        List<Location> locations = response.getBody().as(type, ObjectMapperType.GSON);
        assertThat(locations.get(0).getAddress().get(0), is(equalTo(Address.builder()
                .street("1st street")
                .flat_no("4A")
                .pincode(321421)
                .type("primary").build())));
    }
}