package com.lumaserv.matchtracker.http.middleware;

import com.lumaserv.matchtracker.model.Player;
import com.lumaserv.matchtracker.model.Token;
import com.lumaserv.matchtracker.service.AuthService;
import org.javawebstack.http.router.Exchange;
import org.javawebstack.http.router.handler.RequestInterceptor;

public class TokenMiddleware implements RequestInterceptor {
    AuthService authService;

    @Override
    public boolean intercept(Exchange exchange) {
        String tokenString = exchange.bearerAuth();
        if (tokenString == null && exchange.getQueryParameters().has("auth_token")) {
            tokenString = exchange.query("auth_token");
        }
        try {
            Token token = authService.verifySessionToken(tokenString);
            if(token != null) {
                exchange.attrib("token",token);
                Player player = token.player().first();
                if(player != null) {
                    exchange.attrib("player",player);
                }
            }
        } catch (Exception ignored) {
        }
        return false;
    }
}
