package personal.anand.modal;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public final class AuthLoginResponse{
    private String token;
    private String userId;
    private String message;

}
