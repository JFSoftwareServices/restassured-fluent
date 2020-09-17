package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import request.HttpMethod;
import service.util.AuthenticationConstants;

public class RetrievePostService {
    private static String path;
    private String token;

    public static RetrievePostService setPath(String path) {
        RetrievePostService.path = path;
        return new RetrievePostService();
    }

    public Response retrievePost() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBasePath(path)
                .addHeader(AuthenticationConstants.AUTHORIZATION, AuthenticationConstants.BEARER + " " + token)
                .build();
        return RestfulApiService.send(builder, HttpMethod.GET);
    }

    public RetrievePostService setToken(String token) {
        this.token = token;
        return this;
    }
}