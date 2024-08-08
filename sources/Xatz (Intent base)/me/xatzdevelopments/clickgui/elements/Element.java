package me.xatzdevelopments.clickgui.elements;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.clickgui.ClickGUI;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.settings.Setting;
import me.xatzdevelopments.util.FontUtil;

/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class Element {
	public ClickGUI clickgui;
	public ModuleButton parent;
	//public Setting set;
	//public ModeSetting modeset;
	public double offset;
	public double x;
	public double y;
	public double width;
	public double height;
	public double privheight2;
	
	public String setstrg;
	
	public boolean comboextended;
	
	public void setup(){
		clickgui = parent.parent.clickgui;
	}
	
	public void update(){
		/*
		 * Richtig positionieren! Offset wird von ClickGUI aus bestimmt, sodass
		 * nichts ineinander gerendert wird
		 */
		x = parent.x + parent.width + 2;
		y = parent.y + offset;
		width = parent.width + 10;
		height = 15;
		privheight2 = clickgui.privoff;
		
		/*
		 * Title der Box bestimmen und falls n�tig die Breite der CheckBox
		 * erweitern
		 */
		
		for (final Module m : Xatz.modules) {
		 for(Setting set : m.settings) {
		String sname = set.name;
		if(set instanceof BooleanSetting){
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			double textx = x + width - FontUtil.getStringWidth(setstrg);
			if (textx < x + 13) {
				width += (x + 13) - textx + 1;
			}
		}
		
		if(set instanceof ModeSetting){
			final ModeSetting mode = (ModeSetting)set;
			height = comboextended ? mode.modes.size() * (FontUtil.getFontHeight() + 2) + 15 : 15;
			
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			int longest = FontUtil.getStringWidth(setstrg);
			for (String s : mode.modes) {
				int temp = FontUtil.getStringWidth(s);
				if (temp > longest) {
					longest = temp;
				}
			}
			double textx = x + width - longest;
			if (textx < x) {
				width += x - textx + 1;
			}
		}
		if(set instanceof NumberSetting){
			 final NumberSetting number = (NumberSetting)set;
			setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
			String displayval = "" + Math.round(number.getValue() * 100D)/ 100D;
			String displaymax = "" + Math.round(number.getMaximum() * 100D)/ 100D;
			double textx = x + width - FontUtil.getStringWidth(setstrg) - FontUtil.getStringWidth(displaymax) - 4;
			if (textx < x) {
				width += x - textx + 1;
			}
		}
		}
		 }
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
	
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		return isHovered(mouseX, mouseY);
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {}
	
	public boolean isHovered(int mouseX, int mouseY) 
	{
		
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + privheight2;
	}
}
