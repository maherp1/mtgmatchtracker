package com.lumaserv.matchtracker.service;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.model.Match;
import lombok.AllArgsConstructor;
import org.javawebstack.abstractdata.AbstractObject;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
public class MatchServiceImpl implements MatchService {


    public Match create(AbstractObject data) throws ServiceException {
        Match match = new Match();
        match.setId(UUID.randomUUID());

        match.save();
        return match;
    }

    @Override
    public void update(Match match, AbstractObject data) {
        match.setUpdatedAt(Timestamp.from(Instant.now()));
        match.save();
    }

}
