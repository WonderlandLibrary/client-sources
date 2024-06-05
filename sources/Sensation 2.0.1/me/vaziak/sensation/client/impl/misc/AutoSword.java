package me.vaziak.sensation.client.impl.misc;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.utils.anthony.ItemUtil;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class AutoSword extends Module { 
	private TimerUtil timer;

	private DoubleProperty slot = new DoubleProperty("Slot", "The slot you want the sword to go to", null, 1, 1, 9, 1);
    public AutoSword() {
        super("Auto Sword", Category.MISC); 
        timer = new TimerUtil();
        registerValue(slot);
    }

    public void onEnable() {
        mc.timer.timerSpeed = 1.0f;
    }

    public void onDisable() {
        mc.timer.timerSpeed = 1.0f;
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent event) { 
    	if (!(event.isPre() || !timer.hasPassed(250) || (mc.currentScreen != null && !(mc.currentScreen instanceof GuiInventory)))) {
	        int best = -1;
	        float swordDamage = 0;
	        for (int i = 9; i < 45; ++i) {
	            if (mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
	                final ItemStack is = mc.thePlayer.inventoryContainer.getSlot(i).getStack();
	                if (is.getItem() instanceof ItemSword) {
	                    float swordD = ItemUtil.getItemDamage(is);
	                    if (swordD > swordDamage) {
	                        swordDamage = swordD;
	                        best = i;
	                    }
	                }
	            }
	        }
	        ItemStack current = mc.thePlayer.inventoryContainer.getSlot(36 + 1).getStack();
	        if (best != -1 && (current == null || !(current.getItem() instanceof ItemSword) || swordDamage > ItemUtil.getItemDamage(current))) {
	            mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, best, slot.getValue().intValue() - 1, 2, mc.thePlayer);
	            timer.reset();
	        } 
    	}
    }

    public int getSlot() {
    	return slot.getValue().intValue();
    }
}