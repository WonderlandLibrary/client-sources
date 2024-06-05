package koks.theme;

import koks.theme.themes.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author avox | lmao | kroko
 * @created on 07.09.2020 : 07:43
 */
public class ThemeManager {

    private final List<Theme> themeList = new ArrayList<>();

    public ThemeManager() {
        addTheme(new Jello());
        addTheme(new Moon());
        addTheme(new GAL());
        addTheme(new Clientus());
        addTheme(new Klientus());
        addTheme(new Vega());
        addTheme(new LiquidSense());
        addTheme(new Performance());
        addTheme(new Suicide());
        addTheme(new Modern());
    }

    public void addTheme(Theme theme) {
        this.themeList.add(theme);
    }

    public List<Theme> getThemeList() {
        return themeList;
    }
}
