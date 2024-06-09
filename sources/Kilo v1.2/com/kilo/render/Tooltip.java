package com.kilo.render;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.GL11;

import com.kilo.mod.toolbar.Positionable;
import com.kilo.ui.UIHacks;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class Tooltip extends Positionable{

	public String text = "";
	private List<String> lines = new CopyOnWriteArrayList<String>();
	public boolean enabled;
	private float boxFade;
	private float padding = 4f;
	
	public void update(int mouseX, int mouseY) {
		this.x = mouseX+24;
		this.y = mouseY-24;
		
		this.text = "";
	}

	public void render(float transparency) {
		this.enabled = text.length() > 0;
		
		lines.clear();
		
		try {
			int i = 0;
			int j = 0;
			for(i = 0; i < text.length(); i++) {
				
				if (text.charAt(i) == "|".charAt(0)) {
					lines.add(text.substring(j, i));
					i++;
					j = i;
					continue;
				}
			}
			lines.add(text.substring(j, i));
		} catch (Exception ex) {
		}
		
		if (lines.isEmpty()) {
			lines.add(text);
		}
		
		float ww = 0;
		for(String s : lines) {
			if (Fonts.ttfRoundedBold12.getWidth(s) > ww) {
				ww = Fonts.ttfRoundedBold12.getWidth(s)+padding;
			}
		}
		
		this.boxFade+= ((enabled?0.8:0)-boxFade)/UIHacks.transitionSpeed;
		this.width+= ((enabled?ww:0)-width);
		this.height+= (Fonts.ttfRoundedBold12.getHeight()*lines.size()-height)/UIHacks.transitionSpeed;
		
		GlStateManager.color(0, 0, 0, 0);
		GL11.glColor4f(0, 0, 0, 0);
		
		Draw.rect(x-padding, y-padding, x+width+padding, y+height+padding, Util.reAlpha(Colors.BLACK.c, boxFade*transparency));

		Draw.startClip(x-padding, y, x+width+padding, y+height+padding);
		
		for(int i = 0; i < lines.size(); i++) {
			int color = Util.reAlpha(Colors.WHITE.c, boxFade*transparency);
			if (i == lines.size()-1 && i != 0) {
				color = Util.reAlpha(Colors.GREY.c, boxFade*transparency);
			}
			Draw.string(Fonts.ttfRoundedBold12, x, y+(Fonts.ttfRoundedBold12.getHeight()*i), lines.get(i), color, Align.L, Align.T);
		}
		
		Draw.endClip();
	}
	
}
