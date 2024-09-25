package none.clickGui.valuepanel;

import none.Client;
import none.module.modules.render.ClientColor;
import none.utils.render.TTFFontRenderer;

public class NumberPanel {
	
	public String name;
	public int x,y;
	public TTFFontRenderer fontRenderer = Client.fm.getFont("BebasNeue");
	
	public NumberPanel(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		fontRenderer.drawString(name, x, y, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
	}
	
	public void onMouseClick(int mouseX, int mouseY, int button) {
		
	}
	
	public void onMouseReleased(int mouseX, int mouseY, int button) {
		
	}
}
