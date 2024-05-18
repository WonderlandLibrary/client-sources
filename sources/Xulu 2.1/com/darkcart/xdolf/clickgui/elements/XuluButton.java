package com.darkcart.xdolf.clickgui.elements;

import org.lwjgl.input.Mouse;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.clickgui.XuluGuiClick;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.mods.render.rainbowClickGui;
import com.darkcart.xdolf.util.RenderUtils;


public class XuluButton {
	
	private XuluBWindow window;
	private Module mod;
	private int x, y;
	public boolean overButton;
	
	public XuluButton(XuluBWindow window, Module mod, int x, int y) {
		this.window = window;
		this.mod = mod;
		this.x = x;
		this.y = y;
	}
	
	public void draw() { //00a9ff
		RenderUtils.drawBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.3F, 0xFF, mod.isEnabled() ? overButton ? 0xFF44AAFF : 0xFF00a9ff : overButton ? 0xFF888888 : 0xFF33363d);
		RenderUtils.drawBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.3F, 0xFF282a2b, 0x00000000);
		Wrapper.drawCenteredTTFString(mod.getName(), (int)(x + 48 + window.getDragX()), (int)y + window.getDragY(), 0xFFFFFF);
		if(Hacks.findMod(rainbowClickGui.class).isEnabled()){
			RenderUtils.drawBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.5F, Wrapper.getRainbowClass().getRainbow(6000, -15 * 4), mod.isEnabled() ? overButton ? Wrapper.getRainbowClass().getRainbow(6000, -15 * 4) : Wrapper.getRainbowClass().getRainbow(6000, -15 * 4) : overButton ? Wrapper.getRainbowClass().getRainbow(6000, -15 * 4) : Wrapper.getRainbowClass().getRainbow(6000, -15 * 4));
			RenderUtils.drawBorderedRect(x + window.getDragX(), y + window.getDragY(), x + 96 + window.getDragX(), y + 11  + window.getDragY(), 0.5F, Wrapper.getRainbowClass().getRainbow(6000, -15 * 4), Wrapper.getRainbowClass().getRainbow(6000, -15 * 4));
			Wrapper.drawCenteredTTFString(mod.getName(), (int)(x + 48 + window.getDragX()), (int)y + window.getDragY(), 0xFFFFFF);
		}
		
	}
	/**
	 * 0xFFFF0000 = Button color [RED]
	 */
	public void mouseClicked(int x, int y, int button) {
		if(x >= getX() + window.getDragX() && y >= getY() + window.getDragY() && x <= getX() + 96 + window.getDragX() && y <= getY() + 11 + window.getDragY() && window.isOpen()) {
			XuluGuiClick.sendPanelToFront(window);
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
