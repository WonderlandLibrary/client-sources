/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import net.minecraft.block.BarrelBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class BarrelTileEntity
extends LockableLootTileEntity {
    private NonNullList<ItemStack> barrelContents = NonNullList.withSize(27, ItemStack.EMPTY);
    private int numPlayersUsing;

    private BarrelTileEntity(TileEntityType<?> tileEntityType) {
        super(tileEntityType);
    }

    public BarrelTileEntity() {
        this(TileEntityType.BARREL);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        if (!this.checkLootAndWrite(compoundNBT)) {
            ItemStackHelper.saveAllItems(compoundNBT, this.barrelContents);
        }
        return compoundNBT;
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.barrelContents = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compoundNBT)) {
            ItemStackHelper.loadAllItems(compoundNBT, this.barrelContents);
        }
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.barrelContents;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.barrelContents = nonNullList;
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.barrel");
    }

    @Override
    protected Container createMenu(int n, PlayerInventory playerInventory) {
        return ChestContainer.createGeneric9X3(n, playerInventory, this);
    }

    @Override
    public void openInventory(PlayerEntity playerEntity) {
        if (!playerEntity.isSpectator()) {
            if (this.numPlayersUsing < 0) {
                this.numPlayersUsing = 0;
            }
            ++this.numPlayersUsing;
            BlockState blockState = this.getBlockState();
            boolean bl = blockState.get(BarrelBlock.PROPERTY_OPEN);
            if (!bl) {
                this.playSound(blockState, SoundEvents.BLOCK_BARREL_OPEN);
                this.setOpenProperty(blockState, false);
            }
            this.scheduleTick();
        }
    }

    private void scheduleTick() {
        this.world.getPendingBlockTicks().scheduleTick(this.getPos(), this.getBlockState().getBlock(), 5);
    }

    public void barrelTick() {
        int n = this.pos.getX();
        int n2 = this.pos.getY();
        int n3 = this.pos.getZ();
        this.numPlayersUsing = ChestTileEntity.calculatePlayersUsing(this.world, this, n, n2, n3);
        if (this.numPlayersUsing > 0) {
            this.scheduleTick();
        } else {
            BlockState blockState = this.getBlockState();
            if (!blockState.isIn(Blocks.BARREL)) {
                this.remove();
                return;
            }
            boolean bl = blockState.get(BarrelBlock.PROPERTY_OPEN);
            if (bl) {
                this.playSound(blockState, SoundEvents.BLOCK_BARREL_CLOSE);
                this.setOpenProperty(blockState, true);
            }
        }
    }

    @Override
    public void closeInventory(PlayerEntity playerEntity) {
        if (!playerEntity.isSpectator()) {
            --this.numPlayersUsing;
        }
    }

    private void setOpenProperty(BlockState blockState, boolean bl) {
        this.world.setBlockState(this.getPos(), (BlockState)blockState.with(BarrelBlock.PROPERTY_OPEN, bl), 0);
    }

    private void playSound(BlockState blockState, SoundEvent soundEvent) {
        Vector3i vector3i = blockState.get(BarrelBlock.PROPERTY_FACING).getDirectionVec();
        double d = (double)this.pos.getX() + 0.5 + (double)vector3i.getX() / 2.0;
        double d2 = (double)this.pos.getY() + 0.5 + (double)vector3i.getY() / 2.0;
        double d3 = (double)this.pos.getZ() + 0.5 + (double)vector3i.getZ() / 2.0;
        this.world.playSound(null, d, d2, d3, soundEvent, SoundCategory.BLOCKS, 0.5f, this.world.rand.nextFloat() * 0.1f + 0.9f);
    }
}

