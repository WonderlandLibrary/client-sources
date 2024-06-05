package com.thunderware.module.visuals;

import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.thunderware.Thunder;
import com.thunderware.events.Event;
import com.thunderware.events.listeners.EventRender2D;
import com.thunderware.module.ModuleBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class Hud extends ModuleBase {

	public Hud() {
		super("Hud", Keyboard.KEY_NONE, Category.VISUALS);
		setToggled(true);
	}

	public void onEnable() { }
	/*
	  				
	 */
	public static class sortDefaultFont implements Comparator<ModuleBase> {
		@Override
		public int compare(ModuleBase arg0, ModuleBase arg1) {
				if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.getName() + (arg0.getSuffix().length() > 1 ? (" " + arg0.getSuffix()) : "")) > Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.getName() + (arg1.getSuffix().length() > 1 ? (" " + arg1.getSuffix()) : ""))) {
					return -1;
				} else if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.getName() + (arg0.getSuffix().length() > 1 ? (" " + arg0.getSuffix()) : "")) < Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.getName() + (arg1.getSuffix().length() > 1 ? (" " + arg1.getSuffix()) : "")))
					return 1;
			return 0;
		}
	}
	
	public static class sortCustomFont implements Comparator<ModuleBase> {
		@Override
		public int compare(ModuleBase arg0, ModuleBase arg1) {
				if (mc.customFont.getStringWidth(arg0.getName() + (arg0.getSuffix().length() > 1 ? (" " + arg0.getSuffix()) : "")) > mc.customFont.getStringWidth(arg1.getName() + (arg1.getSuffix().length() > 1 ? (" " + arg1.getSuffix()) : ""))) {
					return -1;
				} else if (mc.customFont.getStringWidth(arg0.getName() + (arg0.getSuffix().length() > 1 ? (" " + arg0.getSuffix()) : "")) < mc.customFont.getStringWidth(arg1.getName() + (arg1.getSuffix().length() > 1 ? (" " + arg1.getSuffix()) : "")))
					return 1;
			return 0;
		}
	}
	
	public static int getColor(int offset) {
		return astolfo(offset * 2);
	}
	
	public static int astolfo(int offset) {
		int i = (int) ((System.currentTimeMillis() / 11 + offset) % 360);
        i = (i > 180 ? 360 - i : i) + 180;
        return Color.HSBtoRGB(i / 360f, 0.55f, 1f);
	}

	
	public void onEvent(Event event) {
		if(event instanceof EventRender2D) {
			EventRender2D e = (EventRender2D)event;
			CopyOnWriteArrayList<ModuleBase> modules = Thunder.i.moduleManager.getModules();
			Collections.sort(modules, new sortCustomFont());
			int count = 0;
			int totalCount = 0;
			mc.customFont.drawStringWithShadow("T\247fhunderware", 3, 3, getColor(3));

			double padding = 7;
			for(ModuleBase mod : modules) {
				if(mod.isToggled()) {
					String text = mod.getName() + (mod.getSuffix().length() > 0 ? "\2477 " + mod.getSuffix() : "");
					if(count == 0) {
						Gui.drawRect(e.getWidth() - mc.customFont.getStringWidth(text) - padding - 1, 3, e.getWidth() - 2, 2, getColor(3));
					}
					double x = e.getWidth() - mc.customFont.getStringWidth(text) - padding - 1;
					double y = (count * (mc.customFont.getHeight() + 3)) + 3;
					double endX = e.getWidth() - 3;
					double endY = (count * (mc.customFont.getHeight() + 3)) + mc.customFont.getHeight() + 6;
					
					Gui.drawRect(x, y, endX, endY, 0x80000000);
					Gui.drawGradientRect(endX,y,endX + 1,y + mc.customFont.getHeight() + 2 + 1,getColor((int)y),getColor((int)endY));
					
					
					mc.customFont.drawString(text, endX - mc.customFont.getStringWidth(text) - (padding / 2 - 1), (count * (mc.customFont.getHeight() + 3)) + 4, getColor((int)y));
					count++;
				}
				totalCount++;
			}
			
			//Gui.drawRect(0, 0, 10, 10, -1);
		}
	}
}
