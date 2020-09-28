package tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import model.UserLoginDetails;

import java.util.List;

class DefaultRequestSpecification {
    private static final String BASE_URI = "http://localhost";
    private static final int PORT = 3000;

    static RequestSpecification buildDefaultRequestSpecification() {
        final RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(BASE_URI);
        requestSpecBuilder.setPort(PORT);
        requestSpecBuilder.setBody(UserLoginDetails.DEFAULT_USER_LOGIN_DETAILS);
        requestSpecBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setAccept(ContentType.JSON);
        return requestSpecBuilder.build();
    }
}