package com.lumaserv.matchtracker.http.controller;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.http.request.CreateDeckRequest;
import com.lumaserv.matchtracker.http.resource.DeckResource;
import com.lumaserv.matchtracker.http.response.Response;
import com.lumaserv.matchtracker.model.Deck;
import com.lumaserv.matchtracker.service.DeckService;
import com.lumaserv.matchtracker.util.scryfall.ScryfallCommander;
import com.lumaserv.matchtracker.util.scryfall.ScryfallUtil;
import org.javawebstack.http.router.Exchange;
import org.javawebstack.http.router.router.annotation.PathPrefix;
import org.javawebstack.http.router.router.annotation.With;
import org.javawebstack.http.router.router.annotation.params.Path;
import org.javawebstack.http.router.router.annotation.verbs.Get;
import org.javawebstack.http.router.router.annotation.verbs.Post;
import org.javawebstack.http.router.router.annotation.verbs.Put;
import org.javawebstack.orm.Repo;

import java.util.ArrayList;
import java.util.List;

@With({"bind"})
@PathPrefix("decks")
public class DeckController extends Controller {

    DeckService deckService;

    @Get
    public Response list(Exchange exchange) {
        return defaultListResponse(exchange, Deck.class, DeckResource.class, null);
    }

    @Post
    public Response create(CreateDeckRequest request) throws ServiceException {
        Deck deck = deckService.create(request.getCommanderName(), request.getPartnerName());
        return Response.created(DeckResource.class, deck);
    }

    @Get("{deck:deck}")
    public Response get(@Path("deck") Deck deck) {
        return Response.success().setData(DeckResource.class, deck);
    }

    @Put("update")
    public Response updateCommanderList() throws ServiceException {
        List<ScryfallCommander> commanders = ScryfallUtil.getCommanders();
        List<String> commanderNames = new ArrayList<>();
        if(commanders == null){
            throw new ServiceException(500, "The list is probably empty.");
        }
        for (ScryfallCommander commander : commanders) {
            commanderNames.add(commander.getName());
        }
        List<Deck> currentCommanders = Repo.get(Deck.class).query().all();
        for (String commanderName : commanderNames) {
            Deck deck = new Deck();
            deck.setCommanderName(commanderName);
            deck.setCommanderPartnerName(" ");
            deck.setGamesPlayed(0);
            deck.setNumberOfWins(0);
            deck.save();
        }
        return Response.success();
    }
}
