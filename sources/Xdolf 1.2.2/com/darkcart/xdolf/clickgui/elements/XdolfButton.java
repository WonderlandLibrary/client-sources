package com.darkcart.xdolf.clickgui.elements;

import org.lwjgl.input.Mouse;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.clickgui.XdolfGuiClick;
import com.darkcart.xdolf.util.RenderUtils;


public class XdolfButton {
	
	private XdolfWindow window;
	private Module mod;
	private int x, y;
	public boolean overButton;
	
	public XdolfButton(XdolfWindow window, Module mod, int x, int y) {
		this.window = window;
		this.mod = mod;
		this.x = x;
		this.y = y;
	}
	
	public void draw() {
		RenderUtils.drawBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.5F, 0xFF000000, mod.isEnabled() ? overButton ? 0xFF44AAFF : 0xFFFF0000 : overButton ? 0xFF888888 : 0xFF33363d);
		RenderUtils.drawBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.5F, 0xFF000000, 0x00000000);
		
		Wrapper.drawCenteredTTFString(mod.getName(), (int)(x + 48 + window.getDragX()), (int)y + window.getDragY(), 0xFFFFFF);
	}
	
	public void mouseClicked(int x, int y, int button) {
		if(x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && window.isOpen()) {
			XdolfGuiClick.sendPanelToFront(window);
			mod.toggle();
		}
	}
	
	public Module getModule() {
		return mod;
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
