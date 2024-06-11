package Hydro.ClickGui.clickgui.component.components;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import Hydro.Client;
import Hydro.ClickGui.clickgui.ClickGui;
import Hydro.ClickGui.clickgui.component.Component;
import Hydro.ClickGui.clickgui.component.Frame;
import Hydro.ClickGui.clickgui.component.components.sub.Checkbox;
import Hydro.ClickGui.clickgui.component.components.sub.Keybind;
import Hydro.ClickGui.clickgui.component.components.sub.ModeButton;
import Hydro.ClickGui.clickgui.component.components.sub.Slider;
import Hydro.ClickGui.clickgui.component.components.sub.VisibleButton;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Module;
import Hydro.util.font.FontUtil;
import net.minecraft.client.gui.Gui;

public class Button extends Component {

	public Module mod;
	public Frame parent;
	public int offset;
	private boolean isHovered;
	private ArrayList<Component> subcomponents;
	public boolean open;
	private int height;
	
	public Button(Module mod2, Frame parent, int offset) {
		this.mod = mod2;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList<Component>();
		this.open = false;
		height = 12;
		int opY = offset + 12;
		if(Client.settingsManager.getSettingsByMod(mod2) != null) {
			for(Setting s : Client.settingsManager.getSettingsByMod(mod2)){
				if(s.isCombo()){
					this.subcomponents.add(new ModeButton(s, this, mod2, opY));
					opY += 12;
				}
				if(s.isSlider()){
					this.subcomponents.add(new Slider(s, this, opY));
					opY += 12;
				}
				if(s.isCheck()){
					this.subcomponents.add(new Checkbox(s, this, opY));
					opY += 12;
				}
			}
		}
		this.subcomponents.add(new Keybind(this, opY));
		this.subcomponents.add(new VisibleButton(this, mod2, opY));
	}
	
	@Override
	public void setOff(int newOff) {
		offset = newOff;
		int opY = offset + 12;
		for(Component comp : this.subcomponents) {
			comp.setOff(opY);
			opY += 12;
		}
	}
	
	@Override
	public void renderComponent() {
		Gui.drawRect(-100, -100, 1, 1, -1); //Fixes rendering issues

		Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isEnabled() ? new Color(0xFF222222).darker().getRGB() : 0xFF222222) : (this.mod.isEnabled() ? new Color(14,14,14).getRGB() : 0xFF111111));
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		Gui.drawRect(-100, -100, 1, 1, -1); //Fixes rendering issues
		Client.instance.fontManager.getFont("regular 38").drawString(this.mod.getName(), (parent.getX() + 2) * 2, (parent.getY() + offset + 2) * 2 - 6, this.mod.isEnabled() ? -1 : Color.gray.darker().brighter().brighter().getRGB());
		if(this.subcomponents.size() > 2) {
			Gui.drawRect(-100, -100, 1, 1, -1); //Fixes rendering issues

			Client.instance.fontManager.getFont("regular 40").drawString(this.open ? "-" : "+", (parent.getX() + parent.getWidth() - 10) * 2, (parent.getY() + offset + 2) * 2 - 10, -1);
		}
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.subcomponents.isEmpty()) {
				for(Component comp : this.subcomponents) {
					comp.renderComponent();
				}
				Gui.drawRect(parent.getX() + 2, parent.getY() + this.offset + 12, parent.getX() + 3, parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12), ClickGui.color);
			}
		}
	}
	
	@Override
	public int getHeight() {
		if(this.open) {
			return (12 * (this.subcomponents.size() + 1));
		}
		return 12;
	}
	
	@Override
	public void updateComponent(int mouseX, int mouseY) {
		this.isHovered = isMouseOnButton(mouseX, mouseY);
		if(!this.subcomponents.isEmpty()) {
			for(Component comp : this.subcomponents) {
				comp.updateComponent(mouseX, mouseY);
			}
		}

		if(this.isHovered){
			Gui.drawRect(mouseX, mouseY - 10, mouseX + Client.instance.fontManager.getFont("regular 20").getWidth(this.mod.desc), mouseY , Color.gray.darker().darker().getRGB());
			Gui.drawRect(-100, -100, 1, 1, -1); //Fixes rendering issues
			Client.instance.fontManager.getFont("regular 20").drawString(this.mod.desc, mouseX, mouseY - 14, -1);
		}
	}
	
	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if(isMouseOnButton(mouseX, mouseY) && button == 0) {
			this.mod.toggle();
		}
		if(isMouseOnButton(mouseX, mouseY) && button == 1) {
			this.open = !this.open;
			this.parent.refresh();
		}
		for(Component comp : this.subcomponents) {
			comp.mouseClicked(mouseX, mouseY, button);
		}
	}
	
	@Override
	public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
		for(Component comp : this.subcomponents) {
			comp.mouseReleased(mouseX, mouseY, mouseButton);
		}
	}
	
	@Override
	public void keyTyped(char typedChar, int key) {
		for(Component comp : this.subcomponents) {
			comp.keyTyped(typedChar, key);
		}
	}
	
	public boolean isMouseOnButton(int x, int y) {
		if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
}
