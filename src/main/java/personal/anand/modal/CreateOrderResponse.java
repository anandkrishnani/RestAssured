package personal.anand.modal;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter@Setter
public class CreateOrderResponse {

    private List<String> orders;
    private List<String> productOrderId;
    private String message;
    private static CreateOrderResponse instance;

    private CreateOrderResponse(){

    }

    public static CreateOrderResponse getInstance(){
        if (instance==null){
            return new CreateOrderResponse();
        }
        return instance;
    }
}
