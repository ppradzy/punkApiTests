package brewdogBeerTests;

import requestExecutor.RequestExecutor;
import constData.BrewdogData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import setup.TestSetUp;
import validator.AbvValueValidator;
import validator.NameValueValidator;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(TestSetUp.class)
public class BasicTests {

    @DisplayName("Verify if each of beer has a valid ABV value")
    @Test
    public void TestValidateAbvValue() {
        int pageNumber = 1;
        do {
            List<String> listOfAbvs = RequestExecutor.getResponseBodyFromRequest(pageNumber).findValuesAsText("abv");
            listOfAbvs.forEach(abvValue -> {
                assertThat("ABV is not of a double type", AbvValueValidator.isDouble(abvValue), is(true));
                assertThat(String.format("ABV value is less than 4.0. Actual value: %s", abvValue), AbvValueValidator.hasValidValue(abvValue), is(true));
            });
            pageNumber++;
        } while (!RequestExecutor.getResponseBodyFromRequest(pageNumber).isEmpty());
    }

    @DisplayName("Verify if each of beer has a valid name value")
    @Test
    public void TestValidateNameValue() {
        int pageNumber = 1;
        do {
            List<String> listOfNames = RequestExecutor.getResponseBodyFromRequest(pageNumber).findValuesAsText("name");
            listOfNames.forEach(nameValue -> {
                assertThat(String.format("Name is a null or empty. Current name: %s", nameValue), NameValueValidator.isEmptyOrNull(nameValue), is(false));
            });
            pageNumber++;
        } while (!RequestExecutor.getResponseBodyFromRequest(pageNumber).isEmpty());
    }

    @DisplayName("Verify error message in case with provided invalid beer ID")
    @Test
    public void TestInvalidBeerId() {
        given()
                .when()
                .log()
                .all(true)
                .get(BrewdogData.INVALID_BEER_ID_PATH)
                .then()
                .log()
                .all(true)
                .statusCode(400)
                .assertThat()
                .body("data[0].msg", equalTo("beerId must be a number and greater than 0"));
    }

    @DisplayName("Verify error message in case with not existing beer ID")
    @Test
    public void TestNotExistingBeerId() {
        given()
                .when()
                .log()
                .all(true)
                .get(BrewdogData.NOT_EXISTING_BEER_ID_PATH)
                .then()
                .log()
                .all(true)
                .statusCode(404)
                .assertThat()
                .body("message", equalTo("No beer found that matches the ID 0123456789"));
    }
}
