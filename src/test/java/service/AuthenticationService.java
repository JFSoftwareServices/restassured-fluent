package service;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import model.UserLoginDetails;
import request.HttpMethod;

public class AuthenticationService {
    public static String authenticate(UserLoginDetails userLoginDetails) {
        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder
                .setBasePath("/auth/login")
                .setBody(userLoginDetails).build();

        Response response = RestfulApiService.send(builder, HttpMethod.POST);
        return response.
                getBody()
                .jsonPath()
                .get("access_token");
    }
}