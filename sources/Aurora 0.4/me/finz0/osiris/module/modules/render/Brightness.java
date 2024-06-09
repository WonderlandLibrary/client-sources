package me.finz0.osiris.module.modules.render;

import me.finz0.osiris.module.Module;

public class Brightness extends Module {
    public Brightness() {
        super("Brightness", Category.RENDER, "Lets you see shit when it's dark");
    }

    float old;

    public void onEnable(){
        old = mc.gameSettings.gammaSetting;
    }

    public void onUpdate(){
        mc.gameSettings.gammaSetting = 666f;
    }

    public void onDisable(){
        mc.gameSettings.gammaSetting = old;
    }
}
