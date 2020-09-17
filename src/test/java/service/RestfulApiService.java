package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import request.HttpMethod;
import request.HttpRequest;

public class RestfulApiService {
    public static Response send(RequestSpecBuilder requestSpecBuilder, HttpMethod httpMethod) {
        requestSpecBuilder.setContentType(ContentType.JSON);
        HttpRequest httpRequest = httpMethod.getHttpRequest();
        return httpRequest.send(requestSpecBuilder.build());
    }
}