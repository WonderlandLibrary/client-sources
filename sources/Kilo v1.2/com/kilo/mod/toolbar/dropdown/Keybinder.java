package com.kilo.mod.toolbar.dropdown;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class Keybinder extends Interactable {

	public String text, alt;
	public boolean setting = false;
	
	public Keybinder(Window parent, TYPE type, String text, int id, String help, float offsetX, float offsetY, float width, float height) {
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
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(Colors.GREY.c, (fade/5)*hoverFade));
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(Colors.WHITE.c, (fade/5)*activeFade));
		
		if (fade > 0.01f) {
			if (setting) {
				Draw.string(Fonts.ttfRounded14, x+(width/2), y+(height/2), "Press Key", Util.reAlpha(Colors.BLUE.c, fade), Align.C, Align.C);
			} else {
				Draw.string(Fonts.ttfRounded14, x+(height-Fonts.ttfRounded14.getHeight()), y+(height/2), text, Util.reAlpha(Colors.WHITE.c, fade), Align.L, Align.C);
				Draw.string(Fonts.ttfRoundedBold14, x+width-(height-Fonts.ttfRoundedBold14.getHeight()), y+(height/2), alt, Util.reAlpha(Colors.GREY.c, fade), Align.R, Align.C);
			}
		}
	}
}
