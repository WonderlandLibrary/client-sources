package ru.FecuritySQ.module.дисплей;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.clickgui.ClickScreen;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionColor;

import java.awt.*;

public class ClickGui extends Module {

    public static  OptionColor color = new OptionColor("Цвет", new Color(0, 111, 255));
    public ClickGui() {
        super(Category.Дисплей, GLFW.GLFW_KEY_RIGHT_SHIFT);
        addOption(color);
    }

    @Override
    public void enable() {
        mc.displayGuiScreen(FecuritySQ.get().getClickGui());
        toggle();
        super.enable();
    }
}
