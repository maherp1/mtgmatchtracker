package com.lumaserv.matchtracker.http.resource;

import com.lumaserv.matchtracker.model.Match;
import com.lumaserv.matchtracker.model.Player;
import org.javawebstack.webutils.Resource;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class MatchResource implements Resource<Match> {
    UUID id;
    List<Player> participants;
    Timestamp playedAt;


    @Override
    public void map(Match match, Context context) {
        this.id = match.getId();
        this.playedAt = match.getCreatedAt();

    }
}
