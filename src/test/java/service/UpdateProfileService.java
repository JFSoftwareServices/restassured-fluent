package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import model.Profile;
import request.HttpMethod;
import service.util.AuthenticationConstants;

public class UpdateProfileService {
    private static String path;
    private Profile profile;
    private String token;

    public static UpdateProfileService setPath(String path) {
        UpdateProfileService.path = path;
        return new UpdateProfileService();
    }

    public Response updateProfile() {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBasePath(path)
                .addHeader(AuthenticationConstants.AUTHORIZATION, AuthenticationConstants.BEARER + " " + token)
                .setBody(profile)
                .build();
        return RestfulApiService.send(builder, HttpMethod.POST);
    }

    public UpdateProfileService setToken(String token) {
        this.token = token;
        return this;
    }

    public UpdateProfileService setProfile(Profile profile) {
        this.profile = profile;
        return this;
    }
}