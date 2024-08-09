package dev.darkmoon.client.module.impl.render;

import dev.darkmoon.client.module.setting.impl.ColorSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;

import java.awt.*;

@ModuleAnnotation(name = "CustomFog", category = Category.RENDER)
public class CustomFog extends Module {
    public static ColorSetting color = new ColorSetting("Color", Color.WHITE.getRGB());
    public static NumberSetting distance = new NumberSetting("Fog Distance", 1, 0.2f, 1.5f, 0.01f);
}
