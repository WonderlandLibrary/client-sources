package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;

import java.awt.Color;
import java.util.ArrayList;

public class Direction extends Module {
    public Direction() {
        super("Direction", Category.GUI);
        ArrayList<String> modes = new ArrayList<>();
        modes.add("XZ");
        modes.add("NSWE");
        modes.add("Both");
        rSetting(mode = new Setting("Mode", this, "XZ", modes, "DirectionMode"));
        rSetting(rainbow = new Setting("Rainbow", this, false, "DirectionRainbow"));
        rSetting(customFont = new Setting("CFont", this, false, "DirectionCustomFont"));
        rSetting(color = new Setting("Color", this, Color.WHITE, "DirectionColor"));
    }

    public Setting color;
    public Setting mode;
    public Setting rainbow;
    public Setting customFont;

    public void onEnable(){
        disable();
    }
}
