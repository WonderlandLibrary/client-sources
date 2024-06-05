package de.Hero.clickgui.elements.menu;

import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.clickgui.util.ColorUtil;
import de.Hero.clickgui.util.FontUtil;
import de.Hero.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.math.MathHelper;
import zamorozka.main.Zamorozka;
import zamorozka.ui.RenderingUtils;
import zamorozka.ui.font.Fonts;

import java.awt.*;

/**
 * Made by HeroCode it's free to use but you have to credit me
 *
 * @author HeroCode
 */
public class ElementSlider extends Element {
	public boolean dragging;

	/*
	 * Konstrukor
	 */
	public ElementSlider(ModuleButton iparent, Setting iset) {
		parent = iparent;
		set = iset;
		dragging = false;
		super.setup();
	}

	public static Color setRainbow(double d, float fade) {
		float hue = (float) (System.nanoTime() * -5L + d) / 1.0E10F % 1.0F;
		long color = Long.parseLong(Integer.toHexString(Integer.valueOf(Color.HSBtoRGB(hue, 1.0F, 1.0F)).intValue()),
				16);
		Color c = new Color((int) color);
		return new Color(c.getRed() / 255.0F * fade, c.getGreen() / 255.0F * fade, c.getBlue() / 255.0F * fade);
	}

	/*
	 * Rendern des Elements
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		String displayval = "" + Math.round(set.getValDouble() * 100D) / 100D;
		boolean hoveredORdragged = isSliderHovered(mouseX, mouseY) || dragging;

		Color temp = ColorUtil.getClickGUIColor();
		String mode2 = Zamorozka.settingsManager.getSettingByName("Array Mode").getValString();
		String mode3 = Zamorozka.settingsManager.getSettingByName("Type").getValString();
		int color = -1;
		if (mode3.equalsIgnoreCase("Defaulted")) {
			color = new Color(255, 255, 255).getRGB();
		}
		if (mode3.equalsIgnoreCase("ArrayColor")) {
			color = Zamorozka.getClientColor();
		}
		// selected = iset.getValDouble() / iset.getMax();
		float percentBar = (float) ((set.getValDouble() - set.getMin()) / (set.getMax() - set.getMin()));

		/*
		 * Die Box und Umrandung rendern
		 */
		RenderingUtils.drawRect(x, y, x + width, y + height, 0x99043792);

		/*
		 * Den Text rendern
		 */
		Fonts.comfortaa16.drawString(setstrg, x + 1, y + 2, 0xffffffff);
		Fonts.comfortaa16.drawString(displayval, x + width - FontUtil.getStringWidth(displayval) - 2, y + 2,
				0xffffffff);

		/*
		 * Den Slider rendern
		 */

		RenderingUtils.drawRect(x, y + 12, x + width, y + 13.5, 0xff101010);
		Gui.drawRect(x, y + 11, x + (percentBar * width), y + 13.5, 0xFF5050FD);

		if (percentBar > 0 && percentBar < 1)
			// slider
			RenderingUtils.drawRect(x + (percentBar * width), y + 11, x + Math.min((percentBar * width + 4), width), y + 13.5,
					Zamorozka.getClientColor());

		/*
		 * Neue Value berechnen, wenn dragging
		 */
		if (this.dragging) {
			float diff = (float) (set.getMax() - set.getMin());
			float val = (float) (set.getMin() + (MathHelper.clamp_double((mouseX - x) / width, 0, 1)) * diff);
			set.setValDouble(val); // Die Value im Setting updaten
		}

	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und sollen alle
	 * anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0 && isSliderHovered(mouseX, mouseY)) {
			this.dragging = true;
			return true;
		}

		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * Wenn die Maus losgelassen wird soll aufgehrt werden die Slidervalue zu
	 * verndern
	 */
	public void mouseReleased(int mouseX, int mouseY, int state) {
		this.dragging = false;
	}

	/*
	 * Einfacher HoverCheck, bentigt damit dragging auf true gesetzt werden kann
	 */
	public boolean isSliderHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y + 11 && mouseY <= y + 14;
	}
}