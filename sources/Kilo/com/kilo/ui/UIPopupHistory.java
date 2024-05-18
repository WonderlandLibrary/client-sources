package com.kilo.ui;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.manager.HistoryManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class UIPopupHistory extends UI {

	private float formOffset;
	
	private float fX, fY, fW, fH;
	
	private List names;
	
	public UIPopupHistory(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = HistoryManager.current.gameProfile.getName();

		formOffset = 0;

		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 350;
		if (names != null) {
			fH = 48+48+Math.max(Fonts.ttfRoundedBold20.getHeight(), ((names.size())*Fonts.ttfRoundedBold20.getHeight()));
		} else {
			fH = 48+48+Fonts.ttfRoundedBold20.getHeight();
			new Thread() {
				@Override
				public void run() {
					try {
						names = Util.getNamesFromUUID(Util.getUUIDFromName(HistoryManager.current.gameProfile.getName()));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		}

		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		if (names != null) {
			if (fH != 48+48+Math.max(Fonts.ttfRoundedBold20.getHeight(), ((names.size())*Fonts.ttfRoundedBold20.getHeight()))) {
				init();
			}
		} else {
			fH = 48+48+Fonts.ttfRoundedBold20.getHeight();
		}
	}
	
	@Override
	public void interact(Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			((UIHistory)parent).changePopup(null);
			break;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
	}
	
	public void mouseRelease(int mx, int my, int b) {
		super.mouseRelease(mx, my, b);
	}
	
	public void mouseScroll(int s) {
		super.mouseScroll(s);
	}
	
	public void keyboardPress(int key) {
		super.keyboardPress(key);
		switch (key) {
		case Keyboard.KEY_ESCAPE:
			((UIHistory)parent).changePopup(null);
			break;
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		super.keyTyped(key, keyChar);
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF202020, 1f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		if (names != null) {
			for(Object s : names) {
				Draw.string(Fonts.ttfRoundedBold20, fX-(fW/2)+24, fY-(fH/2)+48+24+(names.indexOf(s)*Fonts.ttfRoundedBold20.getHeight()), s.toString(), Util.reAlpha(Colors.WHITE.c, opacity));
			}
		} else {
			Draw.loader(fX, fY-(fH/2)+48+((fH-48)/2), 8, Util.reAlpha(Colors.WHITE.c, opacity));
		}
		
		super.render(opacity);
	}
}
