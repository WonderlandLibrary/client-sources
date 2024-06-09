package com.kilo.mod.toolbar.dropdown;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class Slider extends Interactable {

	public String text, alt;
	public float value, animValue;
	public boolean setting;
	
	public Slider(Window parent, TYPE type, String text, int id, String help, float offsetX, float offsetY, float width, float height) {
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
		this.setting = false;
	}
	
	@Override
	public void render(float fade) {
		this.active = setting;
		
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(Colors.GREY.c, (fade/5)*hoverFade));
		
		float padding = 1;
		
		animValue+= (value-animValue)/2f;
		
		Draw.rect(x, y+padding, x+animValue, y+height-padding, Util.reAlpha(Colors.GREY.c, fade/3));
		
		if (fade > 0.01f) {
			Draw.string(Fonts.ttfRounded14, x+(height-Fonts.ttfRounded14.getHeight()), y+(height/2), text, Util.reAlpha(Colors.WHITE.c, fade), Align.L, Align.C);
			Draw.string(Fonts.ttfRoundedBold14, x+width-(height-Fonts.ttfRoundedBold14.getHeight()), y+(height/2), alt, Util.reAlpha(Colors.GREY.c, fade), Align.R, Align.C);
		}
	}
}
