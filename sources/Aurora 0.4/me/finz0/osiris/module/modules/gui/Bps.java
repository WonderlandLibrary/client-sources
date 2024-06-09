package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

public class Bps extends Module {
    public Bps() {
        super("BPS", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;

    public void setup(){
        red = new Setting("Red", this, 255, 0, 255, true, "BlocksPerSecondRed");
        green = new Setting("Green", this, 255, 0, 255, true, "BlocksPerSecondGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "BlocksPerSecondBlue");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        AuroraMod.getInstance().settingsManager.rSetting(green);
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        rainbow = new Setting("Rainbow", this, false, "BlocksPerSecondRaiknbow");
        AuroraMod.getInstance().settingsManager.rSetting(rainbow);
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "BlocksPerSecondCustomFont"));
    }

    public void onEnable(){
        disable();
    }


}
