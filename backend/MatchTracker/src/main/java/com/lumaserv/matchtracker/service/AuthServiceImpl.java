package com.lumaserv.matchtracker.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.model.Player;
import com.lumaserv.matchtracker.model.Token;
import org.javawebstack.orm.Repo;
import org.javawebstack.webutils.config.Config;
import org.javawebstack.webutils.crypt.Crypt;

import java.sql.Timestamp;
import java.time.Instant;

public class AuthServiceImpl implements AuthService {

    Crypt crypt;
    Config config;
    PlayerService playerService;

    @Override
    public Token performLogin(String username, String password) throws ServiceException {
        Player player = Repo.get(Player.class)
                .where("email", username)
                .first();
        if(player == null || !player.checkPassword(password)) {
            throw new ServiceException(400, "authentication_failed", "Wrong credentials");
        }
        return createSession(player);
    }

    @Override
    public String createSessionToken(Token session) throws ServiceException {
        return JWT.create()
                .withSubject(session.getUserId().toString())
                .withClaim("sid", session.getId().toString())
                .withAudience("auth")
                .sign(Algorithm.HMAC256(config.get("jwt.secret", "changeme")));
    }

    public Token verifySessionToken(String tokenStr) throws ServiceException {
        try {
            DecodedJWT decoded = JWT.require(Algorithm.HMAC256(config.get("jwt.secret", "changeme")))
                    .withAudience("auth")
                    .build()
                    .verify(tokenStr);
            Token token = Repo.get(Token.class).whereId(decoded.getClaim("sid").asString()).first();
            if(token == null)
                throw new ServiceException(401, "invalid_token", "Invalid session token");
            return token;
        } catch (JWTVerificationException ex) {
            throw new ServiceException(401, "invalid_token", "Invalid session token");
        }
    }

    public Token createSession(Player player) {
        Token session = new Token()
                .setType(Token.Type.SESSION)
                .setUserId(player.getId())
                .setExpiresAt(calculateSessionExpiry());
        session.save();
        return session;
    }

    private Timestamp calculateSessionExpiry() {
        int lifetime = config.getInt("auth.session.lifetime", 3600);
        return Timestamp.from(Instant.now().plusSeconds(lifetime));
    }

}
