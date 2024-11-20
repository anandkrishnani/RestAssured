package personal.anand.modal;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter@JsonIgnoreType
public class Data {
    private String _id;
    private String orderById;
    private String orderBy;
    private String productOrderedId;
    private String productName;
    private String country;
    private String productDescription;
    private String productImage;
    private String orderPrice;

}
