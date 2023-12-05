package com.lumaserv.matchtracker.http.controller;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.http.request.LoginRequest;
import com.lumaserv.matchtracker.http.response.LoginResponse;
import com.lumaserv.matchtracker.http.response.Response;
import com.lumaserv.matchtracker.model.Token;
import com.lumaserv.matchtracker.service.AuthService;
import org.javawebstack.http.router.router.annotation.With;
import org.javawebstack.http.router.router.annotation.verbs.Post;

@With("bind")
public class AuthController extends Controller {

    AuthService authService;

    @Post("login")
    public Object performLogin(LoginRequest request) {
        try {
            Token session = authService.performLogin(request.getUsername(), request.getPassword());
            String token = authService.createSessionToken(session);
            return Response.success().setData(
                    new LoginResponse().setAccessToken(token)
            );
        } catch (ServiceException e) {
            return e.toResponse();
        }
    }

}
