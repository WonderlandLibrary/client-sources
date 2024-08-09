/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.entity.passive.horse.AbstractChestedHorseEntity;
import net.minecraft.entity.passive.horse.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class HorseInventoryContainer
extends Container {
    private final IInventory horseInventory;
    private final AbstractHorseEntity horse;

    public HorseInventoryContainer(int n, PlayerInventory playerInventory, IInventory iInventory, AbstractHorseEntity abstractHorseEntity) {
        super(null, n);
        int n2;
        int n3;
        this.horseInventory = iInventory;
        this.horse = abstractHorseEntity;
        int n4 = 3;
        iInventory.openInventory(playerInventory.player);
        int n5 = -18;
        this.addSlot(new Slot(this, iInventory, 0, 8, 18, abstractHorseEntity){
            final AbstractHorseEntity val$horse;
            final HorseInventoryContainer this$0;
            {
                this.this$0 = horseInventoryContainer;
                this.val$horse = abstractHorseEntity;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return itemStack.getItem() == Items.SADDLE && !this.getHasStack() && this.val$horse.func_230264_L__();
            }

            @Override
            public boolean isEnabled() {
                return this.val$horse.func_230264_L__();
            }
        });
        this.addSlot(new Slot(this, iInventory, 1, 8, 36, abstractHorseEntity){
            final AbstractHorseEntity val$horse;
            final HorseInventoryContainer this$0;
            {
                this.this$0 = horseInventoryContainer;
                this.val$horse = abstractHorseEntity;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return this.val$horse.isArmor(itemStack);
            }

            @Override
            public boolean isEnabled() {
                return this.val$horse.func_230276_fq_();
            }

            @Override
            public int getSlotStackLimit() {
                return 0;
            }
        });
        if (abstractHorseEntity instanceof AbstractChestedHorseEntity && ((AbstractChestedHorseEntity)abstractHorseEntity).hasChest()) {
            for (n3 = 0; n3 < 3; ++n3) {
                for (n2 = 0; n2 < ((AbstractChestedHorseEntity)abstractHorseEntity).getInventoryColumns(); ++n2) {
                    this.addSlot(new Slot(iInventory, 2 + n2 + n3 * ((AbstractChestedHorseEntity)abstractHorseEntity).getInventoryColumns(), 80 + n2 * 18, 18 + n3 * 18));
                }
            }
        }
        for (n3 = 0; n3 < 3; ++n3) {
            for (n2 = 0; n2 < 9; ++n2) {
                this.addSlot(new Slot(playerInventory, n2 + n3 * 9 + 9, 8 + n2 * 18, 102 + n3 * 18 + -18));
            }
        }
        for (n3 = 0; n3 < 9; ++n3) {
            this.addSlot(new Slot(playerInventory, n3, 8 + n3 * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.horseInventory.isUsableByPlayer(playerEntity) && this.horse.isAlive() && this.horse.getDistance(playerEntity) < 8.0f;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            int n2 = this.horseInventory.getSizeInventory();
            if (n < n2) {
                if (!this.mergeItemStack(itemStack2, n2, this.inventorySlots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(1).isItemValid(itemStack2) && !this.getSlot(1).getHasStack()) {
                if (!this.mergeItemStack(itemStack2, 1, 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (this.getSlot(0).isItemValid(itemStack2)) {
                if (!this.mergeItemStack(itemStack2, 0, 1, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (n2 <= 2 || !this.mergeItemStack(itemStack2, 2, n2, true)) {
                int n3 = n2 + 27;
                int n4 = n3 + 9;
                if (n >= n3 && n < n4 ? !this.mergeItemStack(itemStack2, n2, n3, true) : (n >= n2 && n < n3 ? !this.mergeItemStack(itemStack2, n3, n4, true) : !this.mergeItemStack(itemStack2, n3, n3, true))) {
                    return ItemStack.EMPTY;
                }
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
        this.horseInventory.closeInventory(playerEntity);
    }
}

