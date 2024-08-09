/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class AbstractRepairContainer
extends Container {
    protected final CraftResultInventory field_234642_c_ = new CraftResultInventory();
    protected final IInventory field_234643_d_ = new Inventory(this, 2){
        final AbstractRepairContainer this$0;
        {
            this.this$0 = abstractRepairContainer;
            super(n);
        }

        @Override
        public void markDirty() {
            super.markDirty();
            this.this$0.onCraftMatrixChanged(this);
        }
    };
    protected final IWorldPosCallable field_234644_e_;
    protected final PlayerEntity field_234645_f_;

    protected abstract boolean func_230303_b_(PlayerEntity var1, boolean var2);

    protected abstract ItemStack func_230301_a_(PlayerEntity var1, ItemStack var2);

    protected abstract boolean func_230302_a_(BlockState var1);

    public AbstractRepairContainer(@Nullable ContainerType<?> containerType, int n, PlayerInventory playerInventory, IWorldPosCallable iWorldPosCallable) {
        super(containerType, n);
        int n2;
        this.field_234644_e_ = iWorldPosCallable;
        this.field_234645_f_ = playerInventory.player;
        this.addSlot(new Slot(this.field_234643_d_, 0, 27, 47));
        this.addSlot(new Slot(this.field_234643_d_, 1, 76, 47));
        this.addSlot(new Slot(this, this.field_234642_c_, 2, 134, 47){
            final AbstractRepairContainer this$0;
            {
                this.this$0 = abstractRepairContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return true;
            }

            @Override
            public boolean canTakeStack(PlayerEntity playerEntity) {
                return this.this$0.func_230303_b_(playerEntity, this.getHasStack());
            }

            @Override
            public ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
                return this.this$0.func_230301_a_(playerEntity, itemStack);
            }
        });
        for (n2 = 0; n2 < 3; ++n2) {
            for (int i = 0; i < 9; ++i) {
                this.addSlot(new Slot(playerInventory, i + n2 * 9 + 9, 8 + i * 18, 84 + n2 * 18));
            }
        }
        for (n2 = 0; n2 < 9; ++n2) {
            this.addSlot(new Slot(playerInventory, n2, 8 + n2 * 18, 142));
        }
    }

    public abstract void updateRepairOutput();

    @Override
    public void onCraftMatrixChanged(IInventory iInventory) {
        super.onCraftMatrixChanged(iInventory);
        if (iInventory == this.field_234643_d_) {
            this.updateRepairOutput();
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.field_234644_e_.consume((arg_0, arg_1) -> this.lambda$onContainerClosed$0(playerEntity, arg_0, arg_1));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return this.field_234644_e_.applyOrElse((arg_0, arg_1) -> this.lambda$canInteractWith$1(playerEntity, arg_0, arg_1), true);
    }

    protected boolean func_241210_a_(ItemStack itemStack) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (n == 2) {
                if (!this.mergeItemStack(itemStack2, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack2, itemStack);
            } else if (n != 0 && n != 1) {
                if (n >= 3 && n < 39) {
                    int n2;
                    int n3 = n2 = this.func_241210_a_(itemStack) ? 1 : 0;
                    if (!this.mergeItemStack(itemStack2, n2, 2, true)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.mergeItemStack(itemStack2, 3, 39, true)) {
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

    private Boolean lambda$canInteractWith$1(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        return !this.func_230302_a_(world.getBlockState(blockPos)) ? false : playerEntity.getDistanceSq((double)blockPos.getX() + 0.5, (double)blockPos.getY() + 0.5, (double)blockPos.getZ() + 0.5) <= 64.0;
    }

    private void lambda$onContainerClosed$0(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        this.clearContainer(playerEntity, world, this.field_234643_d_);
    }
}

