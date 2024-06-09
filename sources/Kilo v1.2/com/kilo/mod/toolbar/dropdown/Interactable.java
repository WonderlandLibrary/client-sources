package com.kilo.mod.toolbar.dropdown;

import com.kilo.mod.toolbar.Positionable;
import com.kilo.ui.UIHacks;
import com.kilo.ui.UIHandler;

public abstract class Interactable extends Positionable {

	public enum TYPE {
		BUTTON,
		CHECKBOX,
		SLIDER,
		KEYBINDER,
		TEXTBOX,
		SETTINGS;
	}
	
	public Window parent;
	public int id;
	public String help;
	public boolean enabled, active, hover;
	public TYPE type;
	public float hoverFade, activeFade;
	
	public Interactable(Window parent, TYPE type, int id, String help, float x, float y, float width, float height, float offsetX, float offsetY) {
		this.parent = parent;
		this.type = type;
		this.id = id;
		this.help = help;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.enabled = true;
	}
	
	public void update(int mouseX, int mouseY) {
		if (parent.active && parent.enabled && parent.boxFade > 0.1f) {
			this.hover = mouseOver(mouseX, mouseY);
	
			if (hover) {
				((UIHacks)UIHandler.currentUI).tooltip.text = help;
				this.hoverFade+= (1-hoverFade)/UIHacks.transitionSpeed;
			} else {
				this.hoverFade+= (0-hoverFade)/UIHacks.transitionSpeed;
			}
			this.activeFade+= ((active?1:0)-activeFade)/UIHacks.transitionSpeed;
		}
	}
	
	public void translate(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean mouseOver(int mouseX, int mouseY) {
		return enabled && mouseX > x && mouseX < x+width && mouseY > y && mouseY < y+height;
	}
	public abstract void mouseClicked(int mouseX, int mouseY, int button);
	public abstract void mouseReleased(int mouseX, int mouseY, int button);
	public abstract void render(float fade);
}
