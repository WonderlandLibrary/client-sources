package com.kilo.mod.all;

import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.InventoryClick;
import com.kilo.mod.util.InventoryUtil;
import com.kilo.mod.util.ItemValue;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class AutoPotion extends Module {
	
	private boolean shouldPot;
	private float[] oldRotation = new float[] {0, 0};
	private Timer timer = new Timer();

	public AutoPotion(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);

		addOption("NC+", "Use on servers using NC+", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Speed", "Delay between throwing potions down", Interactable.TYPE.SLIDER, 1, new float[] {0, 10}, true);
		addOption("Health", "Health to splash potions below", Interactable.TYPE.SLIDER, 10, new float[] {0, 20}, false);
		addOption("Amount", "Amount of potions to splash when below health", Interactable.TYPE.SLIDER, 1, new float[] {1, 3}, false);
	}
	
	public void update() {
		if (!active) { return; }
		
		if (!shouldPot) {
			int bestSlot = -1;
			int hotBarEmpty = 0;
			int hotBarItem = 0;
			
			for(int i = 36; i <= 44; i++) {
				Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
				if (!slot.getHasStack()) {
					hotBarEmpty++;
					continue;
				}
				hotBarItem++;
			}
			
			for(int i = 0; i <= 35; i++){
				Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
				if (!slot.getHasStack()) {
					continue;
				}
				
				ItemStack stack = slot.getStack();
				Item item = stack.getItem();
				
				if (!(item instanceof ItemPotion)) { continue; }
				
				ItemPotion potion = (ItemPotion)item;
				
				if (!ItemPotion.isSplash(stack.getItemDamage())) { continue; }
				if (stack.getMetadata() != 16421 && stack.getMetadata() != 16453) { continue; }
				
				bestSlot = i;
				break;
			}
	
			if (hotBarEmpty > 0 && bestSlot != -1) {
				if (Util.makeBoolean(getOptionValue("nc+"))) {
					InventoryUtil.click(new InventoryClick(mc.thePlayer.inventoryContainer.windowId, bestSlot, 1));
				} else {
					mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, bestSlot, 0, 1, mc.thePlayer);
				}
			}
		}
		
		if (mc.thePlayer.getHealth() > Util.makeFloat(getOptionValue("health"))) { shouldPot = false; return; }
		
		shouldPot = timer.isTime(Util.makeFloat(getOptionValue("speed")));
	}
	
	public void onPlayerUpdate() {
		if (!shouldPot) { return; }

		timer.reset();
		
		shouldPot = false;

		new Thread() {
			@Override
			public void run() {
				int bestSlot = -1;

				int repeat = Util.makeInteger(getOptionValue("amount"));

				for(int r = 0; r < repeat; r++) {
					for(int i = 44; i >= 36; i--){
						Slot slot = mc.thePlayer.inventoryContainer.getSlot(i);
						if (!slot.getHasStack()) {
							continue;
						}

						ItemStack stack = slot.getStack();
						Item item = stack.getItem();

						if (!(item instanceof ItemPotion)) { continue; }
						
						ItemPotion potion = (ItemPotion)item;
						
						if (!ItemPotion.isSplash(stack.getItemDamage())) { continue; }
						if (stack.getMetadata() != 16421 && stack.getMetadata() != 16453) { continue; }
						
						bestSlot = i;
						break;
					}
					
					if (bestSlot != -1) {
						mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(mc.thePlayer.rotationYaw, 90, true));
						
						int prev = mc.thePlayer.inventory.currentItem;
						mc.thePlayer.inventory.currentItem = bestSlot-36;
						if (mc.thePlayer.getCurrentEquippedItem() != null) {
							mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getCurrentEquippedItem());
						}
						mc.thePlayer.inventory.currentItem = prev;
						mc.playerController.syncCurrentPlayItem();
						try {
							sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}.start();
	}
}
