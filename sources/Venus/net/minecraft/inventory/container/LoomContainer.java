/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory.container;

import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BannerItem;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LoomContainer
extends Container {
    private final IWorldPosCallable worldPos;
    private final IntReferenceHolder field_217034_d = IntReferenceHolder.single();
    private Runnable runnable = LoomContainer::lambda$new$0;
    private final Slot slotBanner;
    private final Slot slotDye;
    private final Slot slotPattern;
    private final Slot output;
    private long field_226622_j_;
    private final IInventory inputInventory = new Inventory(this, 3){
        final LoomContainer this$0;
        {
            this.this$0 = loomContainer;
            super(n);
        }

        @Override
        public void markDirty() {
            super.markDirty();
            this.this$0.onCraftMatrixChanged(this);
            this.this$0.runnable.run();
        }
    };
    private final IInventory outputInventory = new Inventory(this, 1){
        final LoomContainer this$0;
        {
            this.this$0 = loomContainer;
            super(n);
        }

        @Override
        public void markDirty() {
            super.markDirty();
            this.this$0.runnable.run();
        }
    };

    public LoomContainer(int n, PlayerInventory playerInventory) {
        this(n, playerInventory, IWorldPosCallable.DUMMY);
    }

    public LoomContainer(int n, PlayerInventory playerInventory, IWorldPosCallable iWorldPosCallable) {
        super(ContainerType.LOOM, n);
        int n2;
        this.worldPos = iWorldPosCallable;
        this.slotBanner = this.addSlot(new Slot(this, this.inputInventory, 0, 13, 26){
            final LoomContainer this$0;
            {
                this.this$0 = loomContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return itemStack.getItem() instanceof BannerItem;
            }
        });
        this.slotDye = this.addSlot(new Slot(this, this.inputInventory, 1, 33, 26){
            final LoomContainer this$0;
            {
                this.this$0 = loomContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return itemStack.getItem() instanceof DyeItem;
            }
        });
        this.slotPattern = this.addSlot(new Slot(this, this.inputInventory, 2, 23, 45){
            final LoomContainer this$0;
            {
                this.this$0 = loomContainer;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return itemStack.getItem() instanceof BannerPatternItem;
            }
        });
        this.output = this.addSlot(new Slot(this, this.outputInventory, 0, 143, 58, iWorldPosCallable){
            final IWorldPosCallable val$worldCallable;
            final LoomContainer this$0;
            {
                this.this$0 = loomContainer;
                this.val$worldCallable = iWorldPosCallable;
                super(iInventory, n, n2, n3);
            }

            @Override
            public boolean isItemValid(ItemStack itemStack) {
                return true;
            }

            @Override
            public ItemStack onTake(PlayerEntity playerEntity, ItemStack itemStack) {
                this.this$0.slotBanner.decrStackSize(1);
                this.this$0.slotDye.decrStackSize(1);
                if (!this.this$0.slotBanner.getHasStack() || !this.this$0.slotDye.getHasStack()) {
                    this.this$0.field_217034_d.set(0);
                }
                this.val$worldCallable.consume(this::lambda$onTake$0);
                return super.onTake(playerEntity, itemStack);
            }

            private void lambda$onTake$0(World world, BlockPos blockPos) {
                long l = world.getGameTime();
                if (this.this$0.field_226622_j_ != l) {
                    world.playSound(null, blockPos, SoundEvents.UI_LOOM_TAKE_RESULT, SoundCategory.BLOCKS, 1.0f, 1.0f);
                    this.this$0.field_226622_j_ = l;
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
        this.trackInt(this.field_217034_d);
    }

    public int func_217023_e() {
        return this.field_217034_d.get();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerEntity) {
        return LoomContainer.isWithinUsableDistance(this.worldPos, playerEntity, Blocks.LOOM);
    }

    @Override
    public boolean enchantItem(PlayerEntity playerEntity, int n) {
        if (n > 0 && n <= BannerPattern.PATTERN_ITEM_INDEX) {
            this.field_217034_d.set(n);
            this.createOutputStack();
            return false;
        }
        return true;
    }

    @Override
    public void onCraftMatrixChanged(IInventory iInventory) {
        ItemStack itemStack = this.slotBanner.getStack();
        ItemStack itemStack2 = this.slotDye.getStack();
        ItemStack itemStack3 = this.slotPattern.getStack();
        ItemStack itemStack4 = this.output.getStack();
        if (itemStack4.isEmpty() || !itemStack.isEmpty() && !itemStack2.isEmpty() && this.field_217034_d.get() > 0 && (this.field_217034_d.get() < BannerPattern.BANNER_PATTERNS_COUNT - BannerPattern.BANNERS_WITH_ITEMS || !itemStack3.isEmpty())) {
            if (!itemStack3.isEmpty() && itemStack3.getItem() instanceof BannerPatternItem) {
                boolean bl;
                CompoundNBT compoundNBT = itemStack.getOrCreateChildTag("BlockEntityTag");
                boolean bl2 = bl = compoundNBT.contains("Patterns", 0) && !itemStack.isEmpty() && compoundNBT.getList("Patterns", 10).size() >= 6;
                if (bl) {
                    this.field_217034_d.set(0);
                } else {
                    this.field_217034_d.set(((BannerPatternItem)itemStack3.getItem()).getBannerPattern().ordinal());
                }
            }
        } else {
            this.output.putStack(ItemStack.EMPTY);
            this.field_217034_d.set(0);
        }
        this.createOutputStack();
        this.detectAndSendChanges();
    }

    public void func_217020_a(Runnable runnable) {
        this.runnable = runnable;
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerEntity, int n) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(n);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (n == this.output.slotNumber) {
                if (!this.mergeItemStack(itemStack2, 4, 40, false)) {
                    return ItemStack.EMPTY;
                }
                slot.onSlotChange(itemStack2, itemStack);
            } else if (n != this.slotDye.slotNumber && n != this.slotBanner.slotNumber && n != this.slotPattern.slotNumber ? (itemStack2.getItem() instanceof BannerItem ? !this.mergeItemStack(itemStack2, this.slotBanner.slotNumber, this.slotBanner.slotNumber + 1, true) : (itemStack2.getItem() instanceof DyeItem ? !this.mergeItemStack(itemStack2, this.slotDye.slotNumber, this.slotDye.slotNumber + 1, true) : (itemStack2.getItem() instanceof BannerPatternItem ? !this.mergeItemStack(itemStack2, this.slotPattern.slotNumber, this.slotPattern.slotNumber + 1, true) : (n >= 4 && n < 31 ? !this.mergeItemStack(itemStack2, 31, 40, true) : n >= 31 && n < 40 && !this.mergeItemStack(itemStack2, 4, 31, true))))) : !this.mergeItemStack(itemStack2, 4, 40, true)) {
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
        this.worldPos.consume((arg_0, arg_1) -> this.lambda$onContainerClosed$1(playerEntity, arg_0, arg_1));
    }

    private void createOutputStack() {
        if (this.field_217034_d.get() > 0) {
            ItemStack itemStack = this.slotBanner.getStack();
            ItemStack itemStack2 = this.slotDye.getStack();
            ItemStack itemStack3 = ItemStack.EMPTY;
            if (!itemStack.isEmpty() && !itemStack2.isEmpty()) {
                ListNBT listNBT;
                itemStack3 = itemStack.copy();
                itemStack3.setCount(1);
                BannerPattern bannerPattern = BannerPattern.values()[this.field_217034_d.get()];
                DyeColor dyeColor = ((DyeItem)itemStack2.getItem()).getDyeColor();
                CompoundNBT compoundNBT = itemStack3.getOrCreateChildTag("BlockEntityTag");
                if (compoundNBT.contains("Patterns", 0)) {
                    listNBT = compoundNBT.getList("Patterns", 10);
                } else {
                    listNBT = new ListNBT();
                    compoundNBT.put("Patterns", listNBT);
                }
                CompoundNBT compoundNBT2 = new CompoundNBT();
                compoundNBT2.putString("Pattern", bannerPattern.getHashname());
                compoundNBT2.putInt("Color", dyeColor.getId());
                listNBT.add(compoundNBT2);
            }
            if (!ItemStack.areItemStacksEqual(itemStack3, this.output.getStack())) {
                this.output.putStack(itemStack3);
            }
        }
    }

    public Slot getBannerSlot() {
        return this.slotBanner;
    }

    public Slot getDyeSlot() {
        return this.slotDye;
    }

    public Slot getPatternSlot() {
        return this.slotPattern;
    }

    public Slot getOutputSlot() {
        return this.output;
    }

    private void lambda$onContainerClosed$1(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        this.clearContainer(playerEntity, playerEntity.world, this.inputInventory);
    }

    private static void lambda$new$0() {
    }
}

