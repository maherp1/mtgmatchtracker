package com.lumaserv.matchtracker.service;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.model.Player;

public interface PlayerService {


    Player create(String name, String email, boolean admin) throws ServiceException;

    void update(String name) throws ServiceException;

    void delete(Player player);

}
