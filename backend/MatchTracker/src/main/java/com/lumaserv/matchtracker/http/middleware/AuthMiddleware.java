package com.lumaserv.matchtracker.http.middleware;

import com.lumaserv.matchtracker.http.response.Response;
import org.javawebstack.http.router.Exchange;
import org.javawebstack.http.router.handler.RequestHandler;

public class AuthMiddleware implements RequestHandler {

    public Object handle(Exchange exchange) {
        if (exchange.attrib("player") == null)
            return Response.error(401, "Token is invalid or has expired, new authentication required.");
        return null;
    }
}
