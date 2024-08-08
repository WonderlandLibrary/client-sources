package me.xatzdevelopments.modules.render;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.modules.Module;

public class Australia extends Module {
    
    public Australia() {
        super("Australia", Keyboard.KEY_NONE, Category.RENDER, "The land of the upside down.");
    }
    
    public void onEnable() {
        super.onEnable();
    }
    
    public void onDisable() {
        mc.gameSettings.invertMouse = false;
        super.onDisable();
    }

}