package com.kilo.manager;

import jaco.mp3.player.MP3Player;

import org.lwjgl.opengl.Display;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.render.TextureImage;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class Notification {

	public TextureImage image;
	public String message;
	public float x, y;
	public Timer timer = new Timer();
	private final float time = 5f, width = 400f, height = 48;
	private boolean multi;
	
	public Notification(String icon, String message, boolean multi) {
		timer.reset();
		
		this.message = message;
		
		this.x = (Display.getWidth()-width)/2;
		this.y = -height;
		
		if (icon != null) {
			this.image = Resources.downloadTexture(icon);
		}
		
		this.multi = multi;
	}
	
	public void update() {
		if (timer.isTime(time)) {
			y+= ((-height*2)-y)/5f;
		} else {
			if (y < -height/2) {
				timer.reset();
			}
			y+= (8-y)/5f;
		}
		
		if (y < -height) {
			NotificationManager.list.remove(this);
		}
	}
	
	public void render(float opacity) {
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF1A1A1A, 1f*opacity));
		
		if (image != null && image.getTexture() != null) {
			Draw.rectTexture(x+4, y+4, 40, 40, image.getTexture(), opacity);
		}
		
		if (message != null) {
			if (!multi) {
				Draw.string(Fonts.ttfRoundedBold14, x+64, y+24, message, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.L, Align.C);
			} else {
				Draw.string(Fonts.ttfRoundedBold14, x+(width/2), y+24, message, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
			}
		}
	}
	
}
