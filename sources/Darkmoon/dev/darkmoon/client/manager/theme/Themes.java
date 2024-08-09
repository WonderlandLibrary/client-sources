package dev.darkmoon.client.manager.theme;

import lombok.Getter;

import java.awt.*;
import java.util.HashMap;

public enum Themes {
    SPICY(new Theme("Spicy", Theme.ThemeType.STYLE, new Color(94, 5, 32).brighter(), new Color(255, 106, 0).darker())),
    CHERISH(new Theme("Cherish", Theme.ThemeType.STYLE, new Color[]{(new Color(119, 51, 252)).brighter(), (new Color(18, 18, 49)).darker()})),
    SWEETY(new Theme("Sweety", Theme.ThemeType.STYLE, new Color(28, 167, 222), new Color(236, 133, 209))),
    BRIGHTSWEET(new Theme("BrightSweet", Theme.ThemeType.STYLE, new Color(28, 167, 255), new Color(236, 133, 255))),
    BLOSSOM(new Theme("Blossom", Theme.ThemeType.STYLE, new Color(34, 193, 195), new Color(253, 187, 45))),
    RIVER(new Theme("River", Theme.ThemeType.STYLE, new Color(0x43cea2), new Color(0x185a9d))),
    FRESH(new Theme("Fresh", Theme.ThemeType.STYLE, new Color(0x09cad1), new Color(0x6118cc))),
    ASLING(new Theme("Asling", Theme.ThemeType.STYLE, new Color(54, 1, 1), new Color(30, 1, 1))),
    GRACE(new Theme("Grace", Theme.ThemeType.STYLE, new Color(3, 29, 133), new Color(155, 8, 8))),
    CHARTREUSE(new Theme("Chartreuse", Theme.ThemeType.STYLE, new Color(133, 107, 3), new Color(85, 31, 129))),
    TURQUIOSE(new Theme("Turquiose", Theme.ThemeType.STYLE, new Color(9, 141, 141), new Color(85, 31, 129))),
    SEAFOAM(new Theme("Seafoam", Theme.ThemeType.STYLE, new Color(40, 141, 89), new Color(19, 80, 173))),
    BUTTER(new Theme("Butter", Theme.ThemeType.STYLE, new Color(9, 141, 141), new Color(111, 115, 3))),
    GOLDPINK(new Theme("Goldpink", Theme.ThemeType.STYLE, new Color(182, 165, 7), new Color(194, 8, 162))),
    KELLY(new Theme("Kelly", Theme.ThemeType.STYLE, new Color(22, 129, 134), new Color(9, 164, 62))),
    SUNSHINE(new Theme("Sunshine", Theme.ThemeType.STYLE, new Color(191, 255, 0), new Color(68, 16, 204))),
    FELLING(new Theme("Felling", Theme.ThemeType.STYLE, new Color(0xf7b75b), new Color(0xec2578)));

    @Getter
    private final Theme theme;
    Themes(Theme theme) {
        this.theme = theme;
    }

    private static final HashMap<String, Theme> map;

    static {
        map = new HashMap<>();
        for (Themes v : Themes.values()) {
            map.put(v.theme.getName(), v.theme);
        }
    }
    public static Theme findByName(String name) {
        return map.get(name);
    }
}
