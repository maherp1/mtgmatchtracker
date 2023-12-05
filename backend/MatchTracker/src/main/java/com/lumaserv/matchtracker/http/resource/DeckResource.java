package com.lumaserv.matchtracker.http.resource;

import com.lumaserv.matchtracker.model.Deck;
import org.javawebstack.webutils.Resource;

import java.util.UUID;

public class DeckResource implements Resource<Deck> {

    UUID id;
    String commanderName;
    String partnerName;
    Integer gamesPlayed;
    Integer numberOfWins;


    @Override
    public void map(Deck deck, Context context) {
        this.id = deck.getId();
        this.commanderName = deck.getCommanderName();
        this.partnerName = deck.getCommanderPartnerName();
        this.gamesPlayed = deck.getGamesPlayed();
        this.numberOfWins = deck.getNumberOfWins();
    }
}
