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

public class UIVerified extends UI {

	private Timer timer = new Timer();
	
	public UIVerified(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Welcome ";
		
		inters.clear();
		if (timer != null) {
			timer.reset();
		} else {
			timer = new Timer();
		}
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		if (timer.isTime(3f)) {
			UIHandler.changeUI(new UIMainMenu(null));
		}
	}
	
	@Override
	public void interact(Inter i) {
	}
	
	public void render(float opacity) {
		String name = Kilo.kilo().client.minecraftName;
		Draw.string(Fonts.ttfRoundedBold40, Display.getWidth()/2-(Fonts.ttfRoundedBold40.getWidth(title+name)/2), Display.getHeight()/2-(Fonts.ttfRoundedBold40.getHeight()/2), title, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
		Draw.string(Fonts.ttfRoundedBold40, Display.getWidth()/2-(Fonts.ttfRoundedBold40.getWidth(title+name)/2)+Fonts.ttfRoundedBold40.getWidth(title), Display.getHeight()/2-(Fonts.ttfRoundedBold40.getHeight()/2), name, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		super.render(opacity);
	}
}
