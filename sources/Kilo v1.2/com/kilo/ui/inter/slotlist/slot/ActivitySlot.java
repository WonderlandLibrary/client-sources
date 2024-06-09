package com.kilo.ui.inter.slotlist.slot;

import net.minecraft.client.Minecraft;

import com.kilo.manager.ActivityManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.ui.inter.Inter;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public abstract class ActivitySlot extends Slot {

	final Minecraft mc = Minecraft.getMinecraft();
	
	public int index;
	
	public int clicks = 0;
	public Timer clickTimer = new Timer();
	
	public boolean checking;
	
	public ActivitySlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		index = i;
		
		clickTimer.reset();
		checking = false;
		
		inters.clear();
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		hover = mouseOver(mx, my) && parent.mouseOver(mx, my);

		if (clicks == 2) {
			doubleClick(mx, my);
			clicks = 0;
		}
		
		if (clickTimer.isTime(Util.doubleClickTimer)) {
			clickTimer.reset();
			clicks = 0;
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (hover) {
			if (clicks == 0) {
				clickTimer.reset();
			}
			clicks++;
		}
		super.mouseClick(mx, my, b);
	}
	
	public void doubleClick(int mx, int my) {}
	
	public abstract void interact(Inter i);
	
	public void render(float opacity) {
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF1A1A1A, hoverOpacity*opacity));
		if (checking) {
			Draw.loader(x+(width/2), y+(height/2), 8, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			return;
		}
		if (ActivityManager.getActivity(index) != null) {
			boolean noIcon = true;
			if (ActivityManager.getActivity(index).icon != null) {
				if (ActivityManager.getActivity(index).icon.getTexture() != null) {
					Draw.rectTexture(x+4, y+4, 40, 40, ActivityManager.getActivity(index).icon.getTexture(), opacity);
					noIcon = false;
				}
			}
			if (noIcon) {
				Draw.loader(x+24, y+24, 8, Util.reAlpha(Colors.WHITE.c, 1f*opacity));
			}
		}
		super.render(opacity);
	}
	
}
