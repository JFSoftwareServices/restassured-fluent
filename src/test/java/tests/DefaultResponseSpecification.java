package tests;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

class DefaultResponseSpecification {
    static ResponseSpecification buildDefaultResponseSpecification() {
        final ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType(ContentType.JSON);
        return responseSpecBuilder.build();
    }
}