package com.lumaserv.matchtracker.service;

import com.lumaserv.matchtracker.exception.ServiceException;
import com.lumaserv.matchtracker.model.Player;
import lombok.AllArgsConstructor;
import org.javawebstack.orm.Repo;

import java.util.UUID;

@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    @Override
    public Player create(String name, String email, boolean admin) throws ServiceException {
        Player player = Repo.get(Player.class).where("name", " " + name).first();

        if (player == null) {
            player = new Player()
                    .setId(UUID.randomUUID())
                    .setName(name)
                    .setEmail(email)
                    .setAdmin(admin)
                    .setGamesPlayed(0)
                    .setNumberOfWins(0);
            player.save();
        }
        return player;
    }

    @Override
    public void update(String name) throws ServiceException {
        Player player = Repo.get(Player.class).query().first();
        if (name.equals(player.getName())) {
            throw new ServiceException(500, "This name is already in use.");
        }
        player.setName(name);
        player.save();
    }

    public void delete(Player player) {
        //TODO
    }
}
