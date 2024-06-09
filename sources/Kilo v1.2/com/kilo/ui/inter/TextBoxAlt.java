package com.kilo.ui.inter;

import org.newdawn.slick.TrueTypeFont;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.ui.InteractableParent;
import com.kilo.ui.UICreateWorld;
import com.kilo.util.Align;
import com.kilo.util.Util;


public class TextBoxAlt extends TextBox {
	
	private int underColor;
	private float underlineWidth;

	public TextBoxAlt(InteractableParent parent, String placeholder, float x, float y, float width, float height, TrueTypeFont font, int fontColor, Align fontAlignH, Align fontAlignV) {
		this(parent, placeholder, x, y, width, height, font, fontColor, Colors.DARKBLUE.c, 2f, fontAlignH, fontAlignV);
	}
	
	public TextBoxAlt(InteractableParent parent, String placeholder, float x, float y, float width, float height, TrueTypeFont font, int fontColor, int underColor, float underlineWidth, Align fontAlignH, Align fontAlignV) {
		super(parent, placeholder, x, y, width, height, font, fontColor, fontAlignH, fontAlignV);
		this.underColor = underColor;
		this.underlineWidth = underlineWidth;
	}
	
	public void render(float opacity) {
		float cursorBlink = 1f;
		boolean cursor = (active?(timer.isTime(cursorBlink/2f)?true:false):false);

		if (timer.isTime(cursorBlink)) {
			timer.reset();
		}
		
		Draw.rect(x-8, y+height, x+width+8, y+height+underlineWidth, Util.reAlpha(underColor, 1f*opacity));
		
		Draw.startClip(x, y, x+width, y+height);

		float xx = x+scroll+font.getWidth(text.substring(0, cursorPos));

		if (active) {
			Draw.rect(xx-1, y+(height/2)-(font.getHeight(text)/2), xx+1, y+(height/2)+(font.getHeight(text)/2), Util.reAlpha(fontColor, cursor?0.8f:0.2f*opacity));
			float cp = (x+font.getWidth(text.substring(0, cursorPos))+scrollTo);
			if (cp < x) {
				scrollTo+= (x-cp);
				scrollTo-= Math.min(width, scrollTo);
			}
			if (cp > x+width) {
				scrollTo+= (x+width)-cp;
			}
		}
		
		scroll+= (scrollTo-scroll)/scrollSpeed;

		startSelect = Math.min(Math.max(0, startSelect), text.length());
		endSelect = Math.min(Math.max(0, endSelect), text.length());
		
		float startX = x+scroll+font.getWidth(text.substring(0, startSelect));
		float endX = x+scroll+font.getWidth(text.substring(0, endSelect));
		Draw.rect(startX, y+(height/2)-(font.getHeight(text)/2), endX, y+(height/2)+(font.getHeight(text)/2), Util.reAlpha(0xFF55AAFF, 0.5f*opacity));
		
		Draw.string(font, x+scroll, y+(height/2), text.length() != 0 || active?text:placeholder, Util.reAlpha(fontColor, text.length() != 0?((anim/2f)+0.5f)*opacity:0.5f*opacity), fontAlignH, fontAlignV);
		
		Draw.endClip();
	}
}
