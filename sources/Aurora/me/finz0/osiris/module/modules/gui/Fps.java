package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

import java.awt.*;

public class Fps extends Module {
    public Fps() {
        super("FPS", Category.GUI);
        setDrawn(false);
    }
    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;
    public void setup(){
        red = new Setting("Red", this, 255, 0, 255, true, "FpsRed");
        green = new Setting("Green", this, 255, 0, 255, true, "FpsGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "FpsBlue");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        AuroraMod.getInstance().settingsManager.rSetting(green);
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        rainbow = new Setting("Rainbow", this, false, "FpsRainbow");
        AuroraMod.getInstance().settingsManager.rSetting(rainbow);
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "FpsCustomFont"));
    }

    public void onEnable(){
        disable();
    }
}
