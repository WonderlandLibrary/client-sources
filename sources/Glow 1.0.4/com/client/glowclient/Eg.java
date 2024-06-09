package com.client.glowclient;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;

public class Eg extends Container
{
    public boolean canInteractWith(final EntityPlayer entityPlayer) {
        return false;
    }
    
    public Eg(final PF pf, final int n) {
        final int i = 0;
        super();
        int n2 = i;
        while (i < n) {
            final int n3 = n2 % 9 * 18;
            final int n4 = (n2 / 9 + 1) * 18 + 1;
            final int n5 = n2;
            final int n6 = n3;
            ++n2;
            this.addSlotToContainer(new Slot((IInventory)pf, n5, n6, n4));
        }
    }
}
