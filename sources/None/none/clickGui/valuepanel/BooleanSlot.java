package none.clickGui.valuepanel;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import none.Client;
import none.module.modules.render.ClientColor;
import none.valuesystem.BooleanValue;

public class BooleanSlot extends VSlot{
	public BooleanValue bool;
	
	public BooleanSlot(BooleanValue bool) {
		this.bool = bool;
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		fontRenderer = Client.fm.getFont("BebasNeue");
		int renderColor = !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100);
		int x = this.x;
		if (bool.getObject()) {
			renderColor = Color.GREEN.getRGB();
		}else if (!bool.getObject()) {
			renderColor = Color.RED.getRGB();
		}
		int renderColor2 = renderColor;
		if (isHovered(mouseX, mouseY)) {
			x += 4;
			renderColor2 = ClientColor.getColor();
		}
		this.width = (int)fontRenderer.getStringWidth(bool.getName()) + 10;
		Gui.drawOutineRect(x + 2, y + 2, x + 12, y + 12, 1, Color.BLACK.getRGB(), renderColor2);
		fontRenderer.drawString(bool.getName(), x + 14, y + fontRenderer.getHeight(bool.getName()), renderColor);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		if (!isHovered(x, y)) return;
		if (button == 0) {
			bool.setObject(!bool.getObject());
		}
		super.mousePressed(button, x, y);
	}
	
	public boolean isHovered(int mouseX, int mouseY) 
	{
		return mouseX >= x && mouseX <= x + 2 + this.width && mouseY >= y && mouseY <= y + 12;
	}
}
