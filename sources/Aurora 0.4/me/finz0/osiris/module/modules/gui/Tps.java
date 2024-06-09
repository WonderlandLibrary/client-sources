package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

public class Tps extends Module {
    public static Tps INSTANCE;
    public Tps() {
        super("TPS", Category.GUI);
        setDrawn(false);
        INSTANCE = this;
    }



    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;

    public void setup(){
        red = new Setting("Red", this, 255, 0, 255, true, "TpsRed");
        green = new Setting("Green", this, 255, 0, 255, true, "TpsGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "TpsBlue");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        AuroraMod.getInstance().settingsManager.rSetting(green);
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        AuroraMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "TpsRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "TpsCustomFont"));
    }

    public void onEnable(){
        disable();
    }
}
