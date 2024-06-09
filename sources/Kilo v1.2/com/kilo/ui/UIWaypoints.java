package com.kilo.ui;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.manager.WaypointManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.Button;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.part.Waypoint;
import com.kilo.ui.inter.slotlist.slot.Slot;
import com.kilo.ui.inter.slotlist.slot.WaypointSlot;
import com.kilo.util.Align;
import com.kilo.util.Config;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class UIWaypoints extends UI {

	private UI popup, popupTo;
	private boolean popupFade;
	private float fX, fY, fW, fH, popupOpacity;

	public SlotList wsl;
	
	public UIWaypoints(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Waypoints";
		
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 600;
		fH = 48+48+48+(48*Math.min(8, WaypointManager.getSize()));

		wsl = new SlotList(7f);
		
		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
		inters.add(new Button(this, "Create Waypoint", fX-72-8-144, fY+(fH/2)-40, 144, 32, Fonts.ttfRoundedBold14, Colors.GREEN.c, null, 0));
		inters.add(new Button(this, "Edit Waypoint", fX-72, fY+(fH/2)-40, 144, 32, Fonts.ttfRoundedBold14, Colors.GREY.c, null, 0));
		inters.add(new Button(this, "Delete Waypoint", fX+72+8, fY+(fH/2)-40, 144, 32, Fonts.ttfRoundedBold14, Colors.RED.c, null, 0));
	}
	
	public void update(int mx, int my) {
		if (popup == null) {
			super.update(mx, my);
		}
		
		if (fH != 48+48+48+(48*Math.min(8, WaypointManager.getSize()))) {
			init();
		}

		Waypoint activeWaypoint = null;
		for(Slot s : wsl.slots) {
			if (s.active) {
				activeWaypoint = WaypointManager.getWaypoint(((WaypointSlot)s).index);
				break;
			}
		}
		((Button)inters.get(2)).enabled = activeWaypoint != null;
		((Button)inters.get(3)).enabled = activeWaypoint != null;
		
		wsl.x = fX-(fW/2)+24;
		wsl.y = fY-(fH/2)+72-wsl.oy;
		wsl.w = fW-48;
		wsl.h = fH-144;
		
		if (wsl.slots.size() != WaypointManager.getSize()) {
			wsl.slots.clear();
			int i = 0;
			for(Waypoint w : WaypointManager.getList()) {
				wsl.slots.add(new WaypointSlot(wsl, WaypointManager.getIndex(w), wsl.x, wsl.y, wsl.w, 48, 0, i*48));
				i++;
			}
		}

		wsl.update(mx, my);
		
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
		Waypoint activeWaypoint = null;
		for(Slot s : wsl.slots) {
			if (s.active) {
				activeWaypoint = WaypointManager.getWaypoint(((WaypointSlot)s).index);
				break;
			}
		}
		switch(inters.indexOf(i)) {
		case 0:
			UIHandler.changeUI(parent);
			break;
		case 1:
			changePopup(new UIPopupWaypointCreate(this));
			break;
		case 2:
			changePopup(new UIPopupWaypointEdit(this, activeWaypoint));
			break;
		case 3:
			if (activeWaypoint != null) {
				WaypointManager.removeWaypoint(activeWaypoint);
				Config.saveWaypoints();
			}
			break;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (popup == null) {
			super.mouseClick(mx, my, b);
			wsl.mouseClick(mx, my, b);
		} else {
			popup.mouseClick(mx, my, b);
		}
	}
	
	public void mouseRelease(int mx, int my, int b) {
		if (popup == null) {
			super.mouseRelease(mx, my, b);
			wsl.mouseRelease(mx, my, b);
		} else {
			popup.mouseRelease(mx, my, b);
		}
	}
	
	public void mouseScroll(int s) {
		if (popup == null) {
			super.mouseScroll(s);
			wsl.mouseScroll(s);
		} else {
			popup.mouseScroll(s);
		}
	}
	
	public void keyboardPress(int key) {
		Waypoint activeWaypoint = null;
		for(Slot s : wsl.slots) {
			if (s.active) {
				activeWaypoint = WaypointManager.getWaypoint(((WaypointSlot)s).index);
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
				if (activeWaypoint != null) {
					WaypointManager.removeWaypoint(activeWaypoint);
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
		
		Draw.startClip(fX-(fW/2)+24, fY-(fH/2)+72, fX+(fW/2)-24+wsl.sbw, fY+(fH/2)-72);
		wsl.render(opacity);
		Draw.endClip();

		super.render(opacity);
		
		if (popup != null) {
			popup.render(opacity*(Math.max(popupOpacity, 0.05f)));
		}
	}
}
