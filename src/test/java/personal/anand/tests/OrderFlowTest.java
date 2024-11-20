package personal.anand.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import personal.anand.api_client.AuthClient;
import personal.anand.api_client.OrderFlowClient;
import personal.anand.base.BaseTest;
import java.util.HashMap;
import static personal.anand.utils.Keys.*;

public class OrderFlowTest extends BaseTest {

    public OrderFlowTest(){
        authClient=new AuthClient();
        orderFlowClient=new OrderFlowClient();
    }

    @DataProvider
    private Object[][] createProductData() {
        return new Object[][]{
                {"macbook", "MacBook Air","skirts","888991212","Addidas Orginal","Women"},

        };
    }

    @Test(dataProvider = "createProductData")
    public void createProductTest(String productName, String productCategory, String productSubCategory, String productPrice, String productDescription, String productFor){
        System.out.println("Scenario storage instance in OCT" + scenarioStorage);
        scenarioStorage.put(KEY_AUTH_TOKEN, authClient.getAuthorizationToken());
        HashMap<String, String> createProductHashmap = new HashMap<>();
        createProductHashmap.put("productName", productName);
        createProductHashmap.put("productCategory", productCategory);
        createProductHashmap.put("productSubCategory", productSubCategory);
        createProductHashmap.put("productPrice", productPrice);
        createProductHashmap.put("productDescription", productDescription);
        createProductHashmap.put("productCategory ", productCategory);
        createProductHashmap.put("productFor", productFor);
        createProductHashmap.put("productAddedBy", scenarioStorage.get(KEY_USER_ID));
        orderFlowClient.createProduct(createProductHashmap);
        Assert.assertEquals(scenarioStorage.get(KEY_CREATE_PRODUCT_MESSAGE), "Product Added Successfully");
    }
    @Test
    public void createOrderAndCheckDetails(){
        createProductTest("mac", "MacBook Air","skirts","888991212","Addidas Orginal","Women");
        orderFlowClient.createOrder();
        Assert.assertEquals(scenarioStorage.get(KEY_CREATE_ORDER_MESSAGE), "Order Placed Successfully");
        orderFlowClient.orderDetails();
        Assert.assertEquals(scenarioStorage.get(KEY_ORDER_DETAILS_MESSAGE), "Orders fetched for customer Successfully");
        orderFlowClient.deleteOrder();
    }
}
