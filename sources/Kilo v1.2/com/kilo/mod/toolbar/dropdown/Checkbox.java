package com.kilo.mod.toolbar.dropdown;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.UIHacks;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class Checkbox extends Interactable {

	public String text;
	public boolean checked;
	private float animSize;
	
	public Checkbox(Window parent, TYPE type, String text, int id, String help, float offsetX, float offsetY, float width, float height) {
		super(parent, type, id, help, parent.x, parent.y, width, height, offsetX, offsetY);
		this.text = text;
	}
	
	public void update(int mouseX, int mouseY) {
		super.update(mouseX, mouseY);
		animSize+= ((checked?4f:0)-animSize)/UIHacks.transitionSpeed;
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
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(Colors.GREY.c, (fade/5)*activeFade));
		
		//Draw.rect(x+width-(height/2)-8, y+(height/2)-8, x+width-(height/2)+8, y+(height/2)+8, Util.reAlpha(Colors.GREY.c, fade/5));
		Draw.point(x+width-(height/2), y+(height/2), 16, Util.reAlpha(Colors.GREY.c, fade/5));
		//Draw.circle(x+width-(height/2), y+(height/2), 8, Util.reAlpha(Colors.GREY.c, fade/5));
		if (animSize > 0.1f) {
			//Draw.rect(x+width-(height/2)-animSize, y+(height/2)-animSize, x+width-(height/2)+animSize, y+(height/2)+animSize, Util.reAlpha(Colors.GREY.c, fade/2));
			Draw.point(x+width-(height/2), y+(height/2), animSize*2, Util.reAlpha(Colors.GREY.c, fade/2));
		}
		
		if (fade > 0.01f) {
			Draw.string(Fonts.ttfRounded14, x+(height-Fonts.ttfRounded14.getHeight()), y+(height/2), text, Util.reAlpha(checked?Colors.BLUE.c:Colors.WHITE.c, fade), Align.L, Align.C);
		}
	}
}
