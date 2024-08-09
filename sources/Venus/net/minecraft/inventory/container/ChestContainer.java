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

public class ChestContainer
extends Container {
    private final IInventory lowerChestInventory;
    private final int numRows;

    private ChestContainer(ContainerType<?> containerType, int n, PlayerInventory playerInventory, int n2) {
        this(containerType, n, playerInventory, new Inventory(9 * n2), n2);
    }

    public static ChestContainer createGeneric9X1(int n, PlayerInventory playerInventory) {
        return new ChestContainer(ContainerType.GENERIC_9X1, n, playerInventory, 1);
    }

    public static ChestContainer createGeneric9X2(int n, PlayerInventory playerInventory) {
        return new ChestContainer(ContainerType.GENERIC_9X2, n, playerInventory, 2);
    }

    public static ChestContainer createGeneric9X3(int n, PlayerInventory playerInventory) {
        return new ChestContainer(ContainerType.GENERIC_9X3, n, playerInventory, 3);
    }

    public static ChestContainer createGeneric9X4(int n, PlayerInventory playerInventory) {
        return new ChestContainer(ContainerType.GENERIC_9X4, n, playerInventory, 4);
    }

    public static ChestContainer createGeneric9X5(int n, PlayerInventory playerInventory) {
        return new ChestContainer(ContainerType.GENERIC_9X5, n, playerInventory, 5);
    }

    public static ChestContainer createGeneric9X6(int n, PlayerInventory playerInventory) {
        return new ChestContainer(ContainerType.GENERIC_9X6, n, playerInventory, 6);
    }

    public static ChestContainer createGeneric9X3(int n, PlayerInventory playerInventory, IInventory iInventory) {
        return new ChestContainer(ContainerType.GENERIC_9X3, n, playerInventory, iInventory, 3);
    }

    public static ChestContainer createGeneric9X6(int n, PlayerInventory playerInventory, IInventory iInventory) {
        return new ChestContainer(ContainerType.GENERIC_9X6, n, playerInventory, iInventory, 6);
    }

    public ChestContainer(ContainerType<?> containerType, int n, PlayerInventory playerInventory, IInventory iInventory, int n2) {
        super(containerType, n);
        int n3;
        int n4;
        ChestContainer.assertInventorySize(iInventory, n2 * 9);
        this.lowerChestInventory = iInventory;
        this.numRows = n2;
        iInventory.openInventory(playerInventory.player);
        int n5 = (this.numRows - 4) * 18;
        for (n4 = 0; n4 < this.numRows; ++n4) {
            for (n3 = 0; n3 < 9; ++n3) {
                this.addSlot(new Slot(iInventory, n3 + n4 * 9, 8 + n3 * 18, 18 + n4 * 18));
            }
        }
        for (n4 = 0; n4 < 3; ++n4) {
            for (n3 = 0; n3 < 9; ++n3) {
                this.addSlot(new Slot(playerInventory, n3 + n4 * 9 + 9, 8 + n3 * 18, 103 + n4 * 18 + n5));
            }
        }
        for (n4 = 0; n4 < 9; ++n4) {
            this.addSlot(new Slot(playerInventory, n4, 8 + n4 * 18, 161 + n5));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.lowerChestInventory.isUsableByPlayer(playerEntity);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (n < this.numRows * 9 ? !this.mergeItemStack(itemStack2, this.numRows * 9, this.inventorySlots.size(), false) : !this.mergeItemStack(itemStack2, 0, this.numRows * 9, true)) {
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
        this.lowerChestInventory.closeInventory(playerEntity);
    }

    public IInventory getLowerChestInventory() {
        return this.lowerChestInventory;
    }

    public int getNumRows() {
        return this.numRows;
    }
}

