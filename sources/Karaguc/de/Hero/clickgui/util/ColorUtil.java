package de.Hero.clickgui.util;

import me.Emir.Karaguc.Karaguc;

import java.awt.*;

/**
 * Made by KaragucCode it's free to use but you have to credit me
 *
 * @author KaragucCode
 */
public class ColorUtil {

	public static Color getClickGUIColor() {
		return new Color((int) Karaguc.instance.settingsManager.getSettingByName("GuiRed").getValDouble(),
				(int) Karaguc.instance.settingsManager.getSettingByName("GuiGreen").getValDouble(),
				(int) Karaguc.instance.settingsManager.getSettingByName("GuiBlue").getValDouble());
	}
}
