package com.lumaserv.matchtracker.util.scryfall;

import org.javawebstack.abstractdata.AbstractElement;
import org.javawebstack.abstractdata.AbstractObject;
import org.javawebstack.httpclient.HTTPClient;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScryfallUtil {

    private static final Pattern COST_PATTERN = Pattern.compile("\\{([WURBG1-6])\\}");
    private static List<ScryfallCommander> commanders;

    public static List<ScryfallCommander> getCommanders() {
        if (commanders == null)
            commanders = fetchCommanders();
        return commanders;
    }

    public static List<ScryfallCommander> fetchCommanders() {
        AbstractObject res = new HTTPClient().get("https://api.scryfall.com/decks/17d2401f-3b03-4a00-bd2b-6b8bc4b2954d/export/json").data().object();
        return res.object("entries").array("columna")
                .stream()
                .map(AbstractElement::object)
                .filter(o -> o.bool("found"))
                .map(o -> {
                    AbstractObject cardDigest = o.object("card_digest");
                    ScryfallCommander commander = new ScryfallCommander()
                            .setName(cardDigest.string("name"))
                            .setCost(new HashMap<>());
                    Matcher matcher = COST_PATTERN.matcher(cardDigest.string("mana_cost"));
                    while (matcher.find()) {
                        String cost = matcher.group(1);
                        int amount = 1;
                        LandType type = switch (cost) {
                            case "W" -> LandType.PLAINS;
                            case "G" -> LandType.FOREST;
                            case "U" -> LandType.ISLAND;
                            case "B" -> LandType.SWAMP;
                            case "R" -> LandType.MOUNTAIN;
                            default -> {
                                amount = Integer.parseInt(cost);
                                yield LandType.ANY;
                            }
                        };
                        commander.getCost().put(type, commander.getCost().getOrDefault(type, 0) + amount);
                    }
                    return commander;
                }).toList();
    }
}
