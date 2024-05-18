package de.Hero.clickgui.elements;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import me.finz0.osiris.AuroraMod;
import me.finz0.osiris.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import org.lwjgl.input.Keyboard;

import de.Hero.clickgui.Panel;
import de.Hero.clickgui.elements.menu.ElementCheckBox;
import de.Hero.clickgui.elements.menu.ElementComboBox;
import de.Hero.clickgui.elements.menu.ElementSlider;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;

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
		height = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 2;
		parent = pl;
		menuelements = new ArrayList<>();
		/*
		 * Settings wurden zuvor in eine ArrayList eingetragen
		 * dieses SettingSystem hat 3 Konstruktoren je nach
		 *  verwendetem Konstruktor ndert sich die Value
		 *  bei .isCheck() usw. so kann man ganz einfach ohne
		 *  irgendeinen Aufwand bestimmen welches Element
		 *  fr ein Setting bentigt wird :>
		 */
		if (AuroraMod.getInstance().settingsManager.getSettingsByMod(imod) != null)
			for (Setting s : AuroraMod.getInstance().settingsManager.getSettingsByMod(imod)) {
					if (s.isCheck()) {
						menuelements.add(new ElementCheckBox(this, s));
					} else if (s.isSlider()) {
						menuelements.add(new ElementSlider(this, s));
					} else if (s.isCombo()) {
						menuelements.add(new ElementComboBox(this, s));
					} else if(s.isColorPicker()){
					}
			}

	}

	/*
	 * Rendern des Elements 
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		Color temp;
		temp = ColorUtil.getClickGUIColor();

		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
		if(AuroraMod.getInstance().settingsManager.getSettingByID("ClickGuiDesign").getValString().equalsIgnoreCase("f0nzi"))
			color = 0xbb606060;
		if(AuroraMod.getInstance().settingsManager.getSettingByID("ClickGuiDesign").getValString().equalsIgnoreCase("Windows"))
			color = 0xffffffff;
		
		/*
		 * Ist das Module an, wenn ja dann soll
		 *  #ein neues Rechteck in Gre des Buttons den Knopf als Toggled kennzeichnen
		 *  #sich der Text anders frben
		 */
		int textcolor = 0xffafafaf;
		if (mod.isEnabled()) {
			Gui.drawRect((int)x - 2, (int)y, (int)x + (int)width + 2, (int)y + (int)height + 1, color);
			textcolor = 0xffefefef;
			if(AuroraMod.getInstance().settingsManager.getSettingByID("ClickGuiDesign").getValString().equalsIgnoreCase("f0nzi"))
				textcolor = ColorUtil.getClickGUIColor().getRGB();
			if(AuroraMod.getInstance().settingsManager.getSettingByID("ClickGuiDesign").getValString().equalsIgnoreCase("Windows"))
				textcolor = Color.BLUE.getRGB();
		}
		
		/*
		 * Ist die Maus ber dem Element, wenn ja dann soll der Button sich anders frben
		 */
		if (isHovered(mouseX, mouseY)) {
			Gui.drawRect((int)x - 2, (int)y, (int)x + (int)width + 2, (int)y + (int)height + 1, 0x55111111);
		}
		
		/*
		 * Den Namen des Modules in die Mitte (x und y) rendern
		 */
		if(AuroraMod.getInstance().settingsManager.getSettingByID("ClickGuiDesign").getValString().equalsIgnoreCase("Windows")) {
			if(!mod.isEnabled())
				FontUtil.drawTotalCenteredString(mod.getName(), x + width / 2, y + 1 + height / 2, Color.BLACK.getRGB());
			else
				FontUtil.drawTotalCenteredString(mod.getName(), x + width / 2, y + 1 + height / 2, textcolor);
		}else {
			FontUtil.drawTotalCenteredStringWithShadow(mod.getName(), x + width / 2, y + 1 + height / 2, textcolor);
		}

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
			
			//if(OsirisMod.getInstance().settingsManager.getSettingByName("Sound").getValBoolean())
			//Minecraft.getMinecraft().player.playSound("random.click", 0.5f, 0.5f);
		} else if (mouseButton == 1) {
			/*
			 * Wenn ein Settingsmenu existiert dann sollen alle Settingsmenus 
			 * geschlossen werden und dieses geffnet/geschlossen werden
			 */
			if (menuelements != null && menuelements.size() > 0) {
				boolean b = !this.extended;
				//AuroraMod.getInstance().clickGui.closeAllSettings();
				this.extended = b;
				
				//if(OsirisMod.getInstance().settingsManager.getSettingByName("Sound").getValBoolean())
				//if(extended)Minecraft.getMinecraft().player.playSound("tile.piston.out", 1f, 1f);else Minecraft.getMinecraft().player.playSound("tile.piston.in", 1f, 1f);
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
		 * Wenn listening, dann soll der nchster Key (abgesehen 'ESCAPE') als Keybind fr mod
		 * danach soll nicht mehr gewartet werden!
		 */
		if (listening) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				//Client.sendChatMessage("Bound '" + mod.getName() + "'" + " to '" + Keyboard.getKeyName(keyCode) + "'");
				mod.setBind(keyCode);
			} else {
				//Client.sendChatMessage("Unbound '" + mod.getName() + "'");
				mod.setBind(Keyboard.KEY_NONE);
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
