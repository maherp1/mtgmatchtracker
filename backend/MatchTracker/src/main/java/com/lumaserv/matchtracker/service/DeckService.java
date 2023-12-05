package com.lumaserv.matchtracker.service;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.model.Deck;

public interface DeckService {
    Deck create(String commander, String partner) throws ServiceException;

}
