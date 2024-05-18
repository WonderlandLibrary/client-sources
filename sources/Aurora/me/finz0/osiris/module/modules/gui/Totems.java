package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

import java.util.ArrayList;

public class Totems extends Module {
    public Totems() {
        super("Totems", Category.GUI);
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
        red = new Setting("Red", this, 255, 0, 255, true, "TotemsRed");
        green = new Setting("Green", this, 255, 0, 255, true, "TotemsGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "TotemsBlue");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        AuroraMod.getInstance().settingsManager.rSetting(green);
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        AuroraMod.getInstance().settingsManager.rSetting(rainbow = new Setting("Rainbow", this, false, "TotemsRainbow"));
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "TotemsCustomFont"));
        AuroraMod.getInstance().settingsManager.rSetting(mode = new Setting("Mode", this, "Item", modes, "TotemsMode"));
    }

    public void onEnable(){
        disable();
    }
}
