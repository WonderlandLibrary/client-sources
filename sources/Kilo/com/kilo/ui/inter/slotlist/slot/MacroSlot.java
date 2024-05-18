package com.kilo.ui.inter.slotlist.slot;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

import com.kilo.manager.MacroManager;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.ui.inter.slotlist.part.Macro;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class MacroSlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
	
	public int index;
	
	public MacroSlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
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
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF2A2A2A, hoverOpacity*opacity));
		Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF2A2A2A, activeOpacity*opacity));
		if (MacroManager.getMacro(index) != null) {
			Macro w = MacroManager.getMacro(index);
			String name = w.name;
			for(int i = 0; i < name.length(); i++) {
				if (Fonts.ttfRoundedBold12.getWidth(name.substring(0, i)) > (parent.w/2)-60-Fonts.ttfRoundedBold12.getWidth("...")) {
					name = name.substring(0, i)+"...";
					break;
				}
			}
			
			Draw.string(Fonts.ttfRoundedBold12, x+16, y+24, name, Util.reAlpha(Colors.WHITE.c, 1f*opacity), Align.L, Align.B);
			Draw.string(Fonts.ttfRoundedBold12, x+16, y+24, w.command, Util.reAlpha(Colors.GREY.c, 1f*opacity), Align.L, Align.T);
			Draw.string(Fonts.ttfRoundedBold10, x+width-16, y+24, "Keybind: "+Keyboard.getKeyName(w.keybind), Util.reAlpha(Colors.GREY.c, 1f*opacity), Align.R, Align.C);
		}
		super.render(opacity);
	}
	
}
