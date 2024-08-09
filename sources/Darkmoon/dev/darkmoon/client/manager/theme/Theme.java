package dev.darkmoon.client.manager.theme;

import lombok.Getter;

import java.awt.*;

@Getter
public class Theme {
    private final String name;
    private final ThemeType type;
    private final Color[] colors;

    public Theme(String name, ThemeType type, Color... colors) {
        this.name = name;
        this.type = type;
        this.colors = colors;
    }

    public enum ThemeType {
        STYLE
    }
}
