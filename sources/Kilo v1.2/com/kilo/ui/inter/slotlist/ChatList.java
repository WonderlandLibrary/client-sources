package com.kilo.ui.inter.slotlist;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.opengl.Display;

import com.kilo.manager.ActivityManager;
import com.kilo.manager.ServerManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.ui.inter.slotlist.slot.ServerSlot;
import com.kilo.ui.inter.slotlist.slot.Slot;
import com.kilo.util.Util;

public class ChatList extends SlotList {

	public ChatList(float sbw) {
		super(sbw);
	}

	public void update(int mx, int my) {
		if (Float.isNaN(scroll)) {
			scroll = 0;
		}
		if (Float.isNaN(scrollTo)) {
			scrollTo = 0;
		}
		
		float coy = 0;
		for(int i = 0; i < slots.size(); i++) {
			Slot s = slots.get(i);
			s.offsetY = coy;
			s.x = x+s.offsetX;
			s.y = (y+h)-s.offsetY-s.height;
			s.update(mx, my);
			coy+= s.height;
		}
		
		float sh = 0;
		float slotsHeight = 0;
		for(Slot s : slots) {
			slotsHeight+= s.height;
		}
		
		if (slotsHeight > h) {
			sh = Math.max(h*(1/(slotsHeight/h)), 20);
		} else {
			scrollTo = 0;
		}
		float sslh = h-sh;
		
		sbx = x+w;
		sbh = sh;
		sby = (y+h-sbh)+oy-scroll;
		
		if (dragScroll) {
			scrollTo = Math.min(Math.max(0, -my+Display.getHeight()-dragOffsetY), sslh);
			scroll = scrollTo;
		}

		scrollTo = Math.min(Math.max(0, scrollTo), String.valueOf(sslh).equalsIgnoreCase("nan")?scroll:sslh);
		
		if (!String.valueOf(scrollTo).equalsIgnoreCase("nan")) {
			scroll+= (scrollTo-scroll)/3;
		}
		
		float c = -((slotsHeight-h)*(scroll/(h-sh)));
		
		oy = c;
		
		hover = mouseOver(mx, my);
	}
	
	public void mouseClick(int mx, int my, int b) {
		super.mouseClick(mx, my, b);
		if (mx >= sbx && my > sby && mx < sbx+sbw && my <= sby+sbh) {
			dragOffsetY = (-my+Display.getHeight()+(sby-scroll))-sby;
			dragScroll = true;
		}
	}
	
	public void mouseRelease(int mx, int my, int b) {
		super.mouseRelease(mx, my, b);
	}
	
	public void mouseScroll(float s) {
		float slotsHeight = 0;
		for(Slot ss : slots) {
			slotsHeight+= ss.height;
		}
		if (!dragScroll) {
			scrollTo+= (s/120)*(h/slotsHeight)*(slotsHeight/slots.size());
		}
	}
	
	public boolean mouseOver(int mx, int my) {
		return mx >= x+ox && mx < x+w+ox && my > y+oy && my <= y+h+oy;
	}

	public void render(float opacity) {
		for(Slot s : slots) {
			s.render(opacity);
		}
		if (sbh > 0) {
			Draw.rect(x+w, y+oy-1000, x+w+sbw, y+h+oy+1000, Util.reAlpha(Colors.WHITE.c, 0.2f*opacity));
			Draw.rect(sbx, sby, sbx+sbw, sby+sbh, Util.reAlpha(Colors.GREEN.c, 1f*opacity));
		}
	}
}
