package de.Hero.clickgui.elements;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.input.Keyboard;

import Squad.Squad;
import Squad.base.Module;
import de.Hero.clickgui.FontUtils;
import de.Hero.clickgui.Panel;
import de.Hero.clickgui.elements.menu.ElementCheckBox;
import de.Hero.clickgui.elements.menu.ElementComboBox;
import de.Hero.clickgui.elements.menu.ElementSlider;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;


public class ModuleButton {
	public Module mod;
	public ArrayList<Element> menuelements;
	public Panel parent;
	public float x;
	public float y;
	public float width;
	public float height;
	public boolean extended = false;
	public boolean listening = false;

	/*
	 * Konstrukor
	 */
	public ModuleButton(Module imod, Panel pl) {
		mod = imod;
		height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2;
		parent = pl;
		menuelements = new ArrayList<Element>();
		/*
		 * Settings wurden zuvor in eine ArrayList eingesetKeyBintragen
		 * dieses SettingSystem hat 3 Konstruktoren je nach
		 *  verwendetem Konstruktor ndert sich die Value
		 *  bei .isCheck() usw. so kann man ganz einfach ohne
		 *  irgendeinen Aufwand bestimmen welches Element
		 *  
		 */
		if (Squad.setmgr.getSettingsByMod(imod) != null)
			for (Setting s : Squad.setmgr.getSettingsByMod(imod)) {
				if (s.isCheck()) {
					menuelements.add(new ElementCheckBox(this, s));
				} else if (s.isSlider()) {
					menuelements.add(new ElementSlider(this, s));
				} else if (s.isCombo()) {
					menuelements.add(new ElementComboBox(this, s));
				}
			}

	}

	/*
	 * Rendern des Elements 
	 */

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		/*
		 * Ist das Module an, wenn ja dann soll
		 *  #ein neues Rechteck in Gre des Buttons den Knopf als Toggled kennzeichnen
		 *  #sich der Text anders frben
		 */
		
    	Collections.sort(Squad.instance.moduleManager.getModules(), new Comparator<Module>() {
    		
    		
    		@Override
    		public int compare(Module mod1 , Module mod2){
    		  			return 0;
    		}
    		
    	});
		
		String modname = mod.name;
		
		/*
		 * (Optional) Wenn das Module ein Keybind hat 
		 * dann soll dieser recht neben dem Module angezeight werden 
		 */
		
		
		/*
		 * Den Namen des Modules in die Mitte (x und y) rendern
		 */
				FontUtils.drawCustomTotalCenteredString(modname, x + width / 2, y - 2 + height / 2, 0xffffffff);
				
		
		if (mod.toggled) {
						FontUtils.drawCustomTotalCenteredString(modname, x + width / 2, y - 2 + height / 2, 0xff1167a7);
								
		}
		
		/*
		 * Ist die Maus ber dem Element, wenn ja dann soll der Button sich anders frben
		 */
		if (isHovered(mouseX, mouseY) && (mod.toggled)) {
					FontUtils.drawCustomTotalCenteredString(modname, x + width / 2, y - 2 + height / 2, 0xff1167a7);
									
		}
		
		if(isHovered(mouseX, mouseY) && !(mod.toggled)) {
						FontUtils.drawCustomTotalCenteredString(modname, x + width / 2, y - 2 + height / 2, 0xff1167a7);
									
		}

	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		mod.setSuffix("");
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
			 * geschlossen werden und dieses geffnet/geschlossen werden
			 */
			if (menuelements != null && menuelements.size() > 0) {
				boolean b = !this.extended;
				Squad.clickgui.closeAllSettings();
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
		 * Wenn listening, dann soll der nchster Key (abgesehen 'ESCAPE') als Keybind fr mod
		 * danach soll nicht mehr gewartet werden!
		 */
		if (listening) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				mod.setKeyBind(keyCode);
			} else {
				mod.setKeyBind(0);
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
