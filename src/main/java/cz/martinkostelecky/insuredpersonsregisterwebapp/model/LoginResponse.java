package cz.martinkostelecky.insuredpersonsregisterwebapp.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String accessToken;
}
