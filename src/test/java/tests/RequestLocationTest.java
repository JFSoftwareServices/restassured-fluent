package tests;

import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.Address;
import model.Location;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static tests.Authentication.authenticate;
import static tests.DefaultRequestSpecification.buildDefaultRequestSpecification;
import static tests.DefaultResponseSpecification.buildDefaultResponseSpecification;

@TestInstance(Lifecycle.PER_CLASS)
class RequestLocationTest {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;

    @BeforeAll
    void setUp() {
        requestSpec = buildDefaultRequestSpecification();
        responseSpec = buildDefaultResponseSpecification();
        authenticate(requestSpec, responseSpec);
    }

    @Test
    void requestLocationTest() {
        final ValidatableResponse validatableResponse = RestAssured
                .given()
                .spec(requestSpec)
                .with()
                .basePath("/location")
                .and()
                .queryParams(Map.of("id", 1))
                .when()
                .get()
                .then()
                .spec(responseSpec)
                .assertThat()
                .statusCode(HttpStatus.SC_OK);

        //assert post
        final Type type = new TypeToken<List<Location>>() {
        }.getType();
        final List<Location> locations = validatableResponse.extract().body().as(type, ObjectMapperType.GSON);
        assertThat(locations.get(0).getAddress().get(0), is(equalTo(Address.builder()
                .street("1st street")
                .flat_no("4A")
                .pincode(321421)
                .type("primary").build())));
    }
}