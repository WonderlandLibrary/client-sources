package win.sightclient.ui.clickgui.set;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import win.sightclient.module.settings.ColorSetting;
import win.sightclient.ui.clickgui.Button;
import win.sightclient.ui.clickgui.ClickGui;

public class ColorPicker extends SetComp {

	private ColorSetting set;
	private boolean draggingBox;
	private boolean draggingHue;
	
	private boolean hovered;
	private int height;
	private int x;
	private int y;
	private double size;
	private int yOffset;
	private int xOffset;
	
	public ColorPicker(ColorSetting s, Button parent) {
		super(s, parent);
		this.set = s;
	}

	@Override
	public int drawScreen(int mouseX, int mouseY, int x, int y) {
		this.hovered = this.isHovered(mouseX, mouseY);
		this.height = 80;
		this.x = x;
		this.y = y;
		
		double distance = 0.01F;
		size = 60;
		
		xOffset = 7;
		yOffset = 14;
		Gui.drawRect(this.x, this.y, this.x + this.parent.getWidth(), this.y + this.height, ClickGui.getSecondaryColor(true).getRGB());
		
		Gui.drawRect(x + xOffset, y + 3, x + xOffset + ClickGui.getFont().getStringWidth(this.set.getName()), y + 12, Color.HSBtoRGB(1, 0F, 1F - this.set.getBrightness()));
		ClickGui.getFont().drawStringWithShadow(this.set.getName(), x + xOffset, y + 3, this.set.getColor());
		
		Gui.drawRect(this.x + this.xOffset, this.y + this.yOffset - 1, this.x + this.xOffset + size + 1, this.y + this.yOffset, -1);
		Gui.drawRect(this.x + this.xOffset, this.y + this.yOffset + this.size + 1, this.x + this.xOffset + size + 1, this.y + this.yOffset + this.size + 2, -1);
		Gui.drawRect(this.x + this.xOffset - 1, this.y + this.yOffset - 1, this.x + this.xOffset, this.y + this.yOffset + this.size + 2, -1);
		Gui.drawRect(this.x + this.xOffset + this.size + 1, this.y + this.yOffset - 1, this.x + this.xOffset + this.size + 2, this.y + this.yOffset + this.size + 2, -1);
		for (double brightness = 0; brightness < 1; brightness += distance) {
			for (double saturation = 0; saturation < 1; saturation += distance) {
				double startX = x + (saturation * size) + xOffset;
				double endX = x + (saturation * size) + xOffset + 1;
				double startY = y + ((1 - brightness) * size) + yOffset;
				double endY = y + ((1 - brightness) * size) + 1 + yOffset;
				Gui.drawRect(startX, startY, endX, endY, Color.HSBtoRGB(this.set.getHue(), (float)saturation, (float)brightness));
				
				if (this.draggingBox) {
					if (mouseX >= (saturation == 0 ? -99999999 : startX) && mouseX <= (saturation > 0.9999999 ? 99999999 : endX) && mouseY >= (brightness >=  0.999999 ? -9999999 : startY) && mouseY <= (brightness == 0 ? 9999999 : endY)) {
						this.set.setSaturation((float) saturation);
						this.set.setBrightness((float) brightness);
					}
				}
			}
		}
		
		Gui.drawRect(this.x + this.xOffset + this.size + 10, this.y + this.yOffset - 1, this.x + this.xOffset + this.size + 25, this.y + this.yOffset, -1);
		Gui.drawRect(this.x + this.xOffset + this.size + 10, this.y + this.yOffset + this.size + 1, this.x + this.xOffset + this.size + 25, this.y + this.yOffset + this.size + 2, -1);
		Gui.drawRect(this.x + this.xOffset + this.size + 9, this.y + this.yOffset - 1, this.x + this.xOffset + this.size + 10, this.y + this.yOffset + this.size + 2, -1);
		Gui.drawRect(this.x + this.xOffset + this.size + 25, this.y + this.yOffset - 1, this.x + this.xOffset + this.size + 26, this.y + this.yOffset + this.size + 2, -1);
		for (double hue = 0; hue < 1; hue += distance) {
			double startX = x + size + xOffset + 10;
			double endX = x + size + xOffset + 25;
			double startY = y + ((1 - hue) * size) + yOffset;
			double endY = y + ((1 - hue) * size) + 1 + yOffset;
			Gui.drawRect(startX, startY, endX, endY, Color.HSBtoRGB((float)hue, 1F, 1F));
			
			if (this.draggingHue) {
				if (mouseX >= startX && mouseX <= endX && mouseY >= startY && mouseY <= endY) {
					this.set.setHue((float) hue);
				}
			}
		}
		
		return height;
	}
	
	@Override
	public void mouseClicked(int x, int y, int button) {
		if (button == 0 && this.hovered) {
			if (x >= this.x + this.xOffset && x <= this.x + this.xOffset + this.size && y > this.y && y < this.y + this.height) {
				draggingBox = true;
				draggingHue = false;
			} else {
				draggingBox = false;
				draggingHue = true;
			}
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int state) {
		if (state == 0) {
			draggingBox = false;
			draggingHue = false;
		}
	}
	
	private boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + this.parent.getWidth() && mouseY > y && mouseY < y + height;
	}
}
