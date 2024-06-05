package de.Hero.clickgui.elements;

import de.Hero.clickgui.Panel;
import de.Hero.clickgui.elements.menu.ElementCheckBox;
import de.Hero.clickgui.elements.menu.ElementComboBox;
import de.Hero.clickgui.elements.menu.ElementSlider;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.SoundEvents;
import org.lwjgl.input.Keyboard;
import zamorozka.gui.GuiIngameHook;
import zamorozka.main.Zamorozka;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;
import zamorozka.modules.HUD.Click;
import zamorozka.ui.Colors;
import zamorozka.ui.FileManager;
import zamorozka.ui.RenderingUtils;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
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
	 * Rendern des Elements
	 */
	private int scroll;

	/*
	 * Konstrukor
	 */
	public ModuleButton(Module imod, Panel pl) {

		mod = imod;
		height = Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 2;
		parent = pl;
		menuelements = new ArrayList<>();

		if (Zamorozka.settingsManager.getSettingsByMod(imod) != null)
			for (Setting s : Zamorozka.settingsManager.getSettingsByMod(imod)) {
				if (s.isCheck()) {
					menuelements.add(new ElementCheckBox(this, s));
				} else if (s.isSlider()) {
					menuelements.add(new ElementSlider(this, s));
				} else if (s.isCombo()) {
					menuelements.add(new ElementComboBox(this, s));
				}
			}

	}

	public void drawScreen(double d, double e, float partialTicks) {
		Color temp = ColorUtil.getClickGUIColor();
		int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
		String mode2 = Zamorozka.settingsManager.getSettingByName("Array Mode").getValString();
		String mode3 = Zamorozka.settingsManager.getSettingByName("Type").getValString();
		int color = -1;
		if (mode3.equalsIgnoreCase("Defaulted")) {
			color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), 150).getRGB();
		}
		if (mode3.equalsIgnoreCase("ArrayColor")) {
			color = Zamorozka.getClientColor();
		}
		int textcolor = 0xffafafaf;

		if (mod.getState()) {
			RenderingUtils.drawRect(x - 2, y, x + width + 2, y + height, color);
			textcolor = 0xffefefef;
		}
		RenderingUtils.drawRect(x - 2, y, x + width + 2, y + height + 1, 0x35000000);
		FontUtil.drawTotalCenteredStringWithShadow(mod.getName(), x + width / 2D, y + 1 + height / 2D, textcolor);

		/**
		 * int dw = Mouse.getEventDWheel(); if(dw != 0) { if (dw > 0) { dw = -1; } else
		 * { dw = 1; } float amountScrolled = (float) (dw *0.1);
		 * 
		 * 
		 * height += amountScrolled;
		 * 
		 * }
		 */

	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und sollen alle
	 * anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (!isHovered(mouseX, mouseY))
			return false;

		if (mouseButton == 0) {
			mod.toggleModule();
			if (ModuleManager.getModule(Click.class).getState()) {
				Minecraft.player.playSound(SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, 1F, 1F * 0.4F);
			}

		} else if (mouseButton == 1) {
			if (menuelements != null && menuelements.size() > 0) {
				boolean b = !this.extended;
				Zamorozka.clickGui.closeAllSettings();
				this.extended = b;
			}
		} else if (mouseButton == 2) {
			listening = true;
		}
		return true;
	}

	public boolean keyTyped(char typedChar, int keyCode) throws IOException {
		/*
		 * Wenn listening, dann soll der nchster Key (abgesehen 'ESCAPE') als Keybind fr
		 * mod danach soll nicht mehr gewartet werden!
		 */
		if (listening) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				// Client.sendChatMessage("Bound '" + mod.getName() + "'" + " to '" +
				// Keyboard.getKeyName(keyCode) + "'");
				mod.setKey(keyCode);
				FileManager.saveKeybinds();
			} else {
				// Client.sendChatMessage("Unbound '" + mod.getName() + "'");
				mod.setKey(Keyboard.KEY_NONE);
				FileManager.saveKeybinds();
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
