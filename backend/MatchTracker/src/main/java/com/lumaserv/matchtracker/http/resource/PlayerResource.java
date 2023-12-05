package com.lumaserv.matchtracker.http.resource;

import com.lumaserv.matchtracker.model.Player;
import org.javawebstack.webutils.Resource;

import java.util.UUID;

public class PlayerResource implements Resource<Player> {
    UUID id;
    String name;
    int gamesPlayed;
    int numberOfWins;

    public void map(Player player, Context context) {
        this.id = player.getId();
        this.name = player.getName();
        this.gamesPlayed = player.getGamesPlayed();
        this.numberOfWins = player.getNumberOfWins();
    }
}
