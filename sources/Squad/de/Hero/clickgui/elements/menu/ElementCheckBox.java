package de.Hero.clickgui.elements.menu;


import de.Hero.clickgui.FontUtils;
import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.settings.Setting;
import net.minecraft.client.gui.Gui;

public class ElementCheckBox extends Element {

	/*
	 * Konstrukor
	 */
	public ElementCheckBox(ModuleButton iparent, Setting iset) {
		parent = iparent;
		set = iset;
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
		 * Title der Box bestimmen und falls nötig die Breite der CheckBox
		 * erweitern
		 */
		String sname = set.getName();
		String setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
		float textx = x + width - FontUtils.getStringWidth(setstrg);
		if (textx < x + 1) {
			width += (x + 1) - textx + 1;
		}

		/*
		 * Die Box und Umrandung rendern
		 */
		Gui.drawRect(x, y - 0.50, x + width + 0.25, y + height + 0.60, 0xff000000);
		//RenderUtils.drawRect(x, y, x + width, y + height, 0xDD0f0f0f);

		/*
		 * Titel und Checkbox rendern.
		 */
				FontUtils.drawCustomString(setstrg, x + width - 1 - FontUtils.getCustonStringWidth(setstrg), y + FontUtils.getFontHeight() / 4.3F - 0.5F, 0xffffffff);
				Gui.drawRect(x + 2, y + 2.6F, x + 11, y + 12, set.getValBoolean() ? 0xff1167a7 : 0xffffffff);

				if (isCheckHovered(mouseX, mouseY)){
			
		}
			
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0 && isCheckHovered(mouseX, mouseY)) {
			set.setValBoolean(!set.getValBoolean());
			return true;
		}
		
		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * Einfacher HoverCheck, benötigt damit die Value geändert werden kann
	 */
	public boolean isCheckHovered(int mouseX, int mouseY) {
		return mouseX >= x + 1 && mouseX <= x + 12 && mouseY >= y + 2 && mouseY <= y + 13;
	}
}
