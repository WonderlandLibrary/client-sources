package none.utils;

import java.awt.Color;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import none.utils.render.Colors;

public class XYGui {
	
	public int x,y,x2,y2;
	public String name;
	public FontRenderer fr;
	public XYGui(String namebutton,int x, int y, int x2, int y2, FontRenderer fr) {
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.name = namebutton;
		this.fr = fr;
	}
	
	public void drawButton(int mouseX, int mouseY, float partialTicks) {
		int x = this.x;
		if (onHover(mouseX, mouseY)) {
			x += 8;
		}
		Gui.drawOutlineRGB(this.x, y, x2, y2, 2, Colors.getColor(Color.BLACK, 150));
		fr.drawString(getName(), ((x + x2) / 2) - (fr.getStringWidth(getName()) / 2), (y + y2) / 2 - (fr.FONT_HEIGHT / 2), Colors.getColor(Color.GREEN, 200));
	}
	
	public boolean onHover(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
	}
	
	public boolean onClicked(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2;
	}
	
	public String getName() {
		return name;
	}
}
