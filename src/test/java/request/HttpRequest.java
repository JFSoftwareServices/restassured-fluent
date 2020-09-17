package request;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public interface HttpRequest {
    Response send(RequestSpecification requestSpecification);
}