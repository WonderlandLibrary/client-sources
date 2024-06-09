package com.kilo.ui.inter.slotlist.slot;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.kilo.manager.XrayManager;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.all.Xray;
import com.kilo.mod.util.XrayHandler;
import com.kilo.render.Colors;
import com.kilo.render.Draw;
import com.kilo.render.Fonts;
import com.kilo.ui.inter.slotlist.SlotList;
import com.kilo.util.Align;
import com.kilo.util.Config;
import com.kilo.util.Util;

public class XraySlot extends Slot {

	private final Minecraft mc = Minecraft.getMinecraft();
	
	public int index;
	
	public XraySlot(SlotList p, int i, float x, float y, float w, float h, float ox, float oy) {
		super(p, x, y, w, h, ox, oy);
		index = i;
		
		inters.clear();
	}
	
	public void update(int mx, int my) {
		super.update(mx, my);
		
		if (XrayHandler.blocks.get(XrayManager.getXrayBlock(index).name) != null) {
			active = XrayHandler.blocks.get(XrayManager.getXrayBlock(index).name);
		}
	}
	
	public void mouseClick(int mx, int my, int b) {
		if (parent.mouseOver(mx, my) && hover) {
			active = !active;
		}
		if (XrayHandler.blocks.get(XrayManager.getXrayBlock(index).name) != null && XrayHandler.blocks.get(XrayManager.getXrayBlock(index).name) != active) {
			XrayHandler.blocks.put(XrayManager.getXrayBlock(index).name, active);
			if (ModuleManager.get("xray").active) {
				mc.renderGlobal.loadRenderers();
			}
		} else if (XrayHandler.blocks.get(XrayManager.getXrayBlock(index).name) == null) {
			XrayHandler.blocks.put(XrayManager.getXrayBlock(index).name, active);
		}
	}
	
	public void render(float opacity) {
			Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF2A2A2A, activeOpacity*opacity));
			Draw.rect(x, y, x+width, y+height, Util.reAlpha(0xFF1A1A1A, hoverOpacity*opacity));
			if (XrayManager.getXrayBlock(index) != null) {
				String name = XrayManager.getXrayBlock(index).display;
				for(int i = 0; i < name.length(); i++) {
					if (Fonts.ttfRoundedBold12.getWidth(name.substring(0, i)) > parent.w-60-Fonts.ttfRoundedBold12.getWidth("...")) {
						name = name.substring(0, i)+"...";
						break;
					}
				}

				GlStateManager.pushMatrix();
				GlStateManager.scale(2, 2, 2);
				ItemStack stack = XrayManager.getXrayBlock(index).stack;
				if (stack != null && stack.getItem() != null) {
					RenderHelper.enableGUIStandardItemLighting();
					mc.getRenderItem().renderItemAndEffectIntoGUI(XrayManager.getXrayBlock(index).stack, (int)(x+4)/2, (int)(y+4)/2);
					RenderHelper.disableStandardItemLighting();
				}
				GlStateManager.popMatrix();
	
				GlStateManager.disableLighting();
				Draw.string(Fonts.ttfRoundedBold12, x+48, y+24, name, Util.reAlpha(active?Colors.GREEN.c:Colors.RED.c, 1f*opacity), Align.L, Align.C);
			}
			super.render(opacity);
	}
	
}
