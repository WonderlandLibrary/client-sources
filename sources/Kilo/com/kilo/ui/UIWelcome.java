package com.kilo.ui;

import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.opengl.Display;

import com.kilo.Kilo;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.Link;
import com.kilo.ui.inter.TextBoxAlt;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.UIUtil;
import com.kilo.util.Util;

public class UIWelcome extends UI {

	public UIWelcome(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Welcome to Kilo fixed by Jawed";
		
		inters.clear();
		inters.add(new Link(this, "Get Started", Display.getWidth()/2, Display.getHeight()/2+(40/2), Fonts.ttfRoundedBold20, Colors.BLUE.c, Align.C, Align.C));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			UIHandler.changeUI(new UIMainMenu(this));
		}
	}
	
	public void render(float opacity) {
		Draw.string(Fonts.ttfRoundedBold50, Display.getWidth()/2, Display.getHeight()/2-(40/2), title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		super.render(opacity);
	}
}
