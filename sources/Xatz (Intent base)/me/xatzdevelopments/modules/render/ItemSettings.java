package me.xatzdevelopments.modules.render;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.modules.Module.Category;
import me.xatzdevelopments.settings.NumberSetting;

public class ItemSettings extends Module {
    public NumberSetting handspeed = new NumberSetting("Hand Speed", 1, 1, 12, 1);
    public NumberSetting swordXValue = new NumberSetting("Sword X", 52, 1, 100, 1);
    public NumberSetting swordYValue = new NumberSetting("Sword Y", 56, 1, 100, 1);
    public NumberSetting itemSize = new NumberSetting("Item Size", 56, 1, 100, 1);

    public ItemSettings() {
        super("ItemSettings", Keyboard.KEY_NONE, Category.RENDER, "Customize items");
        addSettings(handspeed, swordXValue, swordYValue, itemSize);
    }

    public void toggle() {

    }

    public void onEnable() {

    }

    public void onDisable() {

    }
}
