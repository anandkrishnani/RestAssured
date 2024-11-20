package personal.anand.api_client;

import com.google.inject.Inject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import personal.anand.modal.AuthLogin;
import personal.anand.modal.AuthLoginResponse;
import personal.anand.utils.ConfigReader;
import personal.anand.utils.ScenarioStorage;
import static io.restassured.RestAssured.given;
import static personal.anand.utils.Keys.KEY_USER_ID;

public class AuthClient {
    private static final Logger logger = LoggerFactory.getLogger(AuthClient.class);
    private AuthLoginResponse authLoginResponse;
    private ConfigReader configReader;
    @Inject
    private ScenarioStorage scenarioStorage;
    public AuthClient(){
        configReader = new ConfigReader();
    }

    public RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder().setBaseUri(configReader.init_property().getProperty("base-uri")).build();
    }

    public String getAuthorizationToken() {
        logger.info("ScenarioStorage instance in Auth Client {}", scenarioStorage);
        RequestSpecification requestSpec = getRequestSpecification();
        AuthLogin authLogin = new AuthLogin();
        authLogin.setUserEmail(configReader.init_property().getProperty("user-email"));
        authLogin.setUserPassword(configReader.init_property().getProperty("user-password"));
        authLoginResponse = given().log().all().spec(requestSpec).header("Content-Type", "application/json").body(authLogin).post("api/ecom/auth/login").as(AuthLoginResponse.class);
        Assert.assertEquals(authLoginResponse.getMessage(), "Login Successfully");
        scenarioStorage.put(KEY_USER_ID, authLoginResponse.getUserId());
        logger.debug("{} User ID in auth login response",authLoginResponse.getUserId());
        return authLoginResponse.getToken();
    }

    public RequestSpecification getRequestSpecificationWithAuthTokenAndContentType() {
        String authToken = getAuthorizationToken();
        return new RequestSpecBuilder().setBaseUri(configReader.init_property().getProperty("base-uri")).addHeader("Authorization", authToken)
                .addHeader("Content-Type", "application/json").build();
    }
}
