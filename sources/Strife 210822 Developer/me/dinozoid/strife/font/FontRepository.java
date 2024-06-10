package me.dinozoid.strife.font;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FontRepository {

    private final List<CustomFont> FONTS = new ArrayList<>();

    private CustomFont defaultFont, currentFont;

    public void init() {
        add(new CustomFont("SF", "assets/minecraft/strife/fonts/SF-Regular.ttf"));
        defaultFont = FONTS.get(0);
        currentFont = defaultFont;
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

    public CustomFont defaultFont() {
        return defaultFont;
    }
}
