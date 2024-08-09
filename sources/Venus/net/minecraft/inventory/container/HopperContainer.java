/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class HopperContainer
extends Container {
    private final IInventory hopperInventory;

    public HopperContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, new Inventory(5));
    }

    public HopperContainer(int n, PlayerInventory playerInventory, IInventory iInventory) {
        super(ContainerType.HOPPER, n);
        int n2;
        this.hopperInventory = iInventory;
        HopperContainer.assertInventorySize(iInventory, 5);
        iInventory.openInventory(playerInventory.player);
        int n3 = 51;
        for (n2 = 0; n2 < 5; ++n2) {
            this.addSlot(new Slot(iInventory, n2, 44 + n2 * 18, 20));
        }
        for (n2 = 0; n2 < 3; ++n2) {
            for (int i = 0; i < 9; ++i) {
                this.addSlot(new Slot(playerInventory, i + n2 * 9 + 9, 8 + i * 18, n2 * 18 + 51));
            }
        }
        for (n2 = 0; n2 < 9; ++n2) {
            this.addSlot(new Slot(playerInventory, n2, 8 + n2 * 18, 109));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.hopperInventory.isUsableByPlayer(playerEntity);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (n < this.hopperInventory.getSizeInventory() ? !this.mergeItemStack(itemStack2, this.hopperInventory.getSizeInventory(), this.inventorySlots.size(), false) : !this.mergeItemStack(itemStack2, 0, this.hopperInventory.getSizeInventory(), true)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemStack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.hopperInventory.closeInventory(playerEntity);
    }
}

