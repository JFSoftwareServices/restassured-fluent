package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import request.HttpMethod;
import service.util.AuthenticationConstants;

import java.util.Map;

public class RetrieveLocationService {
    private static String path;
    private String token;
    private Map<String, Integer> params;

    public static RetrieveLocationService setPath(String path) {
        RetrieveLocationService.path = path;
        return new RetrieveLocationService();
    }

    public Response retrieveLocation() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBasePath(path)
                .addHeader(AuthenticationConstants.AUTHORIZATION, AuthenticationConstants.BEARER + " " + token)
                .addQueryParams(params)
                .build();
        return RestfulApiService.send(builder, HttpMethod.GET);
    }

    public RetrieveLocationService setToken(String token) {
        this.token = token;
        return this;
    }

    public RetrieveLocationService setQueryParams(Map<String, Integer> params) {
        this.params = params;
        return this;
    }
}