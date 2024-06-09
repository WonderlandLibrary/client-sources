package com.kilo.ui;

import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Inter;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.Timer;
import com.kilo.util.UIUtil;
import com.kilo.util.Util;

public class UIBanned extends UI {

	public UIBanned(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "This Account Has Been Banned";
	}
	
	@Override
	public void interact(Inter i) {
	}
	
	public void render(float opacity) {
		String name = Kilo.kilo().client.kiloName;
		String email = Kilo.kilo().client.email;
		
		Draw.string(Fonts.ttfRoundedBold40, Display.getWidth()/2, Display.getHeight()/2-20, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.B);
		
		Draw.string(Fonts.ttfRoundedBold20, Display.getWidth()/2-(Fonts.ttfRoundedBold20.getWidth("In-Game Name: "+name)/2), Display.getHeight()/2+20, "In-Game Name:", Util.reAlpha(Colors.BLUE.c, 1f*opacity));
		Draw.string(Fonts.ttfRoundedBold20, Display.getWidth()/2-(Fonts.ttfRoundedBold20.getWidth("In-Game Name: "+name)/2)+Fonts.ttfRoundedBold20.getWidth("In-Game Name:  "), Display.getHeight()/2+20, name, Util.reAlpha(Colors.GREY.c, 1f*opacity));

		Draw.string(Fonts.ttfRoundedBold20, Display.getWidth()/2-(Fonts.ttfRoundedBold20.getWidth("Email: "+email)/2), Display.getHeight()/2+50, "Email:", Util.reAlpha(Colors.BLUE.c, 1f*opacity));
		Draw.string(Fonts.ttfRoundedBold20, Display.getWidth()/2-(Fonts.ttfRoundedBold20.getWidth("Email: "+email)/2)+Fonts.ttfRoundedBold20.getWidth("Email:  "), Display.getHeight()/2+50, email, Util.reAlpha(Colors.GREY.c, 1f*opacity));
		super.render(opacity);
	}
}
