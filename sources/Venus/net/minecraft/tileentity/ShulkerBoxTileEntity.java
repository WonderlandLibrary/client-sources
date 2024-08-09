/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import java.util.List;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ShulkerBoxContainer;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class ShulkerBoxTileEntity
extends LockableLootTileEntity
implements ISidedInventory,
ITickableTileEntity {
    private static final int[] SLOTS = IntStream.range(0, 27).toArray();
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private int openCount;
    private AnimationStatus animationStatus = AnimationStatus.CLOSED;
    private float progress;
    private float progressOld;
    @Nullable
    private DyeColor color;
    private boolean needsColorFromWorld;

    public ShulkerBoxTileEntity(@Nullable DyeColor dyeColor) {
        super(TileEntityType.SHULKER_BOX);
        this.color = dyeColor;
    }

    public ShulkerBoxTileEntity() {
        this((DyeColor)null);
        this.needsColorFromWorld = true;
    }

    @Override
    public void tick() {
        this.updateAnimation();
        if (this.animationStatus == AnimationStatus.OPENING || this.animationStatus == AnimationStatus.CLOSING) {
            this.moveCollidedEntities();
        }
    }

    protected void updateAnimation() {
        this.progressOld = this.progress;
        switch (this.animationStatus) {
            case CLOSED: {
                this.progress = 0.0f;
                break;
            }
            case OPENING: {
                this.progress += 0.1f;
                if (!(this.progress >= 1.0f)) break;
                this.moveCollidedEntities();
                this.animationStatus = AnimationStatus.OPENED;
                this.progress = 1.0f;
                this.func_213975_v();
                break;
            }
            case CLOSING: {
                this.progress -= 0.1f;
                if (!(this.progress <= 0.0f)) break;
                this.animationStatus = AnimationStatus.CLOSED;
                this.progress = 0.0f;
                this.func_213975_v();
                break;
            }
            case OPENED: {
                this.progress = 1.0f;
            }
        }
    }

    public AnimationStatus getAnimationStatus() {
        return this.animationStatus;
    }

    public AxisAlignedBB getBoundingBox(BlockState blockState) {
        return this.getBoundingBox(blockState.get(ShulkerBoxBlock.FACING));
    }

    public AxisAlignedBB getBoundingBox(Direction direction) {
        float f = this.getProgress(1.0f);
        return VoxelShapes.fullCube().getBoundingBox().expand(0.5f * f * (float)direction.getXOffset(), 0.5f * f * (float)direction.getYOffset(), 0.5f * f * (float)direction.getZOffset());
    }

    private AxisAlignedBB getTopBoundingBox(Direction direction) {
        Direction direction2 = direction.getOpposite();
        return this.getBoundingBox(direction).contract(direction2.getXOffset(), direction2.getYOffset(), direction2.getZOffset());
    }

    private void moveCollidedEntities() {
        Direction direction;
        AxisAlignedBB axisAlignedBB;
        List<Entity> list;
        BlockState blockState = this.world.getBlockState(this.getPos());
        if (blockState.getBlock() instanceof ShulkerBoxBlock && !(list = this.world.getEntitiesWithinAABBExcludingEntity(null, axisAlignedBB = this.getTopBoundingBox(direction = blockState.get(ShulkerBoxBlock.FACING)).offset(this.pos))).isEmpty()) {
            for (int i = 0; i < list.size(); ++i) {
                Entity entity2 = list.get(i);
                if (entity2.getPushReaction() == PushReaction.IGNORE) continue;
                double d = 0.0;
                double d2 = 0.0;
                double d3 = 0.0;
                AxisAlignedBB axisAlignedBB2 = entity2.getBoundingBox();
                switch (direction.getAxis()) {
                    case X: {
                        d = direction.getAxisDirection() == Direction.AxisDirection.POSITIVE ? axisAlignedBB.maxX - axisAlignedBB2.minX : axisAlignedBB2.maxX - axisAlignedBB.minX;
                        d += 0.01;
                        break;
                    }
                    case Y: {
                        d2 = direction.getAxisDirection() == Direction.AxisDirection.POSITIVE ? axisAlignedBB.maxY - axisAlignedBB2.minY : axisAlignedBB2.maxY - axisAlignedBB.minY;
                        d2 += 0.01;
                        break;
                    }
                    case Z: {
                        d3 = direction.getAxisDirection() == Direction.AxisDirection.POSITIVE ? axisAlignedBB.maxZ - axisAlignedBB2.minZ : axisAlignedBB2.maxZ - axisAlignedBB.minZ;
                        d3 += 0.01;
                    }
                }
                entity2.move(MoverType.SHULKER_BOX, new Vector3d(d * (double)direction.getXOffset(), d2 * (double)direction.getYOffset(), d3 * (double)direction.getZOffset()));
            }
        }
    }

    @Override
    public int getSizeInventory() {
        return this.items.size();
    }

    @Override
    public boolean receiveClientEvent(int n, int n2) {
        if (n == 1) {
            this.openCount = n2;
            if (n2 == 0) {
                this.animationStatus = AnimationStatus.CLOSING;
                this.func_213975_v();
            }
            if (n2 == 1) {
                this.animationStatus = AnimationStatus.OPENING;
                this.func_213975_v();
            }
            return false;
        }
        return super.receiveClientEvent(n, n2);
    }

    private void func_213975_v() {
        this.getBlockState().updateNeighbours(this.getWorld(), this.getPos(), 3);
    }

    @Override
    public void openInventory(PlayerEntity playerEntity) {
        if (!playerEntity.isSpectator()) {
            if (this.openCount < 0) {
                this.openCount = 0;
            }
            ++this.openCount;
            this.world.addBlockEvent(this.pos, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount == 1) {
                this.world.playSound(null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_OPEN, SoundCategory.BLOCKS, 0.5f, this.world.rand.nextFloat() * 0.1f + 0.9f);
            }
        }
    }

    @Override
    public void closeInventory(PlayerEntity playerEntity) {
        if (!playerEntity.isSpectator()) {
            --this.openCount;
            this.world.addBlockEvent(this.pos, this.getBlockState().getBlock(), 1, this.openCount);
            if (this.openCount <= 0) {
                this.world.playSound(null, this.pos, SoundEvents.BLOCK_SHULKER_BOX_CLOSE, SoundCategory.BLOCKS, 0.5f, this.world.rand.nextFloat() * 0.1f + 0.9f);
            }
        }
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.shulkerBox");
    }

    @Override
    public void read(BlockState blockState, CompoundNBT compoundNBT) {
        super.read(blockState, compoundNBT);
        this.loadFromNbt(compoundNBT);
    }

    @Override
    public CompoundNBT write(CompoundNBT compoundNBT) {
        super.write(compoundNBT);
        return this.saveToNbt(compoundNBT);
    }

    public void loadFromNbt(CompoundNBT compoundNBT) {
        this.items = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        if (!this.checkLootAndRead(compoundNBT) && compoundNBT.contains("Items", 0)) {
            ItemStackHelper.loadAllItems(compoundNBT, this.items);
        }
    }

    public CompoundNBT saveToNbt(CompoundNBT compoundNBT) {
        if (!this.checkLootAndWrite(compoundNBT)) {
            ItemStackHelper.saveAllItems(compoundNBT, this.items, false);
        }
        return compoundNBT;
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        this.items = nonNullList;
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        return SLOTS;
    }

    @Override
    public boolean canInsertItem(int n, ItemStack itemStack, @Nullable Direction direction) {
        return !(Block.getBlockFromItem(itemStack.getItem()) instanceof ShulkerBoxBlock);
    }

    @Override
    public boolean canExtractItem(int n, ItemStack itemStack, Direction direction) {
        return false;
    }

    public float getProgress(float f) {
        return MathHelper.lerp(f, this.progressOld, this.progress);
    }

    @Nullable
    public DyeColor getColor() {
        if (this.needsColorFromWorld) {
            this.color = ShulkerBoxBlock.getColorFromBlock(this.getBlockState().getBlock());
            this.needsColorFromWorld = false;
        }
        return this.color;
    }

    @Override
    protected Container createMenu(int n, PlayerInventory playerInventory) {
        return new ShulkerBoxContainer(n, playerInventory, this);
    }

    public boolean func_235676_l_() {
        return this.animationStatus == AnimationStatus.CLOSED;
    }

    public static enum AnimationStatus {
        CLOSED,
        OPENING,
        OPENED,
        CLOSING;

    }
}

