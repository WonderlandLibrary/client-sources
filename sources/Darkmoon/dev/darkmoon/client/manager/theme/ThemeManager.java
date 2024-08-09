package dev.darkmoon.client.manager.theme;

import lombok.Getter;
import lombok.Setter;

public class ThemeManager {
    @Getter
    @Setter
    private Theme currentStyleTheme;

    public ThemeManager() {
        this.currentStyleTheme = Themes.FRESH.getTheme();
    }
}
