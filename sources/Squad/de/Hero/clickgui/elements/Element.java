package de.Hero.clickgui.elements;

import de.Hero.settings.Setting;

public class Element {
	public ModuleButton parent;
	public Setting set;
	public float offset;
	public float x;
	public float y;
	public float width;
	public float height;

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {}

	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
		return isHovered(mouseX, mouseY);
	}

	public void mouseReleased(int mouseX, int mouseY, int state) {}
	
	public boolean isHovered(int mouseX, int mouseY) 
	{
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
}
