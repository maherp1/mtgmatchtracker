package com.lumaserv.matchtracker.service;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.model.Deck;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeckServiceImpl implements DeckService {

    @Override
    public Deck create(String commander, String partner) throws ServiceException {
        Deck deck = new Deck()
                .setCommanderName(commander)
                .setCommanderPartnerName(partner);

        deck.save();

        return deck;
    }
}
