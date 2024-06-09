package com.kilo.ui.inter;

import org.newdawn.slick.TrueTypeFont;

import com.kilo.render.Draw;
import com.kilo.ui.InteractableParent;
import com.kilo.util.Align;
import com.kilo.util.Util;


public class Link extends Inter {
	
	public String text;
	public float anim;
	public final float animSpeed = 3f;
	
	public Link(InteractableParent p, String t, float x, float y, TrueTypeFont f, int fc, Align fh, Align fv) {
		super(p, Inter.TYPE.link, x-(fh==Align.C?f.getWidth(t)/2:(fh==Align.R?f.getWidth(t):0)), y-(fv==Align.C?f.getHeight()/2:(fv==Align.B?f.getHeight():0)), 0, 0, f, fc, fh, fv);
		text = t;
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		if (font != null) {
			width = font.getWidth(text);
			height = font.getHeight(text);
		}
		
		anim+= ((hover?1f:0f)-anim)/animSpeed;
	}
	
	public void render(float opacity) {
		Draw.rect(x, y+height+3, x+((width+2)*anim), y+height+5, Util.reAlpha(fontColor, anim*opacity));
		Draw.startClip(x-1, y, x+width+1, y+height);
		
		Draw.string(font, x+(width/2), y+(height/2)+((height*1.5f)*anim), text, Util.reAlpha(fontColor, 1f*opacity), Align.C, Align.C);
		Draw.string(font, x+(width/2), y+(height/2)-(height*1.5f)+((height*1.5f)*anim), text, Util.reAlpha(fontColor, 1f*opacity), Align.C, Align.C);
		
		Draw.endClip();
	}
}
