/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.inventory;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHopper
extends Container {
    private final IInventory field_94538_a;
    private static final String __OBFID = "CL_00001750";

    public ContainerHopper(InventoryPlayer p_i45792_1_, IInventory p_i45792_2_, EntityPlayer p_i45792_3_) {
        this.field_94538_a = p_i45792_2_;
        p_i45792_2_.openInventory(p_i45792_3_);
        int var4 = 51;
        int var5 = 0;
        while (var5 < p_i45792_2_.getSizeInventory()) {
            this.addSlotToContainer(new Slot(p_i45792_2_, var5, 44 + var5 * 18, 20));
            ++var5;
        }
        var5 = 0;
        while (var5 < 3) {
            int var6 = 0;
            while (var6 < 9) {
                this.addSlotToContainer(new Slot(p_i45792_1_, var6 + var5 * 9 + 9, 8 + var6 * 18, var5 * 18 + var4));
                ++var6;
            }
            ++var5;
        }
        var5 = 0;
        while (var5 < 9) {
            this.addSlotToContainer(new Slot(p_i45792_1_, var5, 8 + var5 * 18, 58 + var4));
            ++var5;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.field_94538_a.isUseableByPlayer(playerIn);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
        ItemStack var3 = null;
        Slot var4 = (Slot)this.inventorySlots.get(index);
        if (var4 != null && var4.getHasStack()) {
            ItemStack var5 = var4.getStack();
            var3 = var5.copy();
            if (index < this.field_94538_a.getSizeInventory() ? !this.mergeItemStack(var5, this.field_94538_a.getSizeInventory(), this.inventorySlots.size(), true) : !this.mergeItemStack(var5, 0, this.field_94538_a.getSizeInventory(), false)) {
                return null;
            }
            if (var5.stackSize == 0) {
                var4.putStack(null);
            } else {
                var4.onSlotChanged();
            }
        }
        return var3;
    }

    @Override
    public void onContainerClosed(EntityPlayer p_75134_1_) {
        super.onContainerClosed(p_75134_1_);
        this.field_94538_a.closeInventory(p_75134_1_);
    }
}

