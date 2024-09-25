package none.clickGui.valuepanel;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import none.Client;
import none.clickGui.clickgui;
import none.module.modules.render.ClientColor;
import none.utils.render.TTFFontRenderer;

public class PageV {
	public int x,y, w = 20, h = 10;
	public int number;
	public FontRenderer fontRenderer;
	
	public PageV(int number, int x, int y) {
		this.number = number;
		this.x = x;
		this.y = y;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		fontRenderer = Minecraft.getMinecraft().fontRendererObj;
		int renderColor = ClientColor.getColor();
		if (ClientColor.rainbow.getObject()) {
			renderColor = ClientColor.rainbow(10000);
		}
		int x = this.x;
		
		if (isHovered(mouseX, mouseY)) {
			x+= 4;
			renderColor = ClientColor.getColor();
		}
		Gui.drawOutineRect(x - 2, y - 2, x + 8, y + 10, 2, Color.BLACK.getRGB(), renderColor);
		fontRenderer.drawString(((Integer)number).toString(), x, y, renderColor);
	}
	
	public void mouseReleased(int button, int x, int y) {
		
    }
	public void mousePressed(int button, int x, int y) {
		if (!isHovered(x, y)) return;
		if (button == 0) {
			clickgui.valuePanel.setCurrentPage(number);
		}
	}
	
	public void mouseMoved(int x, int y) {
		
    }
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
	}
}
