package dev.darkmoon.client.module.impl.render;

import dev.darkmoon.client.module.setting.impl.ColorSetting;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;

import java.awt.*;

@ModuleAnnotation(name = "Glint", category = Category.RENDER)
public class Glint extends Module {
    public static ModeSetting mode = new ModeSetting("Color Mode", "Theme", "Theme", "Custom");
    public static ColorSetting color = new ColorSetting("Color", new Color(68, 205, 205).getRGB(), () -> mode.get().equals("Custom"));

    public static Color getColor() {
        Color customColor = Color.WHITE;
        switch (mode.get()) {
            case "Theme":
                customColor = Arraylist.getArrayColor(1);
                break;
            case "Custom":
                customColor = color.getColor();
                break;
        }
        return customColor;
    }
}
