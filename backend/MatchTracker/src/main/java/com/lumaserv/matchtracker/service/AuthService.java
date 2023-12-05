package com.lumaserv.matchtracker.service;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.model.Token;
import org.javawebstack.abstractdata.AbstractObject;

public interface AuthService {
    Token performLogin(String username, String password) throws ServiceException;

    String createSessionToken(Token session) throws ServiceException;
    Token verifySessionToken(String tokenStr) throws ServiceException;

}
