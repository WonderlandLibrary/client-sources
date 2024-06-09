package com.client.glowclient.modules.player;

import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import com.client.glowclient.utils.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoEat extends ModuleContainer
{
    private boolean b;
    
    public AutoEat() {
        final boolean b = false;
        super(Category.PLAYER, "AutoEat", false, -1, "Auto eats when you get hungry");
        this.b = b;
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        try {
            final FoodStats foodStats = Wrapper.mc.player.getFoodStats();
            int currentItem = -1;
            ItemStack itemStack = null;
            int n;
            int i = n = 0;
            while (true) {
                while (i < 9) {
                    final ItemStack stackInSlot;
                    if ((stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(n)) != null && stackInSlot.getItem() instanceof ItemFood && !(stackInSlot.getItem() instanceof ItemAppleGold)) {
                        currentItem = n;
                        final ItemStack itemStack2 = itemStack = stackInSlot;
                        if (itemStack2 != null && 20 - foodStats.getFoodLevel() >= ((ItemFood)itemStack.getItem()).getHealAmount(itemStack) && !Wrapper.mc.player.isHandActive() && Wrapper.mc.rightClickDelayTimer == 0) {
                            this.b = true;
                            Wrapper.mc.player.inventory.currentItem = currentItem;
                            KeybindHelper.keyUseItem.M(true);
                            Wrapper.mc.rightClickMouse();
                            return;
                        }
                        if (this.b && !Wrapper.mc.player.isHandActive()) {
                            this.b = false;
                            KeybindHelper.keyUseItem.M(false);
                        }
                        return;
                    }
                    else {
                        i = ++n;
                    }
                }
                final ItemStack itemStack2 = itemStack;
                continue;
            }
        }
        catch (Exception ex) {}
    }
}
