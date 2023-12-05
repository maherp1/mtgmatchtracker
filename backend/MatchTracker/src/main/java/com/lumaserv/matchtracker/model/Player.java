package com.lumaserv.matchtracker.model;

import lombok.Getter;
import lombok.Setter;
import org.javawebstack.orm.annotation.*;
import org.javawebstack.orm.query.Query;
import org.javawebstack.validator.rule.EmailRule;
import org.javawebstack.webutils.crypt.Crypt;
import org.javawebstack.webutils.modelbind.ModelBind;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Dates
@SoftDelete
@ModelBind("player")
public class Player extends Model {
    @Column
    @Searchable
    UUID id;
    @Column
    @Searchable
    String name;
    @Column(size = 100)
    String passwordHash;
    @Column
    @EmailRule
    @Searchable
    String email;
    @Column
    @Filterable
    Boolean admin;
    @Column
    Integer gamesPlayed;
    @Column
    Integer numberOfWins;
    @Column
    Timestamp createdAt;
    @Column
    Timestamp updatedAt;
    @Column
    Timestamp deletedAt;

    public Player setPassword(String password) {
        this.passwordHash = Crypt.hash(password);
        return this;
    }

    public boolean checkPassword(String password) {
        return Crypt.check(passwordHash, password);
    }

    public Query<Match> matches() {
        return hasMany(Match.class);
    }

}
