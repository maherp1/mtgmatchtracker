package com.lumaserv.matchtracker.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javawebstack.orm.annotation.Column;
import org.javawebstack.orm.annotation.Dates;
import org.javawebstack.orm.annotation.SoftDelete;
import org.javawebstack.orm.query.Query;
import org.javawebstack.webutils.modelbind.ModelBind;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Dates
@SoftDelete
@ModelBind("match")
public class Match extends Model {
    @Column
    UUID id;
    @Column
    Timestamp createdAt;
    @Column
    Timestamp updatedAt;
    @Column
    Timestamp deletedAt;

    public Query<Player> participants() {
        return hasMany(Player.class);
    }

    public Query<Deck> playedDecks() {
        return hasMany(Deck.class);
    }

    public Query<Match> matches() {
        return hasMany(Match.class, "matchId");
    }

}
