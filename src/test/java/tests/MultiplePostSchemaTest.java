package tests;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.RestAssured;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.not;

public class MultiplePostSchemaTest {
    private static final String MULTIPLE_POSTS = "[\n" +
            "{\n" +
            "\"id\": 1,\n" +
            "\"title\": \"Selenium with C#\",\n" +
            "\"author\": \"Karthik KK\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": 2,\n" +
            "\"title\": \"TitleValue\",\n" +
            "\"author\": \"Karthik KK\"\n" +
            "},\n" +
            "{\n" +
            "\"id\": 3\n" +
            "}\n" +
            "]";
    private static WireMockServer server = new WireMockServer();
    private static JsonSchemaFactory jsonSchemaFactory;
    private static ResponseDefinitionBuilder mockResponse = new ResponseDefinitionBuilder();

    @BeforeClass
    public static void setUp() {
        jsonSchemaFactory = JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(
                        ValidationConfiguration.newBuilder()
                                .setDefaultVersion(SchemaVersion.DRAFTV4).freeze())
                .freeze();
        server.start();
    }

    @AfterClass
    public static void teardown() {
        server.stop();
    }

    @Test
    public void testSchemaValidationHappyPath() {
        mockResponse.withStatus(200).withBody(MULTIPLE_POSTS);
        setStub();
        RestAssured.given()
                .when()
                .get("/posts")
                .then().assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/posts.json")
                        .using(jsonSchemaFactory));
    }

    private void arrangeActAssertNoMatch() {
        RestAssured.given()
                .when()
                .get("/posts")
                .then().assertThat()
                .body(not(matchesJsonSchemaInClasspath("schemas/posts.json")
                        .using(jsonSchemaFactory)));
    }

    private void setStub() {
        WireMock.stubFor(WireMock.get("/posts")
                .willReturn(mockResponse)
        );
    }

    @Test
    public void testSchemaValidationForIncorrectIdValue() {
        mockResponse.withStatus(200).withBody(MULTIPLE_POSTS.replace("\"TitleValue\"", "5"));
        setStub();
        arrangeActAssertNoMatch();
    }

    @Test
    public void testSchemaValidationForIncorrectIdKey() {
        mockResponse.withStatus(200).withBody(MULTIPLE_POSTS.replace("id", "idX"));
        setStub();
        arrangeActAssertNoMatch();
    }
}