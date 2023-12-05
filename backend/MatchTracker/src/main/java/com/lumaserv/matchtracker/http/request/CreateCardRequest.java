package com.lumaserv.matchtracker.http.request;

import lombok.Getter;
import org.javawebstack.abstractdata.AbstractObject;
import org.javawebstack.validator.rule.RequiredRule;
import org.javawebstack.validator.rule.StringRule;

@Getter
public class CreateCardRequest {
    @StringRule(min = 2, max = 50)
    @RequiredRule
    String name;
    AbstractObject data;
}
