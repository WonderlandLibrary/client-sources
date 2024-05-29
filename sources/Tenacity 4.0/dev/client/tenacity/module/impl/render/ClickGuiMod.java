package dev.client.tenacity.module.impl.render;

import dev.client.tenacity.module.Category;
import dev.client.tenacity.module.Module;
import dev.client.tenacity.utils.render.RoundedUtil;
import dev.client.tenacity.utils.render.ShaderUtil;
import dev.settings.impl.BooleanSetting;
import dev.settings.impl.ColorSetting;
import dev.settings.impl.ModeSetting;
import dev.settings.impl.NumberSetting;
import dev.client.tenacity.ui.clickguis.dropdown.DropdownClickGui;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class ClickGuiMod extends Module {

    public static final ModeSetting colorMode = new ModeSetting("Color Mode", "Sync", "Sync", "Double Color", "Static", "Dynamic", "Dynamic Sync");
    public static final ColorSetting color = new ColorSetting("Color", new Color(41, 200, 224));
    public static final ColorSetting color2 = new ColorSetting("Color", new Color(136, 41, 224));
    public static final BooleanSetting accentedSettings = new BooleanSetting("Background Accent", true);
    public static final ModeSetting settingAccent = new ModeSetting("Setting Accent", "White", "White", "Color");
    public static final BooleanSetting walk = new BooleanSetting("Allow Movement", true);
    public static final ModeSetting scrollMode = new ModeSetting("Scroll Mode", "Screen Height", "Screen Height", "Value");
    public static final NumberSetting clickHeight = new NumberSetting("Tab Height", 250, 500, 100, 1);
    public static final DropdownClickGui dropdownClickGui = new DropdownClickGui();

    public ClickGuiMod() {
        super("ClickGui", Category.RENDER, "Displays modules");
        clickHeight.addParent(scrollMode, selection -> selection.is("Value"));
        color.addParent(colorMode, selection -> !selection.is("Sync") || !selection.is("Dynamic Sync"));
        color2.addParent(colorMode, selection -> selection.is("Double Color") || selection.is("Dynamic"));
        this.addSettings(colorMode, color, color2, accentedSettings, settingAccent, walk, scrollMode, clickHeight);
        this.setKey(Keyboard.KEY_RSHIFT);
    }

    public void toggle() {
        this.onEnable();
    }

    public void onEnable() {
        mc.displayGuiScreen(dropdownClickGui);
        RoundedUtil.roundedShader = new ShaderUtil("roundedRect");
    }

}
