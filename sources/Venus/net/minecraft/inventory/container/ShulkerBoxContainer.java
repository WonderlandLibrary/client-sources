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
import net.minecraft.inventory.container.ShulkerBoxSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ShulkerBoxContainer
extends Container {
    private final IInventory inventory;

    public ShulkerBoxContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, new Inventory(27));
    }

    public ShulkerBoxContainer(int n, PlayerInventory playerInventory, IInventory iInventory) {
        super(ContainerType.SHULKER_BOX, n);
        int n2;
        int n3;
        ShulkerBoxContainer.assertInventorySize(iInventory, 27);
        this.inventory = iInventory;
        iInventory.openInventory(playerInventory.player);
        int n4 = 3;
        int n5 = 9;
        for (n3 = 0; n3 < 3; ++n3) {
            for (n2 = 0; n2 < 9; ++n2) {
                this.addSlot(new ShulkerBoxSlot(iInventory, n2 + n3 * 9, 8 + n2 * 18, 18 + n3 * 18));
            }
        }
        for (n3 = 0; n3 < 3; ++n3) {
            for (n2 = 0; n2 < 9; ++n2) {
                this.addSlot(new Slot(playerInventory, n2 + n3 * 9 + 9, 8 + n2 * 18, 84 + n3 * 18));
            }
        }
        for (n3 = 0; n3 < 9; ++n3) {
            this.addSlot(new Slot(playerInventory, n3, 8 + n3 * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.inventory.isUsableByPlayer(playerEntity);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (n < this.inventory.getSizeInventory() ? !this.mergeItemStack(itemStack2, this.inventory.getSizeInventory(), this.inventorySlots.size(), false) : !this.mergeItemStack(itemStack2, 0, this.inventory.getSizeInventory(), true)) {
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
        this.inventory.closeInventory(playerEntity);
    }
}

