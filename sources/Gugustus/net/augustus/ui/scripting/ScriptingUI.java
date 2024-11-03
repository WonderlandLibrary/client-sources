package net.augustus.ui.scripting;

import java.io.IOException;

import net.augustus.utils.skid.ohare.RenderUtil;
import net.minecraft.client.gui.GuiScreen;

public class ScriptingUI extends GuiScreen{
	
//	public int X, Y;
//	public int DragX, dragY;
//	public boolean dragging = false;
//	public String script = "";
	
	public void drawScreen() {
	}

	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		super.keyTyped(typedChar, keyCode);
	}

	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}

	protected void mouseReleased(int mouseX, int mouseY, int state) {
		super.mouseReleased(mouseX, mouseY, state);
	}

}
