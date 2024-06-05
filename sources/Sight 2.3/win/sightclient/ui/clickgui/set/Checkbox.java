package win.sightclient.ui.clickgui.set;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import win.sightclient.module.settings.BooleanSetting;
import win.sightclient.ui.clickgui.Button;
import win.sightclient.ui.clickgui.ClickGui;
import win.sightclient.utils.minecraft.RenderUtils;

public class Checkbox extends SetComp {

	private boolean dragging = false;
	private int x;
	private int y;
	private int height;
	private boolean hovered;
	private BooleanSetting set;
	
	public Checkbox(BooleanSetting s, Button b) {
		super(s, b);
		this.set = s;
	}
	
	@Override
	public int drawScreen(int mouseX, int mouseY, int x, int y) {
		this.hovered = this.isHovered(mouseX, mouseY);
		this.height = 20;
		this.x = x;
		this.y = y;
		
		Gui.drawRect(this.x, this.y, this.x + this.parent.getWidth(), this.y + this.height, ClickGui.getSecondaryColor(true).getRGB());
		String name = this.set.getName();
		ClickGui.getFont().drawString(name, (this.x + 16), (y + (ClickGui.getFont().getHeight(name) / 2) + 0.5F), ClickGui.getPrimaryColor().getRGB());
		RenderUtils.drawRoundedRect(this.x + 3, this.y + 5, this.x + 14, this.y + 14, new Color(0, 0, 0, 0).getRGB(), this.set.getValue() ? ClickGui.getPrimaryColor().getRGB() : new Color(180, 180, 180).getRGB());
		GlStateManager.color(1, 1, 1);
		float x1 = this.set.getValue() ? 5 : 1.5F;
		float x2 = this.set.getValue() ? 3 : -0.5F;
		
		float x1Diff = x1 - this.lastX1;
		float x2Diff = x2 - this.lastX2;
		this.lastX1 += x1Diff / 4;
		this.lastX2 += x2Diff / 4;
		RenderUtils.drawRoundedRect(this.x + lastX1 + 2, this.y + 6, this.x + 10 + lastX2, this.y + 13, new Color(0, 0, 0, 0).getRGB(), new Color(255, 255, 255).getRGB());
		
		return this.height;
	}
	
	private float lastX1 = 1.5F;
	private float lastX2 = -0.5F;
	
	private float red = 0.70588235294F;
	private float green = 0.70588235294F;
	private float blue = 0.70588235294F;
	
	@Override
	public void mouseClicked(int x, int y, int button) {
		if (button == 0 && this.hovered)  {
			this.set.setValue(!this.set.getValue(), true);
		}
	}
	
	private boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + this.parent.getWidth() && mouseY > y && mouseY < y + height;
	}
}
