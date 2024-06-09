package com.kilo.ui;

import net.minecraft.client.renderer.RenderHelper;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import com.kilo.manager.WaypointManager;
import com.kilo.manager.NukerManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.IconButton;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.part.Waypoint;
import com.kilo.ui.inter.slotlist.part.NukerBlock;
import com.kilo.ui.inter.slotlist.slot.Slot;
import com.kilo.ui.inter.slotlist.slot.WaypointSlot;
import com.kilo.ui.inter.slotlist.slot.NukerSlot;
import com.kilo.util.Align;
import com.kilo.util.Resources;
import com.kilo.util.Util;

public class UINuker extends UI {

	private float fX, fY, fW, fH;

	public SlotList sl;
	
	public UINuker(UI parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		title = "Nuker";
		
		
		fX = Display.getWidth()/2;
		fY = (Display.getHeight()/2);
		fW = 600;
		fH = 48+48+(48*8);

		NukerManager.loadBlocks();
		sl = new SlotList(7f);
		
		inters.clear();
		inters.add(new IconButton(this, fX+(fW/2)-40, fY-(fH/2)+12, 24, 24, 0x00000000, Resources.iconClose[2]));
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		sl.x = fX-(fW/2)+24;
		sl.y = fY-(fH/2)+72-sl.oy;
		sl.w = fW-48;
		sl.h = fH-96;
		
		if (sl.slots.size() != NukerManager.getSize()) {
			sl.slots.clear();
			int i = 0;
			for(NukerBlock w : NukerManager.getList()) {
				sl.slots.add(new NukerSlot(sl, NukerManager.getIndex(w), sl.x, sl.y, sl.w, 48, 0, i*48));
				i++;
			}
		}

		sl.update(mx, my);
	}
	
	@Override
	public void interact(final Inter i) {
		switch(inters.indexOf(i)) {
		case 0:
			UIHandler.changeUI(parent);
			break;
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
		sl.mouseClick(mx, my, b);
	}
	
	public void mouseRelease(int mx, int my, int b) {
		super.mouseRelease(mx, my, b);
		sl.mouseRelease(mx, my, b);
	}
	
	public void mouseScroll(int s) {
		super.mouseScroll(s);
		sl.mouseScroll(s);
	}
	
	public void keyboardPress(int key) {
		super.keyboardPress(key);
		switch (key) {
		case Keyboard.KEY_ESCAPE:
			mc.displayGuiScreen(null);
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
		
		RenderHelper.enableGUIStandardItemLighting();
		Draw.startClip(fX-(fW/2)+24, fY-(fH/2)+72, fX+(fW/2)-24+sl.sbw, fY+(fH/2)-24);
		sl.render(opacity);
		Draw.endClip();
		RenderHelper.disableStandardItemLighting();

		super.render(opacity);
	}
}
