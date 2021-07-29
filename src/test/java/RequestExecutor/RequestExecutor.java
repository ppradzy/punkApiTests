package RequestExecutor;

import com.fasterxml.jackson.databind.JsonNode;
import constData.BrewdogData;

import static io.restassured.RestAssured.given;

public class RequestExecutor {
    public static JsonNode getResponseBodyFromRequest(int pageNumber) {
        return given()
                .when()
                .log()
                .all(true)
                .get(String.format(BrewdogData.ENDPOINT_PATH, pageNumber))
                .then()
                .extract()
                .body()
                .as(JsonNode.class);
    }
}
