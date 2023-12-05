package com.lumaserv.matchtracker.service;

import com.lumaserv.matchtracker.model.Card;
import org.javawebstack.abstractdata.AbstractObject;

public interface CardService {

    Card create(AbstractObject data);
}
