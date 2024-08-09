/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class LecternContainer
extends Container {
    private final IInventory lecternInventory;
    private final IIntArray field_217019_d;

    public LecternContainer(int n) {
        this(n, new Inventory(1), new IntArray(1));
    }

    public LecternContainer(int n, IInventory iInventory, IIntArray iIntArray) {
        super(ContainerType.LECTERN, n);
        LecternContainer.assertInventorySize(iInventory, 1);
        LecternContainer.assertIntArraySize(iIntArray, 1);
        this.lecternInventory = iInventory;
        this.field_217019_d = iIntArray;
        this.addSlot(new Slot(this, iInventory, 0, 0, 0){
            final LecternContainer this$0;
            {
                this.this$0 = lecternContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public void onSlotChanged() {
                super.onSlotChanged();
                this.this$0.onCraftMatrixChanged(this.inventory);
            }
        });
        this.trackIntArray(iIntArray);
    }

    @Override
    public boolean enchantItem(PlayerEntity playerEntity, int n) {
        if (n >= 100) {
            int n2 = n - 100;
            this.updateProgressBar(0, n2);
            return false;
        }
        switch (n) {
            case 1: {
                int n3 = this.field_217019_d.get(0);
                this.updateProgressBar(0, n3 - 1);
                return false;
            }
            case 2: {
                int n4 = this.field_217019_d.get(0);
                this.updateProgressBar(0, n4 + 1);
                return false;
            }
            case 3: {
                if (!playerEntity.isAllowEdit()) {
                    return true;
                }
                ItemStack itemStack = this.lecternInventory.removeStackFromSlot(0);
                this.lecternInventory.markDirty();
                if (!playerEntity.inventory.addItemStackToInventory(itemStack)) {
                    playerEntity.dropItem(itemStack, true);
                }
                return false;
            }
        }
        return true;
    }

    @Override
    public void updateProgressBar(int n, int n2) {
        super.updateProgressBar(n, n2);
        this.detectAndSendChanges();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.lecternInventory.isUsableByPlayer(playerEntity);
    }

    public ItemStack getBook() {
        return this.lecternInventory.getStackInSlot(0);
    }

    public int getPage() {
        return this.field_217019_d.get(0);
    }
}

