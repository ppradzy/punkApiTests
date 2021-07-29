package setup;

import constData.BrewdogData;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestSetUp implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        RestAssured.basePath = BrewdogData.RESOURCE_PATH;
        RestAssured.baseURI = BrewdogData.HOST_ADDRESS;
        LogConfig logConfig = new LogConfig()
                .enableLoggingOfRequestAndResponseIfValidationFails()
                .enablePrettyPrinting(true);
        logConfig.defaultStream();
        RestAssured.config().logConfig(logConfig);
    }

    @Override
    public void afterAll(ExtensionContext context) {
        RestAssured.reset();
    }
}
