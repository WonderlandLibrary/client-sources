package com.kilo.ui.inter;

import com.kilo.music.MusicHandler;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.InteractableParent;
import com.kilo.util.Align;
import com.kilo.util.Util;


public class VolumeSlider extends Inter {
	
	public float val;
	
	public VolumeSlider(InteractableParent p, float x, float y, float w, float h) {
		super(p, Inter.TYPE.slider, x, y, w, h, Fonts.ttfRounded10, Colors.WHITE.c, Align.L, Align.T);
		val = (MusicHandler.vol/100f)*(h);
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		if (active) {
			val = (y+height)-my;
		}
		val = Math.min(Math.max(0, val), height);
		
		float vol = (((1f/(height))*val)*100);
		MusicHandler.setVolume(vol);
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
		if (mouseOver(mx, my)) {
			parent.interact(this);
			active = true;
		} else {
			active = false;
			parent.inters.remove(this);
		}
	}
	
	public void mouseRelease(int mx, int my, int b) {
		active = false;
	}
	
	public void render(float opacity) {
		Draw.rect(x-4, y-4-(width/2), x+width+4, y+height+4+(width/2), Util.reAlpha(0xFF1A1A1A, 1f*opacity));
		Draw.rect(x, y+height+(width/2), x+width, y+height+(width/2)-val, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		Draw.rect(x, y+height-(width/2)-val, x+width, y+height+(width/2)-val, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
	}
}
