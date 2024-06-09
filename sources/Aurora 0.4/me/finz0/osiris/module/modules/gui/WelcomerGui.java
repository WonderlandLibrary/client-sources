package me.finz0.osiris.module.modules.gui;

import de.Hero.settings.Setting;
import me.finz0.osiris.module.Module;
import me.finz0.osiris.AuroraMod;

import java.util.ArrayList;


public class WelcomerGui extends Module {
    public WelcomerGui() {
        super("Welcome", Category.GUI);
        setDrawn(false);
    }

    public Setting red;
    public Setting green;
    public Setting blue;
    public Setting rainbow;
    public Setting message;
    public Setting customFont;
    ArrayList<String> messages;

    public void setup(){
        messages = new ArrayList<>();
        messages.add("Welcome1");
        messages.add("Welcome2");
        messages.add("Hello1");
        messages.add("Hello2");
        red = new Setting("Red", this, 255, 0, 255, true, "GuiWelcomeRed");
        green = new Setting("Green", this, 255, 0, 255, true, "GuiWelcomeGreen");
        blue = new Setting("Blue", this, 255, 0, 255, true, "GuiWelcomeBlue");
        AuroraMod.getInstance().settingsManager.rSetting(red);
        AuroraMod.getInstance().settingsManager.rSetting(green);
        AuroraMod.getInstance().settingsManager.rSetting(blue);
        rainbow = new Setting("Rainbow", this, true, "GuiWelcomeRainbow");
        AuroraMod.getInstance().settingsManager.rSetting(rainbow);
        AuroraMod.getInstance().settingsManager.rSetting(message = new Setting("Message", this, "Welcome1", messages, "GuiWelcomeMessageMode"));
        AuroraMod.getInstance().settingsManager.rSetting(customFont = new Setting("CFont", this, false, "GuiWelcomeCustomFont"));
    }

    public void onEnable(){
        disable();
    }
}
