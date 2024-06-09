package com.kilo.ui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.manager.ChatSpamManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.slot.ChatSpamSlot;
import com.kilo.ui.inter.slotlist.slot.Slot;
import com.kilo.util.Align;
import com.kilo.util.Config;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class UIChatSpam extends UI {

	private UI popup, popupTo;
	private boolean popupFade;
	private float fX, fY, fW, fH, popupOpacity;

	public SlotList sl;
	
	public UIChatSpam(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Chat Spam";
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 600;
		fH = 48+48+48+(48*Math.min(8, ChatSpamManager.getSize()));

		sl = new SlotList(7f);
		
		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
		inters.add(new Button(this, "Add Message", fX-72-8-144, fY+(fH/2)-40, 144, 32, Fonts.ttfRoundedBold14, Colors.GREEN.c, null, 0));
		inters.add(new Button(this, "Edit Message", fX-72, fY+(fH/2)-40, 144, 32, Fonts.ttfRoundedBold14, Colors.GREY.c, null, 0));
		inters.add(new Button(this, "Delete Message", fX+72+8, fY+(fH/2)-40, 144, 32, Fonts.ttfRoundedBold14, Colors.RED.c, null, 0));
	}
	
	public void update(int mx, int my) {
		if (popup == null) {
			super.update(mx, my);
		}
		
		if (fH != 48+48+48+(48*Math.min(8, ChatSpamManager.getSize()))) {
			init();
		}

		String active = null;
		for(Slot s : sl.slots) {
			if (s.active) {
				active = ChatSpamManager.getMessage(((ChatSpamSlot)s).index);
				break;
			}
		}
		
		((Button)inters.get(2)).enabled = active != null;
		((Button)inters.get(3)).enabled = active != null;
		
		sl.x = fX-(fW/2)+24;
		sl.y = fY-(fH/2)+72-sl.oy;
		sl.w = fW-48;
		sl.h = fH-144;
		
		if (sl.slots.size() != ChatSpamManager.getSize()) {
			sl.slots.clear();
			int i = 0;
			for(String f : ChatSpamManager.getList()) {
				sl.slots.add(new ChatSpamSlot(sl, ChatSpamManager.getIndex(f), sl.x, sl.y, sl.w, 48, 0, i*48));
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
		String active = null;
		for(Slot s : sl.slots) {
			if (s.active) {
				active = ChatSpamManager.getMessage(((ChatSpamSlot)s).index);
				break;
			}
		}
		switch(inters.indexOf(i)) {
		case 0:
			UIHandler.changeUI(parent);
			break;
		case 1:
			changePopup(new UIPopupChatSpamAdd(this));
			break;
		case 2:
			changePopup(new UIPopupChatSpamEdit(this, active));
			break;
		case 3:
			if (active != null) {
				ChatSpamManager.removeMessage(active);
				Config.saveChatSpam();
			}
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
		String active = null;
		for(Slot s : sl.slots) {
			if (s.active) {
				active = ChatSpamManager.getMessage(((ChatSpamSlot)s).index);
				break;
			}
		}
		if (popup == null) {
			super.keyboardPress(key);
			switch (key) {
			case Keyboard.KEY_ESCAPE:
				mc.displayGuiScreen(null);
				break;
			case Keyboard.KEY_DELETE:
				if (active != null) {
					ChatSpamManager.removeMessage(active);
				}
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
		
		Draw.rect(fX-(fW/2), fY-(fH/2)+48, fX+(fW/2), fY+(fH/2)-48, Util.reAlpha(0xFF202020, 1f*opacity));
		Draw.rect(fX-(fW/2), fY+(fH/2)-48, fX+(fW/2), fY+(fH/2), Util.reAlpha(0xFF111111, 1f*opacity));

		Draw.rect(fX-(fW/2), fY-(fH/2), fX+(fW/2), fY-(fH/2)+48, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		
		Draw.string(Fonts.ttfRoundedBold25, fX, fY-(fH/2)+24, title, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.C, Align.C);
		
		Draw.startClip(fX-(fW/2)+24, fY-(fH/2)+72, fX+(fW/2)-24+sl.sbw, fY+(fH/2)-72);
		sl.render(opacity);
		Draw.endClip();

		super.render(opacity);
		
		if (popup != null) {
			popup.render(opacity*(Math.max(popupOpacity, 0.05f)));
		}
	}
}
