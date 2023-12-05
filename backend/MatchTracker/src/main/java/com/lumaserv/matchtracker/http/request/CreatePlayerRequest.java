package com.lumaserv.matchtracker.http.request;

import lombok.Getter;
import org.javawebstack.validator.rule.EmailRule;
import org.javawebstack.validator.rule.RequiredRule;
import org.javawebstack.validator.rule.StringRule;

@Getter
public class CreatePlayerRequest {
    @StringRule(min = 2, max = 50)
    @RequiredRule
    String name;
    @EmailRule
    String email;
    boolean admin;

}
