package com.lumaserv.matchtracker.http.controller;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.http.request.CreatePlayerRequest;
import com.lumaserv.matchtracker.http.resource.PlayerResource;
import com.lumaserv.matchtracker.http.response.Response;
import com.lumaserv.matchtracker.model.Player;
import com.lumaserv.matchtracker.service.PlayerService;
import org.javawebstack.http.router.Exchange;
import org.javawebstack.http.router.router.annotation.PathPrefix;
import org.javawebstack.http.router.router.annotation.With;
import org.javawebstack.http.router.router.annotation.params.Path;
import org.javawebstack.http.router.router.annotation.verbs.Get;
import org.javawebstack.http.router.router.annotation.verbs.Post;

@With({"auth", "bind"})
@PathPrefix("players")
public class PlayerController extends Controller {
    PlayerService playerService;

    @Get
    public Response list(Exchange exchange) {
        return defaultListResponse(exchange, Player.class, PlayerResource.class, null);
    }

    @Post
    public Response create(CreatePlayerRequest request) throws ServiceException {
        Player player = playerService.create(request.getName(), request.getEmail(), false);
        return Response.created(PlayerResource.class, player);
    }

    @Get("{player:player}")
    public Response get(@Path("player") Player player) {
        return Response.success().setData(PlayerResource.class, player);
    }
}
