package com.kilo.ui;

import java.util.UUID;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.manager.ChatSpamManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.slot.ChatSpamSlot;
import com.kilo.ui.inter.slotlist.slot.HistorySlot;
import com.kilo.ui.inter.slotlist.slot.Slot;
import com.kilo.users.UserHandler;
import com.kilo.util.Align;
import com.kilo.util.Config;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class UIHistory extends UI {

	private UI popup, popupTo;
	private boolean popupFade;
	private float fX, fY, fW, fH, popupOpacity;

	public SlotList sl;
	
	public UIHistory(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Username History";
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 400;
		fH = 48+48+(48*Math.min(8, UserHandler.players.size()));

		sl = new SlotList(7f);
		
		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
	}
	
	public void update(int mx, int my) {
		if (popup == null) {
			super.update(mx, my);
		}
		
		if (fH != 48+48+(48*Math.min(8, UserHandler.players.size()))) {
			init();
		}

		sl.x = fX-(fW/2)+24;
		sl.y = fY-(fH/2)+72-sl.oy;
		sl.w = fW-48;
		sl.h = fH-96;
		
		if (sl.slots.size() != UserHandler.players.size()) {
			sl.slots.clear();
			int i = 0;
			for(UUID f : UserHandler.players.keySet()) {
				sl.slots.add(new HistorySlot(sl, UserHandler.players.get(f), sl.x, sl.y, sl.w, 48, 0, i*48));
				i++;
			}
		}

		sl.update(mx, my);
		
		if (popup != null) {
			popup.update(mx, my);
		}
		popupOpacity+= popupFade?-0.2f:0.2f;//((uiFadeIn?1:0)-uiFade)/4f;
		popupOpacity = Math.min(Math.max(0f, popupOpacity), 1f);
		if (popupFade) {
			if (popupOpacity < 0.05f) {
				popup = popupTo;
				popupFade = false;
				if (popup != null) {
					popup.init();
				}
			}
		}
	}
	
	@Override
	public void interact(final Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			UIHandler.changeUI(parent);
			break;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (popup == null) {
			super.mouseClick(mx, my, b);
			sl.mouseClick(mx, my, b);
		} else {
			popup.mouseClick(mx, my, b);
		}
	}
	
	public void mouseRelease(int mx, int my, int b) {
		if (popup == null) {
			super.mouseRelease(mx, my, b);
			sl.mouseRelease(mx, my, b);
		} else {
			popup.mouseRelease(mx, my, b);
		}
	}
	
	public void mouseScroll(int s) {
		if (popup == null) {
			super.mouseScroll(s);
			sl.mouseScroll(s);
		} else {
			popup.mouseScroll(s);
		}
	}
	
	public void keyboardPress(int key) {
		if (popup == null) {
			super.keyboardPress(key);
			switch (key) {
			case Keyboard.KEY_ESCAPE:
				mc.displayGuiScreen(null);
				break;
			}
		} else {
			popup.keyboardPress(key);
		}
	}
	
	public void keyTyped(int key, char keyChar) {
		if (popup == null) {
			super.keyTyped(key, keyChar);
		} else {
			popup.keyTyped(key, keyChar);
		}
	}
	
	public void changePopup(UI u) {
		popupTo = u;
		popupFade = true;
	}
	
	public void render(float opacity) {
		Draw.rect(0, 0, Display.getWidth(), Display.getHeight(), Util.reAlpha(Colors.BLACK.c, 0.7f*opacity));
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF202020, 1f*opacity));

		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		Draw.startClip(fX-(fW/2)+24, fY-(fH/2)+72, fX+(fW/2)-24+sl.sbw, fY+(fH/2)-24);
		sl.render(opacity);
		Draw.endClip();

		super.render(opacity);
		
		if (popup != null) {
			popup.render(opacity*(Math.max(popupOpacity, 0.05f)));
		}
	}
}
