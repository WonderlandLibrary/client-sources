package ru.FecuritySQ.option.imp;

import ru.FecuritySQ.module.дисплей.Theme;
import ru.FecuritySQ.option.Option;
import ru.FecuritySQ.utils.themes.Themes;

public class OptionTheme extends Option {
   private Themes theme;
    public OptionTheme(String name, float current, Themes theme) {
        super(name, current);
        this.theme = theme;
    }

    public Themes getTheme() {
        return theme;
    }
}
