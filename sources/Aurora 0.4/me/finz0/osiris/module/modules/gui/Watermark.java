package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

public class Watermark extends Module {
    public static Watermark INSTANCE;
    public Watermark() {
        super("Watermark", Category.GUI);
        setDrawn(false);
        INSTANCE = this;
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting customFont;
    public Setting version;

    public void setup(){
        red = new Setting("Red", this, 255, 0, 255, true, "GuiWatermarkRed");
        green = new Setting("Green", this, 255, 0, 255, true, "GuiWatermarkGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "GuiWatermarkBlue");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        AuroraMod.getInstance().settingsManager.rSetting(green);
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        rainbow = new Setting("Rainbow", this, true, "GuiWatermarkRainbow");
        AuroraMod.getInstance().settingsManager.rSetting(rainbow);
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CustomFont", this, true, "GuiWatermarkCustomFont"));
        rSetting(version = new Setting("Version", this, true, "GuiWatermarkVersionBoolean"));
    }

    public void onEnable(){
        disable();
    }
}
