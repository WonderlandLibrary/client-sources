package com.kilo.ui.inter;

import org.newdawn.slick.TrueTypeFont;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.ui.InteractableParent;
import com.kilo.util.Align;
import com.kilo.util.Util;


public class CheckBox extends Inter {
	
	public String text;
	public float anim;
	public final float animSpeed = 2f;
	
	public CheckBox(InteractableParent p, String t, float x, float y, TrueTypeFont f, int fc) {
		super(p, Inter.TYPE.checkbox, x, y, 22, 22, f, fc, Align.L, Align.C);
		text = t;
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		anim+= ((hover?1f:0f)-anim)/animSpeed;
	}

	public void mouseClick(int mx, int my, int b) {
		if (mouseOver(mx, my)) {
			parent.interact(this);
		}
	}
	
	public void render(float opacity) {
		float ext = 2*anim;
		Draw.rect(x-ext, y-ext, x+width+ext, y+height+ext, Util.reAlpha(Colors.GREEN.c, anim*opacity));
		
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(Colors.WHITE.c, opacity));

		if (active) {
			float[] pointX = new float[] {5,7,-2,-7,-5,-2};
			float[] pointY = new float[] {-5,-3,6,1,-1,2};
			Draw.polygon(x+(width/2), y+(height/2), pointX, pointY, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		}

		Draw.string(font, x+width+16, y+(height/2), text, Util.reAlpha(fontColor, 1f*opacity), fontAlignH, fontAlignV);
	}
}
