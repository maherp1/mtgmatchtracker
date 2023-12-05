package com.lumaserv.matchtracker.model;

import lombok.Getter;
import lombok.Setter;
import org.javawebstack.orm.annotation.Column;
import org.javawebstack.orm.annotation.Dates;
import org.javawebstack.orm.annotation.SoftDelete;
import org.javawebstack.webutils.modelbind.ModelBind;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Dates
@SoftDelete
@ModelBind("card")
public class Card extends Model {
    @Column
    UUID id;
    @Column
    String name;
    @Column
    String description;
    @Column
    int[][] stats;
    @Column
    Timestamp createdAt;
    @Column
    Timestamp updatedAt;
    @Column
    Timestamp deletedAt;
}
