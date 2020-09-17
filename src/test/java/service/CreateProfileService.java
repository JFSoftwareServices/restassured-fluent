package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import model.Profile;
import request.HttpMethod;
import service.util.AuthenticationConstants;

import java.util.Map;

public class CreateProfileService {
    private static String path;
    private Profile profile;
    private Map<String, Integer> params;
    private String token;

    public static CreateProfileService setPath(String path) {
        CreateProfileService.path = path;
        return new CreateProfileService();
    }

    public Response createProfile() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBasePath(path)
                .addHeader(AuthenticationConstants.AUTHORIZATION, AuthenticationConstants.BEARER + " " + token)
                .addPathParams(params)
                .setBody(profile)
                .build();
        return RestfulApiService.send(builder, HttpMethod.POST);
    }

    public CreateProfileService setToken(String token) {
        this.token = token;
        return this;
    }

    public CreateProfileService setProfile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public CreateProfileService setPathParams(Map<String, Integer> params) {
        this.params = params;
        return this;
    }
}