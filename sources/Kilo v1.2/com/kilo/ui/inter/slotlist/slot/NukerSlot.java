package com.kilo.ui.inter.slotlist.slot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;

import com.kilo.manager.NukerManager;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.all.Nuker;
import com.kilo.mod.util.NukerHandler;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.util.Align;
import com.kilo.util.Util;

public class NukerSlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
	
	public int index;
	
	public NukerSlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		index = i;
		
		inters.clear();
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		if (NukerHandler.blocks.get(NukerManager.getNukerBlock(index).name) != null) {
			active = NukerHandler.blocks.get(NukerManager.getNukerBlock(index).name);
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (parent.mouseOver(mx, my) && hover) {
			active = !active;
		}
		if (NukerHandler.blocks.get(NukerManager.getNukerBlock(index).name) != null && NukerHandler.blocks.get(NukerManager.getNukerBlock(index).name) != active) {
			NukerHandler.blocks.put(NukerManager.getNukerBlock(index).name, active);
		} else if (NukerHandler.blocks.get(NukerManager.getNukerBlock(index).name) == null) {
			NukerHandler.blocks.put(NukerManager.getNukerBlock(index).name, active);
		}
	}
	
	public void render(float opacity) {
			GlStateManager.disableLighting();
			Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF2A2A2A, activeOpacity*opacity));
			Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF1A1A1A, hoverOpacity*opacity));
			if (NukerManager.getNukerBlock(index) != null) {
				String name = NukerManager.getNukerBlock(index).display;
				for(int i = 0; i < name.length(); i++) {
					if (Fonts.ttfRoundedBold12.getWidth(name.substring(0, i)) > parent.w-60-Fonts.ttfRoundedBold12.getWidth("...")) {
						name = name.substring(0, i)+"...";
						break;
					}
				}

				GlStateManager.pushMatrix();
				GlStateManager.scale(2, 2, 2);
				ItemStack stack = NukerManager.getNukerBlock(index).stack;
				if (stack != null && stack.getItem() != null) {
					mc.getRenderItem().renderItemAndEffectIntoGUI(NukerManager.getNukerBlock(index).stack, (int)(x+4)/2, (int)(y+4)/2);
				}
				GlStateManager.popMatrix();
	
				GlStateManager.disableLighting();
				Draw.string(Fonts.ttfRoundedBold12, x+48, y+24, name, Util.reAlpha(active?Colors.GREEN.c:Colors.RED.c, 1f*opacity), Align.L, Align.C);
			}
			super.render(opacity);
	}
	
}
