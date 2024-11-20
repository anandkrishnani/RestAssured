package personal.anand.modal;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter@Setter
public final class AuthLoginResponse implements Serializable {
    private static AuthLoginResponse authLoginResponse;

    private AuthLoginResponse() {
    }

    public static AuthLoginResponse getInstance() {

        //return AuthClient.getAuthLoginResponseInstance();
        if (authLoginResponse==null){
            authLoginResponse=new AuthLoginResponse();
        }
     return authLoginResponse;
    }

   protected Object readResolve(){
        return getInstance();
    }

    private String token;
    private String userId;
    private String message;



}
