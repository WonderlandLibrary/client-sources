package com.kilo.ui.inter;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.ui.InteractableParent;
import com.kilo.util.Align;
import com.kilo.util.Util;


public class Button extends Inter {
	
	public String text;
	public int buttonColor, iconColor;
	public float anim;
	public final float animSpeed = 2f;
	public Texture icon;
	public float size;
	
	public Button(InteractableParent p, String t, float x, float y, float w, float h, TrueTypeFont f, int bc, Texture i, float s) {
		this(p, t, x, y, w, h, f, bc, i, s, Align.C, Align.C);
	}
	
	public Button(InteractableParent p, String t, float x, float y, float w, float h, TrueTypeFont f, int bc, Texture i, float s, Align ha, Align va) {
		this(p, t, x, y, w, h, f, bc, Colors.WHITE.c, i, s, ha, va);
	}
	
	public Button(InteractableParent p, String t, float x, float y, float w, float h, TrueTypeFont f, int bc, int ic, Texture i, float s, Align ha, Align va) {
		super(p, Inter.TYPE.button, x, y, w, h, f, Colors.WHITE.c, ha, va);
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
		y = (float)(Math.floor(y));
		
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(Util.blendColor(buttonColor, 0xFF000000, enabled?1f:0.4f), (buttonColor!=0x00000000)?opacity:0f));
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(Colors.BLACK.c, (anim*0.4f)*opacity));

		Draw.startClip(x, y, x+width, y+height);

		Align ha = fontAlignH;
		float iconX = x+width-16-size;
		float textX = x+16;
		
		switch(ha) {
		case C:
			textX = x+(width/2);
			break;
		case R:
			textX = x+width-8;
			iconX = x+8;
			break;
		default:
			break;
		}
		
		if (icon != null && text != null) {
			Draw.rectTexture(iconX, fontAlignV == Align.T?y+16:fontAlignV == Align.C?y+(height/2)-(size/2):y+height-16-size, size, size, icon, Util.reAlpha(Util.blendColor(iconColor, 0xFF000000, enabled || buttonColor == 0x00000000?1f:0.4f), 1f*opacity));
			if (ha == Align.C) {
				ha = Align.L;
				textX = x+16;
			}
		}
		
		if (text != null) {
			Draw.string(font, textX, fontAlignV == Align.T?y+16:fontAlignV == Align.C?y+(height/2):y+height-16, text, Util.reAlpha(Util.blendColor(fontColor, 0xFF000000, enabled || buttonColor == 0x00000000?1f:0.4f), 1f*opacity), ha, fontAlignV);
		} else {
			float gap = Math.min(width, height)/4;
			Draw.loader(x+(width/2), y+(height/2), gap, Util.reAlpha(Util.blendColor(fontColor, 0xFF000000, enabled || buttonColor == 0x00000000?1f:0.4f), 1f*opacity));
		}
		
		Draw.endClip();
		y = prevY;
	}
}
