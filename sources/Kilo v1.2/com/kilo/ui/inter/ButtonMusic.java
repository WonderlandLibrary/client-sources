package com.kilo.ui.inter;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.ui.InteractableParent;
import com.kilo.util.Align;
import com.kilo.util.Util;


public class ButtonMusic extends Inter {
	
	public String text;
	public int buttonColor, iconColor;
	public float anim;
	public final float animSpeed = 2f;
	public Texture icon;
	public float size;
	
	public ButtonMusic(InteractableParent p, String t, float x, float y, float w, float h, TrueTypeFont f, int bc, int ic, Texture i, float s) {
		super(p, Inter.TYPE.button, x, y, w, h, f, Colors.WHITE.c, Align.L, Align.C);
		text = t;
		buttonColor = bc;
		iconColor = ic;
		icon = i;
		size = s;
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		anim+= ((hover?1f:0f)-anim)/animSpeed;
	}
	
	public void render(float opacity) {
		float prevY = y;
		
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(Util.blendColor(buttonColor, 0xFF000000, enabled?1f:0.4f), (buttonColor!=0x00000000)?opacity:0f));
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(Colors.BLACK.c, (anim*0.4f)*opacity));

		Draw.startClip(x, y, x+width, y+height);

		float iconX = x+8;
		float textX = x+24;
		
		if (icon != null) {
			Draw.rectTexture(iconX, y+(height/2)-(size/2), size, size, icon, Util.reAlpha(Util.blendColor(iconColor, 0xFF000000, enabled || buttonColor == 0x00000000?1f:0.4f), 1f*opacity));
		}
		
		if (text != null) {
			Draw.string(font, textX, y+(height/2), text, Util.reAlpha(Util.blendColor(fontColor, 0xFF000000, enabled || buttonColor == 0x00000000?1f:0.4f), 1f*opacity), fontAlignH, fontAlignV);
		} else {
			float gap = Math.min(width, height)/4;
			Draw.loader(x+(width/2), y+(height/2), gap, Util.reAlpha(Util.blendColor(fontColor, 0xFF000000, enabled || buttonColor == 0x00000000?1f:0.4f), 1f*opacity));
		}
		
		Draw.endClip();
	}
}
