package tests;

import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;
import model.Address;
import model.Location;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

class RequestLocationTest extends TestBase {
    @Test
    void requestLocationTest() {
        ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpecification)
                .with()
                .basePath("/location")
                .and()
                .queryParams(Map.of("id", 1))
                .when()
                .get()
                .then()
                .spec(responseSpecification)
                .assertThat().statusCode(200);

        //assert
        Type type = new TypeToken<List<Location>>() {
        }.getType();
        List<Location> locations = validatableResponse.extract().body().as(type, ObjectMapperType.GSON);
        assertThat(locations.get(0).getAddress().get(0), is(equalTo(Address.builder()
                .street("1st street")
                .flat_no("4A")
                .pincode(321421)
                .type("primary").build())));
    }
}