package com.lumaserv.matchtracker.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.javawebstack.orm.annotation.Column;
import org.javawebstack.orm.annotation.Filterable;
import org.javawebstack.orm.annotation.SoftDelete;
import org.javawebstack.webutils.modelbind.ModelBind;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@SoftDelete
@ModelBind("matchparticipant")
public class MatchParticipant extends Model {
    @Column
    UUID id;
    @Column
    @Filterable("match_id")
    UUID matchId;
    @Column
    @Filterable("player_id")
    UUID playerId;
    @Column
    @Filterable("deck_id")
    UUID deckId;
    @Column
    Boolean winner;
    @Column
    Timestamp createdAt;
    @Column
    Timestamp updatedAt;
    @Column
    Timestamp deletedAt;

    public boolean isWinner() {
        return winner;
    }


}
