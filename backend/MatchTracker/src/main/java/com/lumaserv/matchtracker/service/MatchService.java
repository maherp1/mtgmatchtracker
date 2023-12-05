package com.lumaserv.matchtracker.service;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.model.Match;
import org.javawebstack.abstractdata.AbstractObject;

public interface MatchService {
    Match create(AbstractObject data) throws ServiceException;

    void update(Match match, AbstractObject data) throws ServiceException;

}
