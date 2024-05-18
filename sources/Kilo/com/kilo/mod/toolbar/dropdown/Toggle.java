package com.kilo.mod.toolbar.dropdown;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class Toggle extends Interactable {

	public String text;
	public boolean toggled;
	
	public Toggle(Window parent, TYPE type, String text, int id, String help, float offsetX, float offsetY, float width, float height) {
		super(parent, type, id, help, parent.x, parent.y, width, height, offsetX, offsetY);
		this.text = text;
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button) {
		if (mouseOver(mouseX, mouseY)) {
			parent.action(this, mouseX, mouseY, button);
		}
	}
	@Override
	public void mouseReleased(int mouseX, int mouseY, int button) {
	}
	
	@Override
	public void render(float fade) {
		if (hover) {
			Draw.rect(x, y, x+width, y+height, Util.reAlpha(Colors.GREY.c, (fade/5)*hoverFade));
		}
		if (active) {
			Draw.rect(x, y, x+width, y+height, Util.reAlpha(Colors.WHITE.c, (fade/5)*activeFade));
		}
		if (fade > 0.01f) {
			Draw.string(Fonts.ttfRounded14, x+(width/2), y+(height/2), text, Util.reAlpha(active?Colors.BLUE.c:Colors.WHITE.c, fade), Align.C, Align.C);
		}
	}
}
