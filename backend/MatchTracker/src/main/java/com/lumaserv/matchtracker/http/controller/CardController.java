package com.lumaserv.matchtracker.http.controller;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.http.request.CreateCardRequest;
import com.lumaserv.matchtracker.http.resource.CardResource;
import com.lumaserv.matchtracker.http.response.Response;
import com.lumaserv.matchtracker.model.Card;
import com.lumaserv.matchtracker.service.CardService;
import org.javawebstack.http.router.router.annotation.PathPrefix;
import org.javawebstack.http.router.router.annotation.With;
import org.javawebstack.http.router.router.annotation.verbs.Post;

@With({"auth", "bind"})
@PathPrefix("cards")

public class CardController extends Controller {
    CardService cardService;

    @Post
    public Response create(CreateCardRequest request) throws ServiceException {
        Card card = cardService.create(request.getData());
        return Response.created(CardResource.class, card);
    }

}
