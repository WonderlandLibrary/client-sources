package win.sightclient.ui.clickgui.set;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.client.gui.Gui;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.ui.clickgui.Button;
import win.sightclient.ui.clickgui.ClickGui;

public class Mode extends SetComp {

	private boolean dragging = false;
	private int x;
	private int y;
	private int height;
	private boolean hovered;
	private ModeSetting set;
	
	public Mode(ModeSetting s, Button b) {
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
		String name = this.set.getName() + ": " + this.set.getValue();
		ClickGui.getFont().drawString(name, (this.x + 2), (y + (ClickGui.getFont().getHeight(name) / 2) + 0.4F), ClickGui.getPrimaryColor().getRGB());
		return this.height;
	}
	
	@Override
	public void mouseClicked(int x, int y, int button) {
		if ((button == 0 || button == 1) && this.hovered)  {
			List<String> options = Arrays.asList(this.set.getOptions());
			int index = options.indexOf(this.set.getValue());
			if (button == 0) {
				index++;
			} else if (button == 1) {
				index--;
			}
			if (index >= options.size()) {
				index = 0;
			} else if (index < 0) {
				index = options.size() - 1;
			}
			this.set.setValue(this.set.getOptions()[index], true);
		}
	}
	
	private boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + this.parent.getWidth() && mouseY >= y && mouseY <= y + height;
	}
}
