package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

public class Time extends Module {
    public Time() {
        super("Time", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;

    public void setup(){
        red = new Setting("Red", this, 255, 0, 255, true, "TimeRed");
        green = new Setting("Green", this, 255, 0, 255, true, "TimeGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "TimeBlue");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        AuroraMod.getInstance().settingsManager.rSetting(green);
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        AuroraMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "TimeRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "TimeCustomFont"));
    }

    public void onEnable(){
        disable();
    }
}
