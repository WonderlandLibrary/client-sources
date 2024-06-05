package de.Hero.clickgui.util;

import zamorozka.main.Zamorozka;

import java.awt.*;


/**
 * Made by HeroCode
 * it's free to use
 * but you have to credit me
 *
 * @author HeroCode
 */
public class ColorUtil {

    public static Color getClickGUIColor() {
        return new Color((int) Zamorozka.settingsManager.getSettingByName("GuiRed").getValDouble(), (int) Zamorozka.settingsManager.getSettingByName("GuiGreen").getValDouble(), (int) Zamorozka.settingsManager.getSettingByName("GuiBlue").getValDouble());
    }
}
