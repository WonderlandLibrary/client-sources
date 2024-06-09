package com.kilo.ui.inter;

import org.newdawn.slick.opengl.Texture;

import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.InteractableParent;
import com.kilo.ui.UI;
import com.kilo.util.Align;
import com.kilo.util.Util;


public class IconButton extends Inter {
	
	public int buttonColor;
	public float anim;
	public final float animSpeed = 2f;
	public Texture icon;
	
	public IconButton(InteractableParent p, float x, float y, float w, float h, int bc, Texture i) {
		super(p, Inter.TYPE.button, x, y, w, h, Fonts.ttfStandard20, Colors.WHITE.c, Align.C, Align.C);
		buttonColor = bc;
		icon = i;
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);

		anim+= ((hover?1f:0f)-anim)/animSpeed;
	}
	
	public void render(float opacity) {
		Draw.rectTexture(x, y+(anim*(height/8f)), width, height, icon, Util.reAlpha(buttonColor==0x00000000?Colors.WHITE.c:buttonColor, (enabled?1f:0.5f)*((anim/4)+0.75f)*opacity));
	}
}
