package win.sightclient.ui.clickgui.set;

import java.text.DecimalFormat;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.ui.clickgui.Button;
import win.sightclient.ui.clickgui.ClickGui;

public class Slider extends SetComp {

	private boolean dragging = false;
	private int x;
	private int y;
	private int height;
	private boolean hovered;
	private NumberSetting set;
	
	public Slider(NumberSetting s, Button b) {
		super(s, b);
		this.set = s;
	}
	
	@Override
	public int drawScreen(int mouseX, int mouseY, int x, int y) {
		this.hovered = this.isHovered(mouseX, mouseY);
		this.height = 20;
		this.x = x;
		this.y = y;
		
		if (this.dragging) {
			float toSet = (float)((float)mouseX - (float)this.x) / (float)this.parent.getWidth();
			if (toSet > 1) {
				toSet = 1;
			}
			if (toSet < 0) {
				toSet = 0;
			}
			this.set.setValue(((this.set.getMax() - this.set.getMin()) * toSet) + this.set.getMin(), true);
		}
		float distance = (float) ((this.set.getValueFloat() - this.set.getMin()) / (this.set.getMax() - this.set.getMin()));
		Gui.drawRect(this.x, this.y, this.x + this.parent.getWidth(), this.y + this.height, ClickGui.getSecondaryColor(true).getRGB());
		String name = this.set.getName() + ": " + new DecimalFormat("#.##").format(this.set.getValue());
		Gui.drawRect(this.x, this.y + ClickGui.getFont().getHeight(name) + 6, (int) (this.x + (this.parent.getWidth() * distance)), this.y + this.height - 3, ClickGui.getPrimaryColor().getRGB());
		GlStateManager.pushMatrix();
		float scale = 1;
		GlStateManager.scale(scale, scale, scale);
		ClickGui.getFont().drawString(name, (this.x + 2) / scale, (y + 3) / scale, ClickGui.getPrimaryColor().getRGB());
		GlStateManager.popMatrix();
		return this.height;
	}
	
	@Override
	public void mouseClicked(int x, int y, int button) {
		if (button == 0 && this.hovered)  {
			this.dragging = true;
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		if (state == 0) {
			this.dragging = false;
		}
	}
	
	private boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + this.parent.getWidth() && mouseY >= y && mouseY <= y + height;
	}
}
