package com.kilo.mod.toolbar;

import net.minecraft.util.ResourceLocation;

import org.newdawn.slick.opengl.Texture;

public abstract class Component extends Positionable{

	public Texture texture;
	public int id;
	public boolean hover, enabled, active;
	
	public Component(int id, Texture texture, float x, float y, float width, float height) {
		this.id = id;
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.enabled = true;
	}
	
	public void translate(float x, float y) {
		if (!enabled) { return; }
		
		this.x = x;
		this.y = y;
	}
	
	public boolean mouseOver(int mouseX, int mouseY) {
		return enabled && mouseX > x && mouseX < x+width && mouseY > y && mouseY < y+height;
	}
	
	public abstract void update(int mouseX, int mouseY);
	public abstract void mouseClick(int mouseX, int mouseY, int button);
	public abstract void mouseRelease(int mouseX, int mouseY, int button);
	public abstract void render(float transparency);
}
