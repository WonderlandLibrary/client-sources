package de.Hero.clickgui.elements.menu;


import Squad.Squad;
import de.Hero.clickgui.FontUtils;
import de.Hero.clickgui.elements.Element;
import de.Hero.clickgui.elements.ModuleButton;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;


public class ElementComboBox extends Element {
	
	private boolean extended;
	private String selected;
	/*
	 * Konstrukor
	 */
	 public ElementComboBox(ModuleButton iparent, Setting iset) {
		parent = iparent;
		set = iset;
		selected = iset.getValString();
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
		height = extended ? set.getOptions().size() * (FontUtils.getFontHeight() + 2) + 15 : 15;

		/*
		 * Title der Box bestimmen und falls nötig die Breite der ComboBox
		 * erweitern
		 */
		String sname = set.getName();
		String setstrg = sname.substring(0, 1).toUpperCase() + sname.substring(1, sname.length());
		float longest = FontUtils.getStringWidth(setstrg);
		for (String s : set.getOptions()) {
			float temp = FontUtils.getStringWidth(s);
			if (temp > longest) {
				longest = temp;
			}
		}
		double textx = x + width - longest;
		if (textx < x) {
			width += x - textx + 1;
		}

		/*
		 * Die Box und Umrandung rendern
		 */
    Gui.drawRect(x , y - 0.5 , x + width + 0.5, y + height, 0xff000000);
		//RenderUtils.drawRect(x - 0.25, y - 0.25, x + width + 0.25, y + height + 0.25, 0xDDdfdfdf);
		//RenderUtils.drawRect(x, y, x + width, y + height, 0xDD0f0f0f);
    	
				FontUtils.drawCustomTotalCenteredString(setstrg, x + width / 2 + 4, y + 11 / 2, 0xffffffff);
		
		int clr1 = 0xff1AB4E8;
		int clr2 = 0xff1AB4E8;

		if (extended) {

			Gui.drawRect(x, y + 15, x + width, y + height, 0x80000000);

	
	
			float ay = y + 15;
			for (String sld : set.getOptions()) {
				String elementtitle = sld.substring(0, 1).toUpperCase() + sld.substring(1, sld.length());
						FontUtils.drawCustomCenteredString(elementtitle, x + width / 2 + 3, ay + 0.5F, 0xff1167a7);
				
				
				/*
				 * Ist das Element ausgewählt, wenn ja dann markiere
				 * das Element in der ComboBox
				 */
				if (sld.equalsIgnoreCase(selected)) {
					Gui.drawRect(x, ay, x + 1.5, ay + FontUtils.getFontHeight() + 2, 0xff1167a7);
				}
				/*
				 * Wie bei mouseClicked 'is hovered', wenn ja dann markiere
				 * das Element in der ComboBox
				 */
				if (mouseX >= x && mouseX <= x + width && mouseY >= ay && mouseY < ay + FontUtils.getFontHeight() + 2) {
					Gui.drawRect(x + width - 1, ay, x + width, ay + FontUtils.getFontHeight() + 2, 0xff1167a7);
				}
				ay += FontUtils.getFontHeight() + 2;
			}

		}
	}

	/*
	 * 'true' oder 'false' bedeutet hat der Nutzer damit interagiert und
	 * sollen alle anderen Versuche der Interaktion abgebrochen werden?
	 */
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (mouseButton == 0) {
			if (isButtonHovered(mouseX, mouseY)) {
				extended = !extended;
				return true;
			}
			
			/*
			 * Also wenn die Box ausgefahren ist, dann wird für jede mögliche Options
			 * berprft, ob die Maus auf diese zeigt, wenn ja dann global jeder weitere 
			 * call an mouseClicked gestoppt und die Values werden aktualisiert
			 */
			if (!extended)return false;
			double ay = y + 15;
			for (String slcd : set.getOptions()) {
				if (mouseX >= x && mouseX <= x + width && mouseY >= ay && mouseY <= ay + FontUtils.getFontHeight() + 2) {
				Squad.instance.setmgr.getSettingByName(set.getName()).setValString(slcd.toLowerCase());
					selected = slcd;
					return true;
				}
				ay += FontUtils.getFontHeight() + 2;
			}
		}

		return super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	/*
	 * Einfacher HoverCheck, bentigt damit die Combobox geffnet und geschlossen werden kann
	 */
	public boolean isButtonHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 15;
	}
}
