package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import request.HttpMethod;
import service.util.AuthenticationConstants;

public class DeletePostService {

    private static String path;
    private String token;

    public static DeletePostService setPath(String path) {
        DeletePostService.path = path;
        return new DeletePostService();
    }

    public Response deletePost() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBasePath(path)
                .addHeader(AuthenticationConstants.AUTHORIZATION, AuthenticationConstants.BEARER + " " + token)
                .build();
        return RestfulApiService.send(builder, HttpMethod.DELETE);
    }

    public DeletePostService setToken(String token) {
        this.token = token;
        return this;
    }
}
