package com.kilo.mod.util;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.minecraft.client.Minecraft;

import com.kilo.util.Timer;

public class InventoryUtil {
	
	private static final Minecraft mc = Minecraft.getMinecraft(); 

	private static final float delay = 0.1f;
	private static final Timer timer = new Timer();
	
	private static List<InventoryClick> clicks = new CopyOnWriteArrayList<InventoryClick>();
	
	public static void tick() {
		if (!clicks.isEmpty()) {
			for(InventoryClick ic : clicks) {
				if (timer.isTime(delay)) {
					timer.reset();
					mc.playerController.windowClick(ic.id, ic.slot, 0, ic.mode, mc.thePlayer);
					clicks.remove(ic);
				}
			}
		}
	}
	
	public static void click(InventoryClick ic) {
		if (!exists(ic)) {
			clicks.add(ic);
		}
	}
	
	public static boolean exists(InventoryClick i) {
		for(InventoryClick ic : clicks) {
			if (ic.slot == i.slot && ic.mode == i.mode) {
				return true;
			}
		}
		return false;
	}
	
}
