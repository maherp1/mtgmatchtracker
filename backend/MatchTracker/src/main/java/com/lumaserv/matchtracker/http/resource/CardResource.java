package com.lumaserv.matchtracker.http.resource;

import com.lumaserv.matchtracker.model.Card;
import org.javawebstack.webutils.Resource;

public class CardResource implements Resource<Card> {
    String name;
    String description;


    @Override
    public void map(Card card, Context context) {
        this.name = card.getName();
        this.description = card.getDescription();
    }
}
