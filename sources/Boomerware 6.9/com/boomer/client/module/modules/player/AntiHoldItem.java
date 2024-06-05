package com.boomer.client.module.modules.player;

import com.boomer.client.event.bus.Handler;
import com.boomer.client.event.events.player.UpdateEvent;
import com.boomer.client.module.Module;
import com.boomer.client.utils.TimerUtil;
import com.boomer.client.utils.value.impl.NumberValue;

import net.minecraft.potion.Potion;

public class AntiHoldItem extends Module {
    private NumberValue<Integer> delay = new NumberValue<>("Delay", 100, 1, 2000, 1);
    private TimerUtil timer = new TimerUtil();

    public AntiHoldItem() {
        super("AntiHoldItem", Category.PLAYER, 0x34DE1F);
        setDescription("Don't be seen holding an item when invis");
        setRenderlabel("Anti Hold Item");
        addValues(delay);
    }

    @Handler
    public void onUpdate(UpdateEvent event) {
    	if (!event.isPre() && mc.thePlayer.isPotionActive(Potion.invisibility) && mc.thePlayer.getHeldItem() != null && timer.reach(delay.getValue())) {
            for (int i = 9; i < 45; ++i) {
            	if (i == mc.thePlayer.inventory.currentItem + 36 || mc.thePlayer.inventoryContainer.getSlot(i).getStack() != null) {
            		continue;
            	}
                mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, i, mc.thePlayer.inventory.currentItem, 2, mc.thePlayer);
                timer.reset();
                return;
            }
            mc.playerController.windowClick(0, mc.thePlayer.inventory.currentItem + 36, 1, 4, mc.thePlayer);
            timer.reset();
    	}
    }
}