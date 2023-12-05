package com.lumaserv.matchtracker.http.request;

import lombok.Getter;
import org.javawebstack.validator.rule.RequiredRule;
import org.javawebstack.validator.rule.StringRule;

@Getter
public class CreateDeckRequest {

    @StringRule(min = 2, max = 50)
    @RequiredRule
    String commanderName;
    @StringRule(min = 2, max = 50)
    String partnerName;

}
