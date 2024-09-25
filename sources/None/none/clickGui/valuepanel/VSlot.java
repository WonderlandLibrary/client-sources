package none.clickGui.valuepanel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import none.Client;
import none.utils.render.TTFFontRenderer;

public class VSlot {
	
	public int offset;
	public int x;
	public int y;
	public int width;
	public int height = 15;
	public int page = 0;
	
	protected Minecraft mc = Minecraft.getMinecraft();
	
	public TTFFontRenderer fontRenderer = Client.fm.getFont("BebasNeue");

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getPage() {
		return page;
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
	
	public void onClose() {
		
	}
	
	public void onKey(char typedChar, int keyCode) {
		
	}
	
	public void onUpdate() {
		
	}
}
