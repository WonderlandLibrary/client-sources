package de.Hero.clickgui.elements.menu;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
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

	/*
	 * Rendern des Elements 
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		String displayval = "" + Math.round(set.getValDouble() * 100D)/ 100D;
		boolean hoveredORdragged = isSliderHovered(mouseX, mouseY) || dragging;
		
		Color temp = ColorUtil.getClickGUIColor();
		int color = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 250 : 200).getRGB();
		int color2 = new Color(temp.getRed(), temp.getGreen(), temp.getBlue(), hoveredORdragged ? 255 : 230).getRGB();
		
		//selected = iset.getValDouble() / iset.getMax();
		double percentBar = (set.getValDouble() - set.getMin())/(set.getMax() - set.getMin());
		
		/*
		 * Die Box und Umrandung rendern
		 */
		Gui.drawRect((int) x, (int) y, (int) (x + width), (int) (y + height),  new Color(0, 0, 0, 170).getRGB());

		/*
		 * Den Text rendern
		 */
		FontUtil.drawString(setstrg, x + 1, y , 0xffffffff);
		FontUtil.drawString(displayval, x + width - FontUtil.getStringWidth(displayval) - 2, y, 0xffffffff);

		/*
		 * Den Slider rendern
		 */
		Gui.drawRect((int) x, (int) (y + 12), (int) (x + width), (int) (y + 13.5), new Color(0, 0, 0, 170).getRGB());
		Gui.drawRect((int) x, (int) (y + 12), (int) (x + (percentBar * width)), (int) (y + 13.5), new Color(200, 140, 244, 255).getRGB());
		
		if(percentBar > 0 && percentBar < 1)
		Gui.drawRect((int) (x + (percentBar * width)-1), (int) (y + 12), (int) (x + Math.min((percentBar * width), width)), (int) (y + 13.5), Color.black.getRGB());
		

		/*
		 * Neue Value berechnen, wenn dragging
		 */
		if (this.dragging) {
			double diff = set.getMax() - set.getMin();
			double val = set.getMin() + (MathHelper.clamp_double((mouseX - x) / width, 0, 1)) * diff;
			set.setValDouble(val); //Die Value im Setting updaten
		}

	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0 && isSliderHovered(mouseX, mouseY)) {
			this.dragging = true;
			return true;
		}
		
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * Wenn die Maus losgelassen wird soll aufgeh�rt werden die Slidervalue zu ver�ndern
	 */
	public void mouseReleased(int mouseX, int mouseY, int state) {
		this.dragging = false;
	}

	/*
	 * Einfacher HoverCheck, ben�tigt damit dragging auf true gesetzt werden kann
	 */
	public boolean isSliderHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y + 11 && mouseY <= y + 14;
	}
}