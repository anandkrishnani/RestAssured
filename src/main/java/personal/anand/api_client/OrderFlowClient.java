package personal.anand.api_client;


import com.google.inject.Inject;
import io.restassured.path.json.JsonPath;
import personal.anand.modal.*;
import personal.anand.utils.Keys;
import personal.anand.utils.ScenarioStorage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static io.restassured.RestAssured.given;


public class OrderFlowClient implements Keys {

    private CreateProductResponse createProductResponse;
    private CreateOrderResponse createOrderResponse;
    private OrderDetailsResponse orderDetailsResponse;
    @Inject
    private AuthClient authClient;
    @Inject
    private ScenarioStorage scenarioStorage;
    @Inject
    public OrderFlowClient() {
        authClient = new AuthClient();
    }


    public void createProduct(HashMap< String,String> hashMap){
        createProductResponse = given().log().all().spec(authClient.getRequestSpecification()).header("Authorization", scenarioStorage.get(KEY_AUTH_TOKEN)).formParams(hashMap)
                .multiPart("productImage", new File("src/test/resources/download.jpeg"))
                .post("api/ecom/product/add-product").as(CreateProductResponse.class);

        scenarioStorage.put(KEY_CREATE_PRODUCT_MESSAGE, createProductResponse.getMessage());
        scenarioStorage.put(KEY_PRODUCT_ID, createProductResponse.getProductId());

    }

    public void createOrder(){
        Orders orders=new Orders();
        orders.setCountry("India");
        orders.setProductOrderedId(scenarioStorage.get(KEY_PRODUCT_ID));
        CreateOrder createOrder=new CreateOrder();
        List<Orders> productsList=new ArrayList<>();
        productsList.add(orders);
        createOrder.setOrders(productsList);
        createOrderResponse= given().log().all().spec(authClient.getRequestSpecificationWithAuthTokenAndContentType()).body(createOrder)
                .when()
                .post("api/ecom/order/create-order")
                .as(CreateOrderResponse.class);
        scenarioStorage.put(KEY_CREATE_ORDER_MESSAGE, createOrderResponse.getMessage());


    }

    public void orderDetails(){
        orderDetailsResponse =given().log().all().spec(authClient.getRequestSpecificationWithAuthTokenAndContentType())
                .queryParam("id", createOrderResponse.getOrders().get(0))
                .get("api/ecom/order/get-orders-details").as(OrderDetailsResponse.class);
        scenarioStorage.put(KEY_ORDER_DETAILS_MESSAGE,orderDetailsResponse.getMessage());


    }

    public JsonPath deleteOrder() {
       String delResponse= given().log().all().spec(authClient.getRequestSpecification()).header("Authorization",scenarioStorage.get(KEY_AUTH_TOKEN))
               .pathParam("productId",createProductResponse.getProductId())
                .when()
                .delete("api/ecom/product/delete-product/{productId}").then().log().all().statusCode(200).extract().response().asString();

        JsonPath jsonPath =new JsonPath(delResponse);
        return jsonPath;

    }

}
