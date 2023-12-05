package com.lumaserv.matchtracker.model;


import lombok.Getter;
import lombok.Setter;
import org.javawebstack.orm.annotation.Column;
import org.javawebstack.orm.annotation.Dates;
import org.javawebstack.orm.annotation.SoftDelete;
import org.javawebstack.orm.query.Query;
import org.javawebstack.webutils.modelbind.ModelBind;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Dates
@SoftDelete
@ModelBind("token")
public class Token extends Model {

    @Column
    UUID id;
    @Column
    UUID userId;
    @Column
    Type type;
    @Column
    String name;
    @Column
    Timestamp expiresAt;
    @Column
    Timestamp createdAt;
    @Column
    Timestamp updatedAt;
    @Column
    Timestamp deletedAt;

    public boolean isExpired() {
        return expiresAt != null && expiresAt.toInstant().isBefore(Instant.now());
    }

    public Query<Player> player() {
        return belongsTo(Player.class);
    }

    public enum Type {
        SESSION,
        API
    }
}

