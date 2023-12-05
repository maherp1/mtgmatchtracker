package com.lumaserv.matchtracker.service;

import com.lumaserv.matchtracker.model.Card;
import lombok.AllArgsConstructor;
import org.javawebstack.abstractdata.AbstractObject;

@AllArgsConstructor
public class CardServiceImpl implements CardService {

    @Override
    public Card create(AbstractObject data) {
        Card card = new Card()
                .setName(data.string("name"))
                .setDescription(data.string("description"));
        card.save();
        return card;
    }

}
