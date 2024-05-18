package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

import java.util.ArrayList;

public class Coords extends Module {
    public Coords() {
        super("Coordinates", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;
    public Setting decimal;

    public void setup(){
        ArrayList<String> modes = new ArrayList<>();
        modes.add("0");
        modes.add("0.0");
        modes.add("0.00");
        modes.add("0.0#");
        red = new Setting("Red", this, 255, 0, 255, true, "CoordinatesRed");
        green = new Setting("Green", this, 255, 0, 255, true, "CoordinatesGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "CoordinatesBlue");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        AuroraMod.getInstance().settingsManager.rSetting(green);
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        AuroraMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "CoordinatesRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "CoordinatesCustomFont"));
        AuroraMod.getInstance().settingsManager.rSetting(decimal = new Setting("DecimalFormat", this, "0", modes, "CoordinatesDecimalFormat"));
    }

    public void onEnable(){
        disable();
    }
}
