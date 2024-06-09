package lunadevs.luna.gui.clickgui.component.components;

import java.awt.Color;
import java.util.ArrayList;

import lunadevs.luna.gui.clickgui.ClickGui;
import lunadevs.luna.gui.clickgui.component.Component;
import lunadevs.luna.gui.clickgui.component.Frame;
import lunadevs.luna.gui.clickgui.component.components.sub.Checkbox;
import lunadevs.luna.gui.clickgui.component.components.sub.Keybind;
import lunadevs.luna.gui.clickgui.component.components.sub.Slider;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.BooleanOption;
import lunadevs.luna.option.NumberOption;
import lunadevs.luna.option.Option;
import lunadevs.luna.option.OptionManager;
import lunadevs.luna.utils.Values.BooleanValue;
import lunadevs.luna.utils.Values.RestrictedValue;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Button extends Component {

	public Module mod;
	public Frame parent;
	public int offset;
	private boolean isHovered;
	private ArrayList<Component> subcomponents;
	public boolean open;
	private int height;
	
	public Button(Module mod, Frame parent, int offset) {
		this.mod = mod;
		this.parent = parent;
		this.offset = offset;
		this.subcomponents = new ArrayList();
		this.open = false;
		height = 12;
		int opY = offset + 12;
     if(!mod.getOptions().isEmpty()) {
         for (Option option : mod.getOptions()) {

             if (option instanceof NumberOption) {
                 Slider slider = new Slider((NumberOption) option, this, opY);
                 this.subcomponents.add(slider);
                 opY += 12;
             }

             if (option instanceof BooleanOption) {
                 Checkbox check = new Checkbox((BooleanOption) option, this, opY);
                 this.subcomponents.add(check);
                 opY += 12;
             }
         }
     }
		this.subcomponents.add(new Keybind(this, opY));
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
		Gui.drawRect(parent.getX(), this.parent.getY() + this.offset, parent.getX() + parent.getWidth(), this.parent.getY() + 12 + this.offset, this.isHovered ? (this.mod.isEnabled() ? new Color(0xFF222222).darker().getRGB() : 0xFF222222) : (this.mod.isEnabled() ? new Color(14,14,14).getRGB() : 0xFF111111));
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		Minecraft.getMinecraft().fontRendererObj.drawString(this.mod.getName(), (parent.getX() + 2) * 2, (parent.getY() + offset + 2) * 2 + 4, this.mod.isEnabled() ? 0x999999 : -1);
		if(this.subcomponents.size() > 2)
		Minecraft.getMinecraft().fontRendererObj.drawString(this.open ? "-" : "+", (parent.getX() + parent.getWidth() - 10) * 2, (parent.getY() + offset + 2) * 2 + 4, -1);
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.subcomponents.isEmpty()) {
				for(Component comp : this.subcomponents) {
					comp.renderComponent();
				}
				//Gui.drawRect(parent.getX() + 2, parent.getY() + this.offset + 12, parent.getX() + 3, parent.getY() + this.offset + ((this.subcomponents.size() + 1) * 12), ClickGui.color);
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
	
	private boolean isMouseOnButton(int x, int y) {
		if(x > parent.getX() && x < parent.getX() + parent.getWidth() && y > this.parent.getY() + this.offset && y < this.parent.getY() + 12 + this.offset) {
			return true;
		}
		return false;
	}
}
