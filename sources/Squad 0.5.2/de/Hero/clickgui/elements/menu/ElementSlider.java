package de.Hero.clickgui.elements.menu;

import de.Hero.clickgui.FontUtils;
import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.settings.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;


public class ElementSlider extends Element {
	
	private double selected;
	public boolean dragging;

	/*
	 * Konstrukor
	 */
	public ElementSlider(ModuleButton iparent, Setting iset) {
		parent = iparent;
		set = iset;
		selected = iset.getValDouble() / iset.getMax();
		dragging = false;
	}
	
	/*
	 * Rendern des Elements 
	 */
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		/*
		 * Richtig positionieren! Offset wird von ClickGUI aus bestimmt, sodass
		 * nichts ineinander gerendert wird
		 */
		x = parent.x + parent.width + 3;
		y = parent.y + offset;
		width = parent.width + 10;
		height = 15;

		/*
		 * Title der Box bestimmen und falls ntig die Breite des Sliders
		 * erweitern
		 */
		String sname = set.getName();
		String setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
		String displayval = "" + Math.round(set.getValDouble() * 100D)/ 100D;
		String displaymax = "" + Math.round(set.getMax() * 100D)/ 100D;
		double textx = x + width - FontUtils.getStringWidth(setstrg) - FontUtils.getStringWidth(displaymax) - 4;
		if (textx < x) {
			width += x - textx + 1;
		}

		/*
		 * Die Box und Umrandung rendern
		 */
    	Gui.drawRect(x , y - 0.50 , x + width, y + height + 0.50, 0xff000000);
		//RenderUtils.drawRect(x, y, x + width, y + height, 0xDD0f0f0f);

		/*
		 * Den Text rendern
		 */
				FontUtils.drawCustomString(setstrg, x + 1, y - 2, 0xffffffff);
		
				FontUtils.drawCustomString(displayval, x - 3 + width - FontUtils.getCustonStringWidth(displayval), y - 2, 0xff1167a7);
		

		/*
		 * Den Slider rendern
		 */
				Gui.drawRect(x, y + 12, x + width, y + 17, 0xff101010);
				Gui.drawRect(x, y + 12, x + (this.selected * width), y + 17, 0xff1167a7);
			
				/*
		 * Neue Value berechnen, wenn dragging
		 */
			
				if (this.dragging) {
			double diff = set.getMax() - set.getMin(); 							     // SettingDifference
			double percentBar = MathHelper.clamp_double((mouseX - x) / width, 0, 1); // Unterschied von Balken zur Maus in Prozent
		
		
			double val = set.getMin() + percentBar * diff; 							 // Die Value fr das  Setting berechnen
			set.setValDouble(val);													 // Die Value im Setting updaten
			this.selected = percentBar;												 // Die Prozentvalue zum rendern des Sliders
		
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
	 * Wenn die Maus losgelassen wird soll aufgehrt werden die Slidervalue zu verndern
	 */
	public void mouseReleased(int mouseX, int mouseY, int state) {
		this.dragging = false;
	}

	/*
	 * Einfacher HoverCheck, bentigt damit dragging auf true gesetzt werden kann
	 */
	public boolean isSliderHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y + 11 && mouseY <= y + 17;
	}
}