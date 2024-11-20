package personal.anand.base;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import personal.anand.api_client.AuthClient;
import personal.anand.api_client.OrderFlowClient;
import personal.anand.utils.AppModule;
import personal.anand.utils.ScenarioStorage;

import java.util.Properties;

public class BaseTest {

    protected Properties prop;
    protected ScenarioStorage scenarioStorage;
    protected AuthClient authClient;
    protected OrderFlowClient orderFlowClient;


    @BeforeTest
    public void setup() {
        Injector injector= Guice.createInjector(new AppModule());
        scenarioStorage = injector.getInstance(ScenarioStorage.class);
        injector.injectMembers(authClient);
        injector.injectMembers(orderFlowClient);

    }
}
