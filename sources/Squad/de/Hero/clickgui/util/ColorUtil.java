package de.Hero.clickgui.util;

import java.awt.Color;

import Squad.Squad;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ColorUtil {
	
	public static Color getClickGUIColor(){
		return new Color((int)Squad.instance.setmgr.getSettingByName("GuiRed").getValDouble(), (int)Squad.instance.setmgr.getSettingByName("GuiGreen").getValDouble(), (int)Squad.instance.setmgr.getSettingByName("GuiBlue").getValDouble());
	}
}
