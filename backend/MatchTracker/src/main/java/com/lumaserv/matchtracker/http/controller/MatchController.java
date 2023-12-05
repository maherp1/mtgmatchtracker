package com.lumaserv.matchtracker.http.controller;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.http.request.CreateMatchRequest;
import com.lumaserv.matchtracker.http.resource.MatchResource;
import com.lumaserv.matchtracker.http.response.Response;
import com.lumaserv.matchtracker.model.Match;
import org.javawebstack.abstractdata.AbstractElement;
import org.javawebstack.http.router.Exchange;
import org.javawebstack.http.router.router.annotation.PathPrefix;
import org.javawebstack.http.router.router.annotation.With;
import org.javawebstack.http.router.router.annotation.params.Path;
import org.javawebstack.http.router.router.annotation.verbs.Get;
import org.javawebstack.http.router.router.annotation.verbs.Post;

import java.util.UUID;

/*
    match: {
        [
            {
                player1: "xx",
                deck: "xx"
            },
            {
                player2: "xx",
                deck: "xx"
            }
        ]
    }

 */
@With({"auth", "bind"})
@PathPrefix("matches")
public class MatchController extends Controller {
    @Get("{match:match}")
    public Response get(@Path("match") Match match) {
        return Response.success().setData(MatchResource.class, match);
    }

    @Get
    public Response list(Exchange exchange) {
        return defaultListResponse(exchange, Match.class, MatchResource.class, null);
    }

    @Post
    public Response create(CreateMatchRequest request) throws ServiceException {
        for (AbstractElement participant : request.getData().array("participants")) {

        }
        Match match = new Match();
        match.setId(UUID.randomUUID());
        match.save();
        return Response.created(MatchResource.class, match);
    }

}
