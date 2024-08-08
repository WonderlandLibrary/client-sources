package me.xatzdevelopments.clickgui.elements;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.clickgui.Panel;
import me.xatzdevelopments.clickgui.elements.menu.ElementCheckBox;
import me.xatzdevelopments.clickgui.elements.menu.ElementComboBox;
import me.xatzdevelopments.clickgui.elements.menu.ElementSlider;
import me.xatzdevelopments.util.ColorUtil;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.BooleanSetting;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.settings.Setting;
import me.xatzdevelopments.util.ChatUtils;
import me.xatzdevelopments.util.FontUtil;



//Deine Imports


/**
 *  Made by HeroCode
 *  it's free to use
 *  but you have to credit me
 *
 *  @author HeroCode
 */
public class ModuleButton {
	public Module mod;
	public ArrayList<Element> menuelements;
	public Panel parent;
	public double x;
	public double y;
	public double width;
	public double height;
	public boolean extended = false;
	public boolean listening = false;

	/*
	 * Konstrukor
	 */
	public ModuleButton(Module imod, Panel pl) {
		mod = imod;
		height = FontUtil.getFontHeight() + 2;
		parent = pl;
		menuelements = new ArrayList<>();
		/*
		 * Settings wurden zuvor in eine ArrayList eingetragen
		 * dieses SettingSystem hat 3 Konstruktoren je nach
		 *  verwendetem Konstruktor �ndert sich die Value
		 *  bei .isCheck() usw. so kann man ganz einfach ohne
		 *  irgendeinen Aufwand bestimmen welches Element
		 *  f�r ein Setting ben�tigt wird :>
		 */
		
				for(Setting s : mod.settings) {
				if (s instanceof BooleanSetting) {
					final BooleanSetting bool = (BooleanSetting)s;
					menuelements.add(new ElementCheckBox(this, bool));
				}
				if (s instanceof NumberSetting) {
					final NumberSetting number = (NumberSetting)s;
					menuelements.add(new ElementSlider(this, number));
				}
				if (s instanceof ModeSetting) {
					final ModeSetting mode = (ModeSetting)s;
					menuelements.add(new ElementComboBox(this, mode));
				}
				}
		 }
	

	/*
	 * Rendern des Elements 
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
		
		/*
		 * Ist das Module an, wenn ja dann soll
		 *  #ein neues Rechteck in Gr��e des Buttons den Knopf als Toggled kennzeichnen
		 *  #sich der Text anders f�rben
		 */
		int textcolor = 0xffafafaf;
		if (mod.isEnabled()) {
			Gui.drawRect(x - 2, y, x + width + 2, y + height + 1, color);
			textcolor = 0xffefefef;
		}
		
		/*
		 * Ist die Maus �ber dem Element, wenn ja dann soll der Button sich anders f�rben
		 */
		if (isHovered(mouseX, mouseY)) {
			Gui.drawRect(x - 2, y, x + width + 2, y + height + 1, 0x55111111);
			Gui.drawRect(x + width / 2 + 40, y, x + width / 2 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(mod.description) + 46, y + height + 1, 0x751c1c1c);
			FontUtil.drawTotalCenteredStringWithShadow(mod.description, x + width / 2 + Minecraft.getMinecraft().fontRendererObj.getStringWidth(mod.description)/2 + 43, y + 1 + height / 2, -1);
		}
		
		/*
		 * Den Namen des Modules in die Mitte (x und y) rendern
		 */
		FontUtil.drawTotalCenteredStringWithShadow(mod.name, x + width / 2, y + 1 + height / 2, textcolor);
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!isHovered(mouseX, mouseY))
			return false;

		/*
		 * Rechtsklick, wenn ja dann Module togglen, 
		 */
		if (mouseButton == 0) {
			mod.toggle();
			
			
		} else if (mouseButton == 1) {
			/*
			 * Wenn ein Settingsmenu existiert dann sollen alle Settingsmenus 
			 * geschlossen werden und dieses ge�ffnet/geschlossen werden
			 */
			if (menuelements != null && menuelements.size() > 0) {
				boolean b = !this.extended;
				Xatz.clickgui.closeAllSettings();
				this.extended = b;
				
				
			}
		} else if (mouseButton == 2) {
			/*
			 * MidClick => Set keybind (wait for next key)
			 */
			listening = true;
		}
		return true;
	}

	public boolean keyTyped(char typedChar, int keyCode) throws IOException {
		/*
		 * Wenn listening, dann soll der n�chster Key (abgesehen 'ESCAPE') als Keybind f�r mod
		 * danach soll nicht mehr gewartet werden!
		 */
		if (listening) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				ChatUtils.sendMessage("Bound '" + mod.name + "'" + " to '" + Keyboard.getKeyName(keyCode) + "'");
				mod.keycode.setKeyCode(keyCode);
			} else {
				ChatUtils.sendMessage("Unbound '" + mod.name + "'");
				mod.keycode.setKeyCode(Keyboard.KEY_NONE);
			}
			listening = false;
			return true;
		}
		return false;
	}

	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}

}
