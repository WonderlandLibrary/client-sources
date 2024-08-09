/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class CartographyContainer
extends Container {
    private final IWorldPosCallable worldPosCallable;
    private long field_226605_f_;
    public final IInventory tableInventory = new Inventory(this, 2){
        final CartographyContainer this$0;
        {
            this.this$0 = cartographyContainer;
            super(n);
        }

        @Override
        public void markDirty() {
            this.this$0.onCraftMatrixChanged(this);
            super.markDirty();
        }
    };
    private final CraftResultInventory field_217001_f = new CraftResultInventory(this){
        final CartographyContainer this$0;
        {
            this.this$0 = cartographyContainer;
        }

        @Override
        public void markDirty() {
            this.this$0.onCraftMatrixChanged(this);
            super.markDirty();
        }
    };

    public CartographyContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, IWorldPosCallable.DUMMY);
    }

    public CartographyContainer(int n, PlayerInventory playerInventory, IWorldPosCallable iWorldPosCallable) {
        super(ContainerType.CARTOGRAPHY_TABLE, n);
        int n2;
        this.worldPosCallable = iWorldPosCallable;
        this.addSlot(new Slot(this, this.tableInventory, 0, 15, 15){
            final CartographyContainer this$0;
            {
                this.this$0 = cartographyContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return itemStack.getItem() == Items.FILLED_MAP;
            }
        });
        this.addSlot(new Slot(this, this.tableInventory, 1, 15, 52){
            final CartographyContainer this$0;
            {
                this.this$0 = cartographyContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                Item item = itemStack.getItem();
                return item == Items.PAPER || item == Items.MAP || item == Items.GLASS_PANE;
            }
        });
        this.addSlot(new Slot(this, this.field_217001_f, 2, 145, 39, iWorldPosCallable){
            final IWorldPosCallable val$worldPosCallable;
            final CartographyContainer this$0;
            {
                this.this$0 = cartographyContainer;
                this.val$worldPosCallable = iWorldPosCallable;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return true;
            }

            @Override
            public ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
                ((Slot)this.this$0.inventorySlots.get(0)).decrStackSize(1);
                ((Slot)this.this$0.inventorySlots.get(1)).decrStackSize(1);
                itemStack.getItem().onCreated(itemStack, playerEntity.world, playerEntity);
                this.val$worldPosCallable.consume(this::lambda$onTake$0);
                return super.onTake(playerEntity, itemStack);
            }

            private void lambda$onTake$0(World world, BlockPos blockPos) {
                long l = world.getGameTime();
                if (this.this$0.field_226605_f_ != l) {
                    world.playSound(null, blockPos, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    this.this$0.field_226605_f_ = l;
                }
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

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return CartographyContainer.isWithinUsableDistance(this.worldPosCallable, playerEntity, Blocks.CARTOGRAPHY_TABLE);
    }

    @Override
    public void onCraftMatrixChanged(IInventory iInventory) {
        ItemStack itemStack = this.tableInventory.getStackInSlot(0);
        ItemStack itemStack2 = this.tableInventory.getStackInSlot(1);
        ItemStack itemStack3 = this.field_217001_f.getStackInSlot(2);
        if (itemStack3.isEmpty() || !itemStack.isEmpty() && !itemStack2.isEmpty()) {
            if (!itemStack.isEmpty() && !itemStack2.isEmpty()) {
                this.func_216993_a(itemStack, itemStack2, itemStack3);
            }
        } else {
            this.field_217001_f.removeStackFromSlot(2);
        }
    }

    private void func_216993_a(ItemStack itemStack, ItemStack itemStack2, ItemStack itemStack3) {
        this.worldPosCallable.consume((arg_0, arg_1) -> this.lambda$func_216993_a$0(itemStack2, itemStack, itemStack3, arg_0, arg_1));
    }

    @Override
    public boolean canMergeSlot(ItemStack itemStack, Slot slot) {
        return slot.inventory != this.field_217001_f && super.canMergeSlot(itemStack, slot);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            Item item = itemStack2.getItem();
            itemStack = itemStack2.copy();
            if (n == 2) {
                item.onCreated(itemStack2, playerEntity.world, playerEntity);
                if (!this.mergeItemStack(itemStack2, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack2, itemStack);
            } else if (n != 1 && n != 0 ? (item == Items.FILLED_MAP ? !this.mergeItemStack(itemStack2, 0, 1, true) : (item != Items.PAPER && item != Items.MAP && item != Items.GLASS_PANE ? (n >= 3 && n < 30 ? !this.mergeItemStack(itemStack2, 30, 39, true) : n >= 30 && n < 39 && !this.mergeItemStack(itemStack2, 3, 30, true)) : !this.mergeItemStack(itemStack2, 1, 2, true))) : !this.mergeItemStack(itemStack2, 3, 39, true)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            }
            slot.onSlotChanged();
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(playerEntity, itemStack2);
            this.detectAndSendChanges();
        }
        return itemStack;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerEntity) {
        super.onContainerClosed(playerEntity);
        this.field_217001_f.removeStackFromSlot(2);
        this.worldPosCallable.consume((arg_0, arg_1) -> this.lambda$onContainerClosed$1(playerEntity, arg_0, arg_1));
    }

    private void lambda$onContainerClosed$1(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        this.clearContainer(playerEntity, playerEntity.world, this.tableInventory);
    }

    private void lambda$func_216993_a$0(ItemStack itemStack, ItemStack itemStack2, ItemStack itemStack3, World world, BlockPos blockPos) {
        Item item = itemStack.getItem();
        MapData mapData = FilledMapItem.getData(itemStack2, world);
        if (mapData != null) {
            ItemStack itemStack4;
            if (item == Items.PAPER && !mapData.locked && mapData.scale < 4) {
                itemStack4 = itemStack2.copy();
                itemStack4.setCount(1);
                itemStack4.getOrCreateTag().putInt("map_scale_direction", 1);
                this.detectAndSendChanges();
            } else if (item == Items.GLASS_PANE && !mapData.locked) {
                itemStack4 = itemStack2.copy();
                itemStack4.setCount(1);
                itemStack4.getOrCreateTag().putBoolean("map_to_lock", false);
                this.detectAndSendChanges();
            } else {
                if (item != Items.MAP) {
                    this.field_217001_f.removeStackFromSlot(2);
                    this.detectAndSendChanges();
                    return;
                }
                itemStack4 = itemStack2.copy();
                itemStack4.setCount(2);
                this.detectAndSendChanges();
            }
            if (!ItemStack.areItemStacksEqual(itemStack4, itemStack3)) {
                this.field_217001_f.setInventorySlotContents(2, itemStack4);
                this.detectAndSendChanges();
            }
        }
    }
}

