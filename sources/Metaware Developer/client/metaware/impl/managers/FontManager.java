package client.metaware.impl.managers;

import client.metaware.api.font.CustomFont;

import java.util.ArrayList;
import java.util.List;

public final class FontManager {

    private final List<CustomFont> FONTS = new ArrayList<>();

    private CustomFont defaultFont, currentFont, notisFont, testFont;

    public void init() {
        add(new CustomFont("SF", "minecraft/whiz/fonts/SF-Regular.ttf"));
        add(new CustomFont("Notis", "minecraft/whiz/fonts/notis.ttf"));
        add(new CustomFont("test", "minecraft/whiz/fonts/stylesicons.ttf"));
        defaultFont = FONTS.get(0);
        currentFont = defaultFont;
        notisFont = FONTS.get(1);
        testFont = FONTS.get(2);
    }

    public void load(CustomFont font) {
        if(font == null) return;
        if(!FONTS.contains(font)) add(font);
        currentFont = font;
    }

    public void add(CustomFont... fonts) {
        for (CustomFont font : fonts) {
            if(!font.setup()) System.out.println("An error has occurred while attempting to load \"" + font.name() + "\"");
            else System.out.println("Successfully loaded \"" + font.name() + "\"");
            FONTS.add(font);
        }
    }

    public CustomFont fontBy(String name) {
        return FONTS.stream().filter(customFont -> customFont.name().equalsIgnoreCase(name)).findFirst().orElse(defaultFont);
    }

    public List<CustomFont> fonts() {
        return FONTS;
    }

    public CustomFont currentFont() {
        return currentFont;
    }

    public CustomFont notisFont() {return notisFont;}

    public CustomFont testFont() {return testFont; }

    public CustomFont defaultFont() {
        return defaultFont;
    }
}
