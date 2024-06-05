package win.sightclient.ui.clickgui.set;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatAllowedCharacters;
import win.sightclient.module.settings.Setting;
import win.sightclient.module.settings.TextboxSetting;
import win.sightclient.ui.clickgui.Button;
import win.sightclient.ui.clickgui.ClickGui;

public class Textbox extends SetComp {

	private boolean dragging = false;
	private int x;
	private int y;
	private int height;
	private boolean hovered;
	private boolean selected;
	private TextboxSetting set;
	
	public Textbox(TextboxSetting s, Button b) {
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
		Gui.drawRect(this.x + 2, this.y + 2, this.x - 2 + this.parent.getWidth(), this.y + this.height - 2, ClickGui.getSecondaryColor(true).darker().getRGB());
		if (this.selected) {
			Gui.drawRect(this.x + 2, this.y + 1, this.x - 2 + this.parent.getWidth(), this.y + 2, ClickGui.getSecondaryColor(true).darker().darker().getRGB());
			Gui.drawRect(this.x + 2, this.y + this.height - 2, this.x - 2 + this.parent.getWidth(), this.y + this.height - 1, ClickGui.getSecondaryColor(true).darker().darker().getRGB());
			//Gui.drawRect(this.x + 2, this.y + 2, this.x - 2 + this.parent.getWidth(), this.y + this.height - 2, ClickGui.getSecondaryColor().darker().getRGB());
			Gui.drawRect(this.x + 1, this.y + 1, this.x + 2, this.y + this.height - 1, ClickGui.getSecondaryColor(true).darker().darker().getRGB());
			Gui.drawRect(this.x + this.parent.getWidth() - 1, this.y + 1, x + this.parent.getWidth() - 2, this.y + this.height - 1, ClickGui.getSecondaryColor(true).darker().darker().getRGB());
		}
		if (this.set.getValue().isEmpty()) {
			ClickGui.getFont().drawCenteredString(name, (this.x + (this.parent.getWidth() / 2)), (y + (ClickGui.getFont().getHeight(name) / 2) + 0.4F), ClickGui.getPrimaryColor().getRGB());
		} else {
			String toRender = this.set.getValue();
			int cutOffIndex = -1;
			
			float width = ClickGui.getFont().getStringWidth(toRender);
			if (width > this.parent.getWidth() - 8) {
				for (int i = 0; i < toRender.length(); i++) {
					if (cutOffIndex == -1) {
						float stage = ClickGui.getFont().getStringWidth(toRender.substring(0, i));
						if (stage > this.parent.getWidth() - 14) {
							cutOffIndex = i;
							break;
						}
					}
				}
			}
			
			if (cutOffIndex == -1) {
				ClickGui.getFont().drawString(toRender + (this.selected ? "_" : ""), (this.x + 4), (y + (ClickGui.getFont().getHeight(name) / 2) + 0.4F), ClickGui.getPrimaryColor().getRGB());
			} else {
				ClickGui.getFont().drawString(toRender.substring(0, cutOffIndex) + "...", (this.x + 4), (y + (ClickGui.getFont().getHeight(name) / 2) + 0.4F), ClickGui.getPrimaryColor().getRGB());
			}
		}
		return this.height;
	}
	
	@Override
	public void keyTyped(char typedChar, int keyCode) {
		Keyboard.enableRepeatEvents(true);
		if (this.selected && typedChar != -1 && keyCode != -1) {
			if ((keyCode == Keyboard.KEY_DELETE || keyCode == 14) && !this.set.getValue().isEmpty()) {
				this.set.setValue(this.set.getValue().substring(0, this.set.getValue().length() - 1), true);
			} else if (keyCode == Keyboard.KEY_RETURN) {
				this.selected = false;
			} else if (ChatAllowedCharacters.isAllowedCharacter(typedChar)){
				this.set.setValue(this.set.getValue() + typedChar, true);
			}
		}
	}
	
	@Override
	public void mouseClicked(int x, int y, int button) {
		if (button == 0)  {
			if (this.hovered) {
				this.selected = !this.selected;
			} else {
				this.selected = false;
			}
		}
	}
	
	private boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + this.parent.getWidth() && mouseY >= y && mouseY <= y + height;
	}
}
