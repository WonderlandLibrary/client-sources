package com.client.glowclient.modules.combat;

import com.client.glowclient.modules.*;
import com.client.glowclient.events.*;
import com.client.glowclient.*;
import com.client.glowclient.utils.*;
import net.minecraft.item.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class AutoArmor extends ModuleContainer
{
    private Timer b;
    
    public AutoArmor() {
        super(Category.COMBAT, "AutoArmor", false, -1, "Automatically equip best armor");
        this.b = new Timer();
    }
    
    @SubscribeEvent
    public void D(final EventUpdate eventUpdate) {
        if (!this.b.hasBeenSet()) {
            this.b.reset();
            return;
        }
        if (this.b.delay(0.0)) {
            final int n = 4;
            this.b.reset();
            final int[] array = new int[n];
            int n2;
            int i = n2 = 0;
            while (i < 4) {
                final ItemStack armorItemInSlot;
                if ((armorItemInSlot = Wrapper.mc.player.inventory.armorItemInSlot(n2)).getItem() instanceof ItemArmor) {
                    array[n2] = ((ItemArmor)armorItemInSlot.getItem()).damageReduceAmount;
                }
                i = ++n2;
            }
            final int[] array2 = { -1, -1, -1, -1 };
            int n3;
            int j = n3 = 0;
            while (j < 36) {
                final ItemStack stackInSlot;
                if ((stackInSlot = Wrapper.mc.player.inventory.getStackInSlot(n3)).getItem() instanceof ItemArmor) {
                    final ItemArmor itemArmor2;
                    final ItemArmor itemArmor = itemArmor2 = (ItemArmor)stackInSlot.getItem();
                    final int m = UB.M(itemArmor);
                    if (itemArmor.damageReduceAmount > array[m]) {
                        final int[] array3 = array2;
                        final int[] array4 = array;
                        final int n4 = m;
                        array4[n4] = itemArmor2.damageReduceAmount;
                        array3[n4] = n3;
                    }
                }
                j = ++n3;
            }
            int n5;
            int k = n5 = 0;
            while (k < 4) {
                final int n6;
                if ((n6 = array2[n5]) != -1) {
                    ControllerUtils.pickupSlot((n6 < 9) ? (36 + n6) : n6);
                    ControllerUtils.pickupSlot(8 - n5);
                    ControllerUtils.pickupSlot((n6 < 9) ? (36 + n6) : n6);
                }
                k = ++n5;
            }
        }
    }
}
