package tests;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import model.UserLoginDetails;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class TestBase {
    private static final String BASE_URI = "http://localhost";
    private static final int PORT = 3000;
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    JsonSchemaFactory jsonSchemaFactory;

    @BeforeEach
    public void initializationAndAuthenticate() {
        initialize();
        authenticate();
    }

    private void authenticate() {
        final String token = RestAssured
                .given()
                .spec(requestSpecification)
                .basePath("/auth/login")
                .with()
                .body(UserLoginDetails.DEFAULT_AUTHENTICATION_DATA)
                .when()
                .post()
                .then()
                .extract()
                .body()
                .path("access_token");
        requestSpecification.header(AUTHORIZATION, BEARER + " " + token);
    }

    private void initialize() {
        initializeSchemaFactory();
        initializeRequestSpecification();
        initializeResponseSpecification();
    }

    private void initializeResponseSpecification() {
        final ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectContentType(ContentType.JSON);
        responseSpecification = responseSpecBuilder.build();
    }

    private void initializeRequestSpecification() {
        final RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri(BASE_URI);
        requestSpecBuilder.setPort(PORT);
        requestSpecBuilder.addFilters(List.of(new RequestLoggingFilter(), new ResponseLoggingFilter()));
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecification = requestSpecBuilder.build();
    }

    private void initializeSchemaFactory() {
        jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(
                        ValidationConfiguration.newBuilder()
                                .setDefaultVersion(SchemaVersion.DRAFTV4).freeze())
                .freeze();
    }

    @AfterEach
    public void after() {
        JsonSchemaValidator.reset();
    }
}