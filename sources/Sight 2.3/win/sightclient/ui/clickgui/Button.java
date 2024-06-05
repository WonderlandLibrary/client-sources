package win.sightclient.ui.clickgui;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import win.sightclient.Sight;
import win.sightclient.fonts.TTFFontRenderer;
import win.sightclient.module.Module;
import win.sightclient.module.settings.BooleanSetting;
import win.sightclient.module.settings.ColorSetting;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.NumberSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.module.settings.TextboxSetting;
import win.sightclient.ui.clickgui.set.Checkbox;
import win.sightclient.ui.clickgui.set.ColorPicker;
import win.sightclient.ui.clickgui.set.Mode;
import win.sightclient.ui.clickgui.set.SetComp;
import win.sightclient.ui.clickgui.set.Slider;
import win.sightclient.ui.clickgui.set.Textbox;
import win.sightclient.utils.TimerUtils;

public class Button {

	private Module mod;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean hovered;
	
	private boolean opened = false;
	
	private ArrayList<SetComp> settings = new ArrayList<SetComp>();
	
	public Button(Module mod) {
		this.mod = mod;
		
		for (Setting s : Sight.instance.sm.getSettingsByMod(this.mod)) {
			if (s instanceof NumberSetting) {
				this.settings.add(new Slider((NumberSetting) s, this));
			}
			if (s instanceof BooleanSetting) {
				this.settings.add(new Checkbox((BooleanSetting) s, this));
			}
			if (s instanceof ModeSetting) {
				this.settings.add(new Mode((ModeSetting) s, this));
			}
			if (s instanceof TextboxSetting) {
				this.settings.add(new Textbox((TextboxSetting) s, this));
			}
			if (s instanceof ColorSetting) {
				this.settings.add(new ColorPicker((ColorSetting) s, this));
			}
		}
	}
	
	public int upTo = 0;
	private TimerUtils timer = new TimerUtils();
	
	public void keyTyped(char typedChar, int keyCode) {
		for (SetComp s : this.settings) {
			s.keyTyped(typedChar, keyCode);
		}
	}
	
	public Module getMod() {
		return this.mod;
	}
	
	public int drawScreen(int mouseX, int mouseY, int x, int y, int width) {
		this.x = x;
		this.y = y;
		this.height = 20;
		this.width = width;
		this.hovered = this.isHovered(mouseX, mouseY);
		Color correctColor = this.mod.isToggled() ? ClickGui.getSecondaryColor(false).darker().darker() : ClickGui.getSecondaryColor(false);
		if (this.hovered) {
			int dark = 4;
			correctColor = new Color(Math.max(correctColor.getRed() - dark, 0), Math.max(correctColor.getGreen() - dark, 0), Math.max(correctColor.getBlue() - dark, 0));
		}
		
		float speed = 256F / (float)Minecraft.getMinecraft().getDebugFPS();
		
		lastRed += (((float)correctColor.getRed() / 255F) - lastRed) / speed;
		lastGreen += (((float)correctColor.getGreen() / 255F) - lastGreen) / speed;
		lastBlue += (((float)correctColor.getBlue() / 255F) - lastBlue) / speed;
		
		lastRed = Math.max(0, Math.min(1, lastRed));
		lastGreen = Math.max(0, Math.min(1, lastGreen));
		lastBlue = Math.max(0, Math.min(1, lastBlue));
		
		Gui.drawRect(x, y, x + width, y + height, new Color(lastRed, lastGreen, lastBlue).getRGB());
		TTFFontRenderer font = ClickGui.getFont();
		font.drawString(this.mod.getName(), this.x + 5, this.y + (this.height / 2) - (font.getHeight(this.mod.getName()) / 2),  ClickGui.getPrimaryColor().getRGB());
		int addVal = 0;
		if ((this.opened || this.upTo > 0) && !this.settings.isEmpty()) {
			if (timer.hasReached(15)) {
				if (this.opened) {
					upTo++;
					if (upTo > this.settings.size()) {
						upTo = this.settings.size();
					}
				} else {
					upTo--;
					if (upTo < 0) {
						upTo = 0;
					}
				}
				timer.reset();
			}
			addVal = this.height;
			for (int i = 0; i < this.settings.size(); i++) {
				if (i < this.upTo) {
					SetComp sc = this.settings.get(i);
					if (sc.getSetting().isVisible()) {
						addVal += sc.drawScreen(mouseX, mouseY, x, y + addVal);
					}
				}
			}
			//Gui.drawRect(this.x, this.y + this.height - 2, this.x + this.getWidth(), this.y + this.height, ClickGui.getPrimaryColor().getRGB());
			addVal -= height;
			//oGui.drawRect(this.x, this.y + addVal + this.height - 2, this.x + this.getWidth(), this.y + addVal + this.height, ClickGui.getPrimaryColor().getRGB());
			
		}
		return this.height + addVal;
	}
	
	private float lastRed = (float)ClickGui.getSecondaryColor(false).getRed() / 255F;
	private float lastGreen = (float)ClickGui.getSecondaryColor(false).getGreen() / 255F;
	private float lastBlue = (float)ClickGui.getSecondaryColor(false).getBlue() / 255F;
	
	public void mouseClicked(int x, int y, int button) {
		this.hovered = this.isHovered(x, y);
		if (this.hovered && button == 0) {
			this.mod.toggle();
		} else if (this.hovered && button == 1) {
			opened = !opened;
		} else if (this.opened) {
			for (SetComp sc : this.settings) {
				sc.mouseClicked(x, y, button);
			}
		}
	}
	
	public void mouseReleased(int mouseX, int mouseY, int state) {
		 if (this.opened) {
			for (SetComp sc : this.settings) {
				sc.mouseReleased(mouseX, mouseY, state);
			}
		 }
	}
	
	private boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + width && mouseY > y && mouseY < y + height;
	}
	
	public int getWidth() {
		return this.width;
	}
}
