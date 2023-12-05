package com.lumaserv.matchtracker.util.scryfall;

import lombok.Data;

import java.util.Map;

@Data
public class ScryfallCommander {

    String name;
    Map<LandType, Integer> cost;

}
