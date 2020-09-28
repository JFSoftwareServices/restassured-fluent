package tests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import static org.apache.http.HttpHeaders.AUTHORIZATION;

class Authentication {
    private static final String BEARER = "Bearer";
    private static final String ACCESS_TOKEN_PATH = "access_token";
    private static final String BASE_PATH = "/auth/login";

    static void authenticate(RequestSpecification requestSpec, ResponseSpecification responseSpec) {
        final String token = RestAssured
                .given()
                .spec(requestSpec)
                .basePath(BASE_PATH)
                .when()
                .post()
                .then()
                .spec(responseSpec)
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .body()
                .path(ACCESS_TOKEN_PATH);
        requestSpec.header(AUTHORIZATION, BEARER + " " + token);
    }
}