package tests;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.junit.After;
import org.junit.Before;

import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;

public class TestBase {
    private static final String BASE_URI = "http://localhost";
    private static final int PORT = 3000;

    @Before
    public void initialization() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = PORT;
        JsonSchemaValidator.settings = settings().with().jsonSchemaFactory(
                JsonSchemaFactory.newBuilder().setValidationConfiguration(ValidationConfiguration.newBuilder()
                        .setDefaultVersion(SchemaVersion.DRAFTV4).freeze()).freeze());
//                and().with().checkedValidation(false);
    }

    @After
    public void after() {
        JsonSchemaValidator.reset();
    }
}