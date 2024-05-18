/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.inventory;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerDispenser
extends Container {
    private IInventory field_178146_a;
    private static final String __OBFID = "CL_00001763";

    public ContainerDispenser(IInventory p_i45799_1_, IInventory p_i45799_2_) {
        int var4;
        this.field_178146_a = p_i45799_2_;
        int var3 = 0;
        while (var3 < 3) {
            var4 = 0;
            while (var4 < 3) {
                this.addSlotToContainer(new Slot(p_i45799_2_, var4 + var3 * 3, 62 + var4 * 18, 17 + var3 * 18));
                ++var4;
            }
            ++var3;
        }
        var3 = 0;
        while (var3 < 3) {
            var4 = 0;
            while (var4 < 9) {
                this.addSlotToContainer(new Slot(p_i45799_1_, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
                ++var4;
            }
            ++var3;
        }
        var3 = 0;
        while (var3 < 9) {
            this.addSlotToContainer(new Slot(p_i45799_1_, var3, 8 + var3 * 18, 142));
            ++var3;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.field_178146_a.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(index);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (index < 9 ? !this.mergeItemStack(var5, 9, 45, true) : !this.mergeItemStack(var5, 0, 9, false)) {
                return null;
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            } else {
                var4.onSlotChanged();
            }
            if (var5.stackSize == var3.stackSize) {
                return null;
            }
            var4.onPickupFromSlot(playerIn, var5);
        }
        return var3;
    }
}

