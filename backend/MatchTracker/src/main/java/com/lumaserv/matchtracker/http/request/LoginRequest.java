package com.lumaserv.matchtracker.http.request;

import lombok.Getter;
import org.javawebstack.abstractdata.AbstractObject;
import org.javawebstack.validator.rule.RequiredRule;

@Getter
public class LoginRequest implements Request {

    @RequiredRule
    String username;
    @RequiredRule
    String password;

}
