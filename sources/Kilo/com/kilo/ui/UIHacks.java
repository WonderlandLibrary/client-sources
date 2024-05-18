package com.kilo.ui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.mod.toolbar.Button;
import com.kilo.mod.toolbar.Component;
import com.kilo.mod.toolbar.Separator;
import com.kilo.mod.toolbar.dropdown.WindowModule;
import com.kilo.mod.toolbar.dropdown.WindowOptions;
import com.kilo.mod.toolbar.dropdown.WindowSubOptions;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Tooltip;
import com.kilo.ui.inter.Inter;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class UIHacks extends UI {
	
	private final Minecraft mc;
	private static final float transitionSpeedBase = 2f;
	
	public List<Component> toolbarComponents = new ArrayList<Component>();
	
	public WindowModule window;
	public WindowOptions options;
	public WindowSubOptions subOptions;
	
	public Tooltip tooltip;
	
	public static float transitionSpeed = transitionSpeedBase;
	
	private float transparency;
	
	public UIHacks() {
		super(null);
		this.mc = Minecraft.getMinecraft();
		int i = 0;
		float x = (Display.getWidth()/2)-((toolbarComponents.size())*32);
		float y = 0;
		
		toolbarComponents.add(new Separator(-1, Resources.iconSeparator, x, y, 64, 64));
		x+= 64;
		for(i = 0; i < Resources.iconHacks.length; i++) {
			toolbarComponents.add(new Button(i, Resources.iconHacks[i], x, y, 64, 64));
			x+= 64;
		}
		toolbarComponents.add(new Separator(-1, Resources.iconSeparator, x, y, 64, 64));
		
		this.window = new WindowModule(null);
		this.options = new WindowOptions(window);
		this.subOptions = new WindowSubOptions(options);
		this.tooltip = new Tooltip();
	}
	
	public void update(int mouseX, int mouseY) {
		this.transitionSpeed = transitionSpeedBase;
		
		int i = 0;
		float x = (Display.getWidth()/2)-((toolbarComponents.size())*32);
		float y = 0;
		
		window.active = false;
		
		for(Component component : toolbarComponents) {
			component.update(mouseX, mouseY);
			component.translate(x, y);
			x+= 64;
		}
		
		tooltip.update(mouseX, mouseY);
		
		window.update(mouseX, mouseY);
		options.update(mouseX, mouseY);
		subOptions.update(mouseX, mouseY);
	}
	
	public void mouseClick(int mouseX, int mouseY, int button) {
		for(Component component : toolbarComponents) {
			component.mouseClick(mouseX, mouseY, button);
		}
		window.mouseClick(mouseX, mouseY, button);
		options.mouseClick(mouseX, mouseY, button);
		subOptions.mouseClick(mouseX, mouseY, button);
	}
	public void mouseRelease(int mouseX, int mouseY, int button) {
		for(Component component : toolbarComponents) {
			component.mouseRelease(mouseX, mouseY, button);
		}
		window.mouseRelease(mouseX, mouseY, button);
		options.mouseRelease(mouseX, mouseY, button);
		subOptions.mouseRelease(mouseX, mouseY, button);
	}
	
	public void keyboardPress(int key) {
		window.keyboardPress(key);
		options.keyboardPress(key);
		subOptions.keyboardPress(key);
		if (key == Keyboard.KEY_ESCAPE) {
			options.active = false;
			subOptions.active = false;
			mc.displayGuiScreen((GuiScreen)null);
		}
	}
	public void keyboardRelease(int key) {
		window.keyboardRelease(key);
		options.keyboardRelease(key);
		subOptions.keyboardRelease(key);
	}
	
	public void keyTyped(int key, char keyChar) {
		options.keyTyped(key, keyChar);
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.WHITE.c, opacity/4f));
		
		Draw.rect(0, 0, Display.getWidth(), 64, Util.reAlpha(Colors.BLACK.c, opacity*0.6f));
		
		window.render(opacity);
		options.render(opacity);
		subOptions.render(opacity);
		
		for(Component component : toolbarComponents) {
			component.render(opacity);
		}
		
		tooltip.render(opacity);
	}

	@Override
	public void init() {
	}

	@Override
	public void interact(Inter i) {
	}
}
