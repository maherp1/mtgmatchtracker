package com.lumaserv.matchtracker.http.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    String accessToken;
    String tokenType = "Bearer";
    Integer expiresIn;

}