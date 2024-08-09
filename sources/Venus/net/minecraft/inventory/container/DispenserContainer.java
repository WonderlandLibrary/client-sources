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

public class DispenserContainer
extends Container {
    private final IInventory dispenserInventory;

    public DispenserContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, new Inventory(9));
    }

    public DispenserContainer(int n, PlayerInventory playerInventory, IInventory iInventory) {
        super(ContainerType.GENERIC_3X3, n);
        int n2;
        int n3;
        DispenserContainer.assertInventorySize(iInventory, 9);
        this.dispenserInventory = iInventory;
        iInventory.openInventory(playerInventory.player);
        for (n3 = 0; n3 < 3; ++n3) {
            for (n2 = 0; n2 < 3; ++n2) {
                this.addSlot(new Slot(iInventory, n2 + n3 * 3, 62 + n2 * 18, 17 + n3 * 18));
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
        return this.dispenserInventory.isUsableByPlayer(playerEntity);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (n < 9 ? !this.mergeItemStack(itemStack2, 9, 45, false) : !this.mergeItemStack(itemStack2, 0, 9, true)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerEntity, itemStack2);
        }
        return itemStack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.dispenserInventory.closeInventory(playerEntity);
    }
}

