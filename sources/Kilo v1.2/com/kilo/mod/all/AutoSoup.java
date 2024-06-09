package com.kilo.mod.all;

import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.InventoryClick;
import com.kilo.mod.util.InventoryUtil;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class AutoSoup extends Module {
	
	private boolean shouldSoup;
	private Timer timer = new Timer();

	public AutoSoup(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("NC+", "Use on servers using NC+", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Health", "Health to eat soup below", Interactable.TYPE.SLIDER, 10, new float[] {0, 20}, false);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (!shouldSoup) {
			int bestSlot = -1;
			int hotBarEmpty = 0;
			int hotBarOther = 0;
			
			for(int i = 36; i <= 44; i++) {
				if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
					ItemStack stack = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
					Item item = stack.getItem();
					if (!(item instanceof ItemSoup)) {
						hotBarOther++;
						continue;
					}
					
					hotBarEmpty++;
				}
			}
			
			for(int i = 0; i <= 35; i++){
				Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
				if (!slot.getHasStack()) {
					continue;
				}
				
				ItemStack stack = slot.getStack();
				Item item = stack.getItem();
				
				if (!(item instanceof ItemSoup)) { continue; }
				
				bestSlot = i;
				break;
			}
	
			if (hotBarEmpty <= 3 && hotBarOther <= 6 && bestSlot != -1 && bestSlot < 36) {
				if (Util.makeBoolean(getOptionValue("nc+"))) {
					InventoryUtil.click(new InventoryClick(mc.thePlayer.inventoryContainer.windowId, bestSlot, 1));
				} else {
					mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, bestSlot, 0, 1, mc.thePlayer);
				}
			}
		}
		
		if (mc.thePlayer.getHealth() > Util.makeFloat(getOptionValue("health"))) { shouldSoup = false; return; }

		shouldSoup = timer.isTime(0.5f);
	}
	
	public void onPlayerUpdate() {
		if (!shouldSoup) { return; }

		timer.reset();
		
		shouldSoup = false;

		new Thread() {
			@Override
			public void run() {
				int bestSlot = -1;

				int repeat = 0;
				
				if (mc.thePlayer.getHealth() > 14) {
					repeat = 1;
				} else if (mc.thePlayer.getHealth() > 8) {
					repeat = 2;
				} else if (mc.thePlayer.getHealth() > 2) {
					repeat = 3;
				} else {
					repeat = 4;
				}

				for(int r = 0; r < repeat; r++) {
					for(int i = 44; i >= 36; i--){
						Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
						if (!slot.getHasStack()) {
							continue;
						}
	
						ItemStack stack = slot.getStack();
						Item item = stack.getItem();
	
						if (!(item instanceof ItemSoup)) { continue; }
						
						bestSlot = i;
						break;
					}
						
					if (bestSlot != -1) {
						int prev = mc.thePlayer.inventory.currentItem;
						mc.thePlayer.inventory.currentItem = bestSlot-36;
						if (mc.thePlayer.getCurrentEquippedItem() != null) {
							mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
						}
						mc.thePlayer.inventory.currentItem = prev;
						mc.playerController.syncCurrentPlayItem();
						try {
							sleep(50);
							InventoryUtil.click(new InventoryClick(mc.thePlayer.inventoryContainer.windowId, bestSlot, 4));
							sleep(50);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
	}
}
