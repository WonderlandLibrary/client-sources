package com.thunderware.ui.ClickGUI.dropDown.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.awt.*;

import com.thunderware.module.ModuleBase;
import com.thunderware.module.visuals.Hud;
import com.thunderware.settings.Setting;
import com.thunderware.settings.settings.BooleanSetting;
import com.thunderware.settings.settings.ColorSetting;
import com.thunderware.settings.settings.ModeSetting;
import com.thunderware.settings.settings.NumberSetting;
import com.thunderware.ui.ClickGUI.dropDown.ClickGui;
import com.thunderware.ui.ClickGUI.dropDown.impl.set.Checkbox;
import com.thunderware.ui.ClickGUI.dropDown.impl.set.ColorPicker;
import com.thunderware.ui.ClickGUI.dropDown.impl.set.Mode;
import com.thunderware.ui.ClickGUI.dropDown.impl.set.SetComp;
import com.thunderware.ui.ClickGUI.dropDown.impl.set.Slider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Button {
	public Panel panel;
	public ModuleBase mod;
	
	public ArrayList<SetComp> settings = new ArrayList<>();
	
	public double y;
	public boolean extended = false;
	public double height = 15;
	public double setHeight = 0;
	private double sussy = 0;
	
	public Button(double y,ModuleBase mod, Panel panel) {
		this.y = y;
		this.panel = panel;
		this.mod = mod;
		for (Setting s : mod.getSettings()) {
			if (s instanceof NumberSetting) {
				this.settings.add(new Slider((NumberSetting) s, this));
			}
			if (s instanceof BooleanSetting) {
				this.settings.add(new Checkbox((BooleanSetting) s, this));
			}
			if (s instanceof ModeSetting) {
				this.settings.add(new Mode((ModeSetting) s, this));
			}
			if (s instanceof ColorSetting) {
				this.settings.add(new ColorPicker((ColorSetting) s, this));
			}
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks, double plusplus) {
		Minecraft mc = Minecraft.getMinecraft();
		
		Gui.drawRect(panel.x + 1,panel.y + y + plusplus, panel.x + panel.width - 1, panel.y + y + height + plusplus, mod.isToggled() ? !isHovered(mouseX, mouseY) ? Hud.getColor(0) : new Color(Hud.getColor(0)).darker().getRGB() : isHovered(mouseX, mouseY) ? 
				ClickGui.getSecondaryColor().getRGB() : ClickGui.getThirdColor().getRGB());
		mc.customFont.drawStringWithShadow(mod.getName(), panel.x + 5, panel.y + y + ((height - mc.customFont.getHeight()) / 2) + plusplus, -1);

		setHeight = 0;
		sussy = plusplus;
		if(extended) {
			int count = 0;
			for(SetComp sc : settings) {
				setHeight += sc.drawScreen(mouseX, mouseY, panel.x, panel.y +  y + height + plusplus + (count * sc.getHeight()));
				count++;
			}
		}
	}

	public void keyTyped(char typedChat, int keyCode) {
		
	}

	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(isHovered(mouseX,mouseY)) {
			switch(mouseButton) {
				case 0:
					mod.toggle();
					break;
				case 1:
					extended = !extended;
					break;
			}
		}
		for(SetComp sc : settings) {
			sc.mouseClicked(mouseX, mouseY, mouseButton);
		}
	}
	
	public void mouseReleased(int mouseX, int mouseY, int state) {
		for(SetComp sc : settings) {
			sc.mouseReleased(mouseX, mouseY, state);
		}
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return (mouseX > panel.x && mouseX < panel.x + panel.width)
				&& (mouseY > panel.y + y + sussy && mouseY < panel.y + y + height + sussy);
	}
	
	public double getWidth() {
		return panel.width;
	}
}
