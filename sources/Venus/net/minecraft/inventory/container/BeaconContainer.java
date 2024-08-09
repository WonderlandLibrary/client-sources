/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import javax.annotation.Nullable;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntArray;

public class BeaconContainer
extends Container {
    private final IInventory tileBeacon = new Inventory(this, 1){
        final BeaconContainer this$0;
        {
            this.this$0 = beaconContainer;
            super(n);
        }

        @Override
        public boolean isItemValidForSlot(int n, ItemStack itemStack) {
            return itemStack.getItem().isIn(ItemTags.BEACON_PAYMENT_ITEMS);
        }

        @Override
        public int getInventoryStackLimit() {
            return 0;
        }
    };
    private final BeaconSlot beaconSlot;
    private final IWorldPosCallable worldPosCallable;
    private final IIntArray field_216972_f;

    public BeaconContainer(int n, IInventory iInventory) {
        this(n, iInventory, new IntArray(3), IWorldPosCallable.DUMMY);
    }

    public BeaconContainer(int n, IInventory iInventory, IIntArray iIntArray, IWorldPosCallable iWorldPosCallable) {
        super(ContainerType.BEACON, n);
        int n2;
        BeaconContainer.assertIntArraySize(iIntArray, 3);
        this.field_216972_f = iIntArray;
        this.worldPosCallable = iWorldPosCallable;
        this.beaconSlot = new BeaconSlot(this, this.tileBeacon, 0, 136, 110);
        this.addSlot(this.beaconSlot);
        this.trackIntArray(iIntArray);
        int n3 = 36;
        int n4 = 137;
        for (n2 = 0; n2 < 3; ++n2) {
            for (int i = 0; i < 9; ++i) {
                this.addSlot(new Slot(iInventory, i + n2 * 9 + 9, 36 + i * 18, 137 + n2 * 18));
            }
        }
        for (n2 = 0; n2 < 9; ++n2) {
            this.addSlot(new Slot(iInventory, n2, 36 + n2 * 18, 195));
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        ItemStack itemStack;
        super.onContainerClosed(playerEntity);
        if (!playerEntity.world.isRemote && !(itemStack = this.beaconSlot.decrStackSize(this.beaconSlot.getSlotStackLimit())).isEmpty()) {
            playerEntity.dropItem(itemStack, true);
        }
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return BeaconContainer.isWithinUsableDistance(this.worldPosCallable, playerEntity, Blocks.BEACON);
    }

    @Override
    public void updateProgressBar(int n, int n2) {
        super.updateProgressBar(n, n2);
        this.detectAndSendChanges();
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (n == 0) {
                if (!this.mergeItemStack(itemStack2, 1, 37, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack2, itemStack);
            } else if (!this.beaconSlot.getHasStack() && this.beaconSlot.isItemValid(itemStack2) && itemStack2.getCount() == 1 ? !this.mergeItemStack(itemStack2, 0, 1, true) : (n >= 1 && n < 28 ? !this.mergeItemStack(itemStack2, 28, 37, true) : (n >= 28 && n < 37 ? !this.mergeItemStack(itemStack2, 1, 28, true) : !this.mergeItemStack(itemStack2, 1, 37, true)))) {
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

    public int func_216969_e() {
        return this.field_216972_f.get(0);
    }

    @Nullable
    public Effect func_216967_f() {
        return Effect.get(this.field_216972_f.get(1));
    }

    @Nullable
    public Effect func_216968_g() {
        return Effect.get(this.field_216972_f.get(2));
    }

    public void func_216966_c(int n, int n2) {
        if (this.beaconSlot.getHasStack()) {
            this.field_216972_f.set(1, n);
            this.field_216972_f.set(2, n2);
            this.beaconSlot.decrStackSize(1);
        }
    }

    public boolean func_216970_h() {
        return !this.tileBeacon.getStackInSlot(0).isEmpty();
    }

    class BeaconSlot
    extends Slot {
        final BeaconContainer this$0;

        public BeaconSlot(BeaconContainer beaconContainer, IInventory iInventory, int n, int n2, int n3) {
            this.this$0 = beaconContainer;
            super(iInventory, n, n2, n3);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return itemStack.getItem().isIn(ItemTags.BEACON_PAYMENT_ITEMS);
        }

        @Override
        public int getSlotStackLimit() {
            return 0;
        }
    }
}

