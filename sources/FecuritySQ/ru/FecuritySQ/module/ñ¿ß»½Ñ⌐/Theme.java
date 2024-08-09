package ru.FecuritySQ.module.дисплей;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionTheme;
import ru.FecuritySQ.utils.themes.Themes;

public class Theme extends Module {
    public Theme() {
        super(Category.Дисплей, GLFW.GLFW_KEY_0);
        for (Themes themes:Themes.values()){
            OptionTheme themi = new OptionTheme(themes.name(), 0, themes);
            addOption(themi);
        }
    }
}
