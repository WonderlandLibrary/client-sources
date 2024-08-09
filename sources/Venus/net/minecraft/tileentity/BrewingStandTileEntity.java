/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.BrewingStandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.BrewingStandContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.PotionBrewing;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BrewingStandTileEntity
extends LockableTileEntity
implements ISidedInventory,
ITickableTileEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{3};
    private static final int[] SLOTS_FOR_DOWN = new int[]{0, 1, 2, 3};
    private static final int[] OUTPUT_SLOTS = new int[]{0, 1, 2, 4};
    private NonNullList<ItemStack> brewingItemStacks = NonNullList.withSize(5, ItemStack.EMPTY);
    private int brewTime;
    private boolean[] filledSlots;
    private Item ingredientID;
    private int fuel;
    protected final IIntArray field_213954_a = new IIntArray(this){
        final BrewingStandTileEntity this$0;
        {
            this.this$0 = brewingStandTileEntity;
        }

        @Override
        public int get(int n) {
            switch (n) {
                case 0: {
                    return this.this$0.brewTime;
                }
                case 1: {
                    return this.this$0.fuel;
                }
            }
            return 1;
        }

        @Override
        public void set(int n, int n2) {
            switch (n) {
                case 0: {
                    this.this$0.brewTime = n2;
                    break;
                }
                case 1: {
                    this.this$0.fuel = n2;
                }
            }
        }

        @Override
        public int size() {
            return 1;
        }
    };

    public BrewingStandTileEntity() {
        super(TileEntityType.BREWING_STAND);
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.brewing");
    }

    @Override
    public int getSizeInventory() {
        return this.brewingItemStacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.brewingItemStacks) {
            if (itemStack.isEmpty()) continue;
            return true;
        }
        return false;
    }

    @Override
    public void tick() {
        boolean[] blArray;
        ItemStack itemStack = this.brewingItemStacks.get(4);
        if (this.fuel <= 0 && itemStack.getItem() == Items.BLAZE_POWDER) {
            this.fuel = 20;
            itemStack.shrink(1);
            this.markDirty();
        }
        boolean bl = this.canBrew();
        boolean bl2 = this.brewTime > 0;
        ItemStack itemStack2 = this.brewingItemStacks.get(3);
        if (bl2) {
            boolean bl3;
            --this.brewTime;
            boolean bl4 = bl3 = this.brewTime == 0;
            if (bl3 && bl) {
                this.brewPotions();
                this.markDirty();
            } else if (!bl) {
                this.brewTime = 0;
                this.markDirty();
            } else if (this.ingredientID != itemStack2.getItem()) {
                this.brewTime = 0;
                this.markDirty();
            }
        } else if (bl && this.fuel > 0) {
            --this.fuel;
            this.brewTime = 400;
            this.ingredientID = itemStack2.getItem();
            this.markDirty();
        }
        if (!this.world.isRemote && !Arrays.equals(blArray = this.createFilledSlotsArray(), this.filledSlots)) {
            this.filledSlots = blArray;
            BlockState blockState = this.world.getBlockState(this.getPos());
            if (!(blockState.getBlock() instanceof BrewingStandBlock)) {
                return;
            }
            for (int i = 0; i < BrewingStandBlock.HAS_BOTTLE.length; ++i) {
                blockState = (BlockState)blockState.with(BrewingStandBlock.HAS_BOTTLE[i], blArray[i]);
            }
            this.world.setBlockState(this.pos, blockState, 1);
        }
    }

    public boolean[] createFilledSlotsArray() {
        boolean[] blArray = new boolean[3];
        for (int i = 0; i < 3; ++i) {
            if (this.brewingItemStacks.get(i).isEmpty()) continue;
            blArray[i] = true;
        }
        return blArray;
    }

    private boolean canBrew() {
        ItemStack itemStack = this.brewingItemStacks.get(3);
        if (itemStack.isEmpty()) {
            return true;
        }
        if (!PotionBrewing.isReagent(itemStack)) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            ItemStack itemStack2 = this.brewingItemStacks.get(i);
            if (itemStack2.isEmpty() || !PotionBrewing.hasConversions(itemStack2, itemStack)) continue;
            return false;
        }
        return true;
    }

    private void brewPotions() {
        ItemStack itemStack = this.brewingItemStacks.get(3);
        for (int i = 0; i < 3; ++i) {
            this.brewingItemStacks.set(i, PotionBrewing.doReaction(itemStack, this.brewingItemStacks.get(i)));
        }
        itemStack.shrink(1);
        BlockPos blockPos = this.getPos();
        if (itemStack.getItem().hasContainerItem()) {
            ItemStack itemStack2 = new ItemStack(itemStack.getItem().getContainerItem());
            if (itemStack.isEmpty()) {
                itemStack = itemStack2;
            } else if (!this.world.isRemote) {
                InventoryHelper.spawnItemStack(this.world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack2);
            }
        }
        this.brewingItemStacks.set(3, itemStack);
        this.world.playEvent(1035, blockPos, 0);
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.brewingItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compoundNBT, this.brewingItemStacks);
        this.brewTime = compoundNBT.getShort("BrewTime");
        this.fuel = compoundNBT.getByte("Fuel");
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        compoundNBT.putShort("BrewTime", (short)this.brewTime);
        ItemStackHelper.saveAllItems(compoundNBT, this.brewingItemStacks);
        compoundNBT.putByte("Fuel", (byte)this.fuel);
        return compoundNBT;
    }

    @Override
    public ItemStack getStackInSlot(int n) {
        return n >= 0 && n < this.brewingItemStacks.size() ? this.brewingItemStacks.get(n) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int n, int n2) {
        return ItemStackHelper.getAndSplit(this.brewingItemStacks, n, n2);
    }

    @Override
    public ItemStack removeStackFromSlot(int n) {
        return ItemStackHelper.getAndRemove(this.brewingItemStacks, n);
    }

    @Override
    public void setInventorySlotContents(int n, ItemStack itemStack) {
        if (n >= 0 && n < this.brewingItemStacks.size()) {
            this.brewingItemStacks.set(n, itemStack);
        }
    }

    @Override
    public boolean isUsableByPlayer(PlayerEntity playerEntity) {
        if (this.world.getTileEntity(this.pos) != this) {
            return true;
        }
        return !(playerEntity.getDistanceSq((double)this.pos.getX() + 0.5, (double)this.pos.getY() + 0.5, (double)this.pos.getZ() + 0.5) > 64.0);
    }

    @Override
    public boolean isItemValidForSlot(int n, ItemStack itemStack) {
        if (n == 3) {
            return PotionBrewing.isReagent(itemStack);
        }
        Item item = itemStack.getItem();
        if (n == 4) {
            return item == Items.BLAZE_POWDER;
        }
        return (item == Items.POTION || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE) && this.getStackInSlot(n).isEmpty();
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        if (direction == Direction.UP) {
            return SLOTS_FOR_UP;
        }
        return direction == Direction.DOWN ? SLOTS_FOR_DOWN : OUTPUT_SLOTS;
    }

    @Override
    public boolean canInsertItem(int n, ItemStack itemStack, @Nullable Direction direction) {
        return this.isItemValidForSlot(n, itemStack);
    }

    @Override
    public boolean canExtractItem(int n, ItemStack itemStack, Direction direction) {
        if (n == 3) {
            return itemStack.getItem() == Items.GLASS_BOTTLE;
        }
        return false;
    }

    @Override
    public void clear() {
        this.brewingItemStacks.clear();
    }

    @Override
    protected Container createMenu(int n, PlayerInventory playerInventory) {
        return new BrewingStandContainer(n, playerInventory, this, this.field_213954_a);
    }
}

