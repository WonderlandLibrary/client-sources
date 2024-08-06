package club.strifeclient.module.implementations.visual;

import club.strifeclient.module.Category;
import club.strifeclient.module.Module;
import club.strifeclient.module.ModuleInfo;
import club.strifeclient.setting.implementations.ColorSetting;

import java.awt.*;

@ModuleInfo(name = "GlintColorize", description = "Changes the glint color of enchanted items.", category = Category.VISUAL)
public final class GlintColorize extends Module {
    public final ColorSetting colorSetting = new ColorSetting("Color", new Color(209, 50, 50));
}
