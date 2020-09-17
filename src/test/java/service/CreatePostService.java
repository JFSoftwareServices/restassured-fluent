package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import model.Post;
import request.HttpMethod;
import service.util.AuthenticationConstants;

public class CreatePostService {
    private static String path;
    private Post post;
    private String token;

    public static CreatePostService setPath(String path) {
        CreatePostService.path = path;
        return new CreatePostService();
    }

    public Response createPost() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBasePath(path)
                .addHeader(AuthenticationConstants.AUTHORIZATION, AuthenticationConstants.BEARER + " " + token)
                .setBody(post)
                .build();
        return RestfulApiService.send(builder, HttpMethod.POST);
    }

    public CreatePostService setToken(String token) {
        this.token = token;
        return this;
    }

    public CreatePostService setPost(Post post) {
        this.post = post;
        return this;
    }
}