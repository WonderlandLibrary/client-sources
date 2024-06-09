package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

import java.util.ArrayList;

public class Crystals extends Module {
    public Crystals() {
        super("Crystals", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;
    public Setting mode;

    public void setup(){
        ArrayList<String> modes = new ArrayList<>();
        modes.add("Short");
        modes.add("Full");
        modes.add("Item");
        red = new Setting("Red", this, 255, 0, 255, true, "CrystalsRed");
        green = new Setting("Green", this, 255, 0, 255, true, "CrystalsGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "CrystalsBlue");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        AuroraMod.getInstance().settingsManager.rSetting(green);
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        AuroraMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "CrystalsRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "CrystalsCustomFont"));
        AuroraMod.getInstance().settingsManager.rSetting(mode = new Setting("Mode", this, "Item", modes, "CrystalsMode"));
    }

    public void onEnable(){
        disable();
    }
}
