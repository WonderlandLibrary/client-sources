package me.xatzdevelopments.modules.render;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.modules.Module;

public class NoBobbing extends Module {

    public NoBobbing() {
        super("NoBobbing", Keyboard.KEY_O, Category.RENDER, "Prevents bobbing");
    }

    public void onEnable() {
        mc.gameSettings.viewBobbing = false;
        super.onEnable();
    }

    public void onDisable() {
        mc.gameSettings.viewBobbing = true;
        super.onDisable();
    }
}

