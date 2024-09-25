package none.noneClickGui.Panel.ValuePanel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import none.Client;
import none.module.modules.render.ClientColor;
import none.utils.render.TTFFontRenderer;
import none.valuesystem.Value;

public class ValueSlot {
	
	public int offset;
	public int x;
	public int y;
	public int width;
	public int height = 15;
	
	public TTFFontRenderer jigsawFont = Client.fm.getFont("JIGR 19");
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean isHovered(int mouseX, int mouseY) 
	{
		return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
	}
	
	public void mousePressed(int button, int x, int y) {
	}
	
	public void mouseReleased(int button, int x, int y) {
	}
	public void mouseMoved(int x, int y) {
		
    }
}
