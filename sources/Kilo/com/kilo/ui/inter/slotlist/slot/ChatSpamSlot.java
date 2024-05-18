package com.kilo.ui.inter.slotlist.slot;

import net.minecraft.client.Minecraft;

import com.kilo.manager.ChatSpamManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class ChatSpamSlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
	
	public int index;
	
	public ChatSpamSlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		index = i;
		
		inters.clear();
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (parent.mouseOver(mx, my)) {
			active = hover;
		}
	}
	
	public void render(float opacity) {
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF2A2A2A, activeOpacity*opacity));
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF1A1A1A, hoverOpacity*opacity));
		if (ChatSpamManager.getMessage(index) != null) {
			String name = ChatSpamManager.getMessage(index);
			for(int i = 0; i < name.length(); i++) {
				if (Fonts.ttfRoundedBold14.getWidth(name.substring(0, i)) > parent.w-60-Fonts.ttfRoundedBold14.getWidth("...")) {
					name = name.substring(0, i)+"...";
					break;
				}
			}
			
			Draw.string(Fonts.ttfRoundedBold14, x+16, y+24, name, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.L, Align.C);
		}
		super.render(opacity);
	}
	
}
