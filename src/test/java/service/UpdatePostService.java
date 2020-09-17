package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import model.Post;
import request.HttpMethod;
import service.util.AuthenticationConstants;

public class UpdatePostService {
    private static String path;
    private Post post;
    private String token;

    public static UpdatePostService setPath(String path) {
        UpdatePostService.path = path;
        return new UpdatePostService();
    }

    public Response updatePost() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBasePath(path)
                .addHeader(AuthenticationConstants.AUTHORIZATION, AuthenticationConstants.BEARER + " " + token)
                .setBody(post)
                .build();
        return RestfulApiService.send(builder, HttpMethod.PUT);
    }

    public UpdatePostService setToken(String token) {
        this.token = token;
        return this;
    }

    public UpdatePostService setPost(Post post) {
        this.post = post;
        return this;
    }
}
