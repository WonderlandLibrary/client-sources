/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tileentity;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class TileEntityPiston
extends TileEntity
implements ITickable {
    private IBlockState pistonState;
    private EnumFacing pistonFacing;
    private boolean extending;
    private boolean shouldHeadBeRendered;
    private static final ThreadLocal<EnumFacing> field_190613_i = new ThreadLocal<EnumFacing>(){

        @Override
        protected EnumFacing initialValue() {
            return null;
        }
    };
    private float progress;
    private float lastProgress;

    public TileEntityPiston() {
    }

    public TileEntityPiston(IBlockState pistonStateIn, EnumFacing pistonFacingIn, boolean extendingIn, boolean shouldHeadBeRenderedIn) {
        this.pistonState = pistonStateIn;
        this.pistonFacing = pistonFacingIn;
        this.extending = extendingIn;
        this.shouldHeadBeRendered = shouldHeadBeRenderedIn;
    }

    public IBlockState getPistonState() {
        return this.pistonState;
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public int getBlockMetadata() {
        return 0;
    }

    public boolean isExtending() {
        return this.extending;
    }

    public EnumFacing getFacing() {
        return this.pistonFacing;
    }

    public boolean shouldPistonHeadBeRendered() {
        return this.shouldHeadBeRendered;
    }

    public float getProgress(float ticks) {
        if (ticks > 1.0f) {
            ticks = 1.0f;
        }
        return this.lastProgress + (this.progress - this.lastProgress) * ticks;
    }

    public float getOffsetX(float ticks) {
        return (float)this.pistonFacing.getFrontOffsetX() * this.getExtendedProgress(this.getProgress(ticks));
    }

    public float getOffsetY(float ticks) {
        return (float)this.pistonFacing.getFrontOffsetY() * this.getExtendedProgress(this.getProgress(ticks));
    }

    public float getOffsetZ(float ticks) {
        return (float)this.pistonFacing.getFrontOffsetZ() * this.getExtendedProgress(this.getProgress(ticks));
    }

    private float getExtendedProgress(float p_184320_1_) {
        return this.extending ? p_184320_1_ - 1.0f : 1.0f - p_184320_1_;
    }

    public AxisAlignedBB getAABB(IBlockAccess p_184321_1_, BlockPos p_184321_2_) {
        return this.getAABB(p_184321_1_, p_184321_2_, this.progress).union(this.getAABB(p_184321_1_, p_184321_2_, this.lastProgress));
    }

    public AxisAlignedBB getAABB(IBlockAccess p_184319_1_, BlockPos p_184319_2_, float p_184319_3_) {
        p_184319_3_ = this.getExtendedProgress(p_184319_3_);
        IBlockState iblockstate = this.func_190606_j();
        return iblockstate.getBoundingBox(p_184319_1_, p_184319_2_).offset(p_184319_3_ * (float)this.pistonFacing.getFrontOffsetX(), p_184319_3_ * (float)this.pistonFacing.getFrontOffsetY(), p_184319_3_ * (float)this.pistonFacing.getFrontOffsetZ());
    }

    private IBlockState func_190606_j() {
        return !this.isExtending() && this.shouldPistonHeadBeRendered() ? Blocks.PISTON_HEAD.getDefaultState().withProperty(BlockPistonExtension.TYPE, this.pistonState.getBlock() == Blocks.STICKY_PISTON ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT).withProperty(BlockPistonExtension.FACING, this.pistonState.getValue(BlockPistonBase.FACING)) : this.pistonState;
    }

    private void moveCollidedEntities(float p_184322_1_) {
        AxisAlignedBB axisalignedbb;
        List<Entity> list1;
        EnumFacing enumfacing = this.extending ? this.pistonFacing : this.pistonFacing.getOpposite();
        double d0 = p_184322_1_ - this.progress;
        ArrayList<AxisAlignedBB> list = Lists.newArrayList();
        this.func_190606_j().addCollisionBoxToList(this.world, BlockPos.ORIGIN, new AxisAlignedBB(BlockPos.ORIGIN), list, null, true);
        if (!list.isEmpty() && !(list1 = this.world.getEntitiesWithinAABBExcludingEntity(null, this.func_190610_a(axisalignedbb = this.func_190607_a(this.func_191515_a(list)), enumfacing, d0).union(axisalignedbb))).isEmpty()) {
            boolean flag = this.pistonState.getBlock() == Blocks.SLIME_BLOCK;
            for (int i = 0; i < list1.size(); ++i) {
                AxisAlignedBB axisalignedbb2;
                AxisAlignedBB axisalignedbb1;
                Entity entity = list1.get(i);
                if (entity.getPushReaction() == EnumPushReaction.IGNORE) continue;
                if (flag) {
                    switch (enumfacing.getAxis()) {
                        case X: {
                            entity.motionX = enumfacing.getFrontOffsetX();
                            break;
                        }
                        case Y: {
                            entity.motionY = enumfacing.getFrontOffsetY();
                            break;
                        }
                        case Z: {
                            entity.motionZ = enumfacing.getFrontOffsetZ();
                        }
                    }
                }
                double d1 = 0.0;
                for (int j = 0; !(j >= list.size() || (axisalignedbb1 = this.func_190610_a(this.func_190607_a((AxisAlignedBB)list.get(j)), enumfacing, d0)).intersectsWith(axisalignedbb2 = entity.getEntityBoundingBox()) && (d1 = Math.max(d1, this.func_190612_a(axisalignedbb1, enumfacing, axisalignedbb2))) >= d0); ++j) {
                }
                if (!(d1 > 0.0)) continue;
                d1 = Math.min(d1, d0) + 0.01;
                field_190613_i.set(enumfacing);
                entity.moveEntity(MoverType.PISTON, d1 * (double)enumfacing.getFrontOffsetX(), d1 * (double)enumfacing.getFrontOffsetY(), d1 * (double)enumfacing.getFrontOffsetZ());
                field_190613_i.set(null);
                if (this.extending || !this.shouldHeadBeRendered) continue;
                this.func_190605_a(entity, enumfacing, d0);
            }
        }
    }

    private AxisAlignedBB func_191515_a(List<AxisAlignedBB> p_191515_1_) {
        double d0 = 0.0;
        double d1 = 0.0;
        double d2 = 0.0;
        double d3 = 1.0;
        double d4 = 1.0;
        double d5 = 1.0;
        for (AxisAlignedBB axisalignedbb : p_191515_1_) {
            d0 = Math.min(axisalignedbb.minX, d0);
            d1 = Math.min(axisalignedbb.minY, d1);
            d2 = Math.min(axisalignedbb.minZ, d2);
            d3 = Math.max(axisalignedbb.maxX, d3);
            d4 = Math.max(axisalignedbb.maxY, d4);
            d5 = Math.max(axisalignedbb.maxZ, d5);
        }
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    private double func_190612_a(AxisAlignedBB p_190612_1_, EnumFacing p_190612_2_, AxisAlignedBB p_190612_3_) {
        switch (p_190612_2_.getAxis()) {
            case X: {
                return TileEntityPiston.func_190611_b(p_190612_1_, p_190612_2_, p_190612_3_);
            }
            default: {
                return TileEntityPiston.func_190608_c(p_190612_1_, p_190612_2_, p_190612_3_);
            }
            case Z: 
        }
        return TileEntityPiston.func_190604_d(p_190612_1_, p_190612_2_, p_190612_3_);
    }

    private AxisAlignedBB func_190607_a(AxisAlignedBB p_190607_1_) {
        double d0 = this.getExtendedProgress(this.progress);
        return p_190607_1_.offset((double)this.pos.getX() + d0 * (double)this.pistonFacing.getFrontOffsetX(), (double)this.pos.getY() + d0 * (double)this.pistonFacing.getFrontOffsetY(), (double)this.pos.getZ() + d0 * (double)this.pistonFacing.getFrontOffsetZ());
    }

    private AxisAlignedBB func_190610_a(AxisAlignedBB p_190610_1_, EnumFacing p_190610_2_, double p_190610_3_) {
        double d0 = p_190610_3_ * (double)p_190610_2_.getAxisDirection().getOffset();
        double d1 = Math.min(d0, 0.0);
        double d2 = Math.max(d0, 0.0);
        switch (p_190610_2_) {
            case WEST: {
                return new AxisAlignedBB(p_190610_1_.minX + d1, p_190610_1_.minY, p_190610_1_.minZ, p_190610_1_.minX + d2, p_190610_1_.maxY, p_190610_1_.maxZ);
            }
            case EAST: {
                return new AxisAlignedBB(p_190610_1_.maxX + d1, p_190610_1_.minY, p_190610_1_.minZ, p_190610_1_.maxX + d2, p_190610_1_.maxY, p_190610_1_.maxZ);
            }
            case DOWN: {
                return new AxisAlignedBB(p_190610_1_.minX, p_190610_1_.minY + d1, p_190610_1_.minZ, p_190610_1_.maxX, p_190610_1_.minY + d2, p_190610_1_.maxZ);
            }
            default: {
                return new AxisAlignedBB(p_190610_1_.minX, p_190610_1_.maxY + d1, p_190610_1_.minZ, p_190610_1_.maxX, p_190610_1_.maxY + d2, p_190610_1_.maxZ);
            }
            case NORTH: {
                return new AxisAlignedBB(p_190610_1_.minX, p_190610_1_.minY, p_190610_1_.minZ + d1, p_190610_1_.maxX, p_190610_1_.maxY, p_190610_1_.minZ + d2);
            }
            case SOUTH: 
        }
        return new AxisAlignedBB(p_190610_1_.minX, p_190610_1_.minY, p_190610_1_.maxZ + d1, p_190610_1_.maxX, p_190610_1_.maxY, p_190610_1_.maxZ + d2);
    }

    private void func_190605_a(Entity p_190605_1_, EnumFacing p_190605_2_, double p_190605_3_) {
        double d1;
        EnumFacing enumfacing;
        double d0;
        AxisAlignedBB axisalignedbb1;
        AxisAlignedBB axisalignedbb = p_190605_1_.getEntityBoundingBox();
        if (axisalignedbb.intersectsWith(axisalignedbb1 = Block.FULL_BLOCK_AABB.offset(this.pos)) && Math.abs((d0 = this.func_190612_a(axisalignedbb1, enumfacing = p_190605_2_.getOpposite(), axisalignedbb) + 0.01) - (d1 = this.func_190612_a(axisalignedbb1, enumfacing, axisalignedbb.func_191500_a(axisalignedbb1)) + 0.01)) < 0.01) {
            d0 = Math.min(d0, p_190605_3_) + 0.01;
            field_190613_i.set(p_190605_2_);
            p_190605_1_.moveEntity(MoverType.PISTON, d0 * (double)enumfacing.getFrontOffsetX(), d0 * (double)enumfacing.getFrontOffsetY(), d0 * (double)enumfacing.getFrontOffsetZ());
            field_190613_i.set(null);
        }
    }

    private static double func_190611_b(AxisAlignedBB p_190611_0_, EnumFacing p_190611_1_, AxisAlignedBB p_190611_2_) {
        return p_190611_1_.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE ? p_190611_0_.maxX - p_190611_2_.minX : p_190611_2_.maxX - p_190611_0_.minX;
    }

    private static double func_190608_c(AxisAlignedBB p_190608_0_, EnumFacing p_190608_1_, AxisAlignedBB p_190608_2_) {
        return p_190608_1_.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE ? p_190608_0_.maxY - p_190608_2_.minY : p_190608_2_.maxY - p_190608_0_.minY;
    }

    private static double func_190604_d(AxisAlignedBB p_190604_0_, EnumFacing p_190604_1_, AxisAlignedBB p_190604_2_) {
        return p_190604_1_.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE ? p_190604_0_.maxZ - p_190604_2_.minZ : p_190604_2_.maxZ - p_190604_0_.minZ;
    }

    public void clearPistonTileEntity() {
        if (this.lastProgress < 1.0f && this.world != null) {
            this.lastProgress = this.progress = 1.0f;
            this.world.removeTileEntity(this.pos);
            this.invalidate();
            if (this.world.getBlockState(this.pos).getBlock() == Blocks.PISTON_EXTENSION) {
                this.world.setBlockState(this.pos, this.pistonState, 3);
                this.world.func_190524_a(this.pos, this.pistonState.getBlock(), this.pos);
            }
        }
    }

    @Override
    public void update() {
        this.lastProgress = this.progress;
        if (this.lastProgress >= 1.0f) {
            this.world.removeTileEntity(this.pos);
            this.invalidate();
            if (this.world.getBlockState(this.pos).getBlock() == Blocks.PISTON_EXTENSION) {
                this.world.setBlockState(this.pos, this.pistonState, 3);
                this.world.func_190524_a(this.pos, this.pistonState.getBlock(), this.pos);
            }
        } else {
            float f = this.progress + 0.5f;
            this.moveCollidedEntities(f);
            this.progress = f;
            if (this.progress >= 1.0f) {
                this.progress = 1.0f;
            }
        }
    }

    public static void registerFixesPiston(DataFixer fixer) {
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.pistonState = Block.getBlockById(compound.getInteger("blockId")).getStateFromMeta(compound.getInteger("blockData"));
        this.pistonFacing = EnumFacing.getFront(compound.getInteger("facing"));
        this.lastProgress = this.progress = compound.getFloat("progress");
        this.extending = compound.getBoolean("extending");
        this.shouldHeadBeRendered = compound.getBoolean("source");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setInteger("blockId", Block.getIdFromBlock(this.pistonState.getBlock()));
        compound.setInteger("blockData", this.pistonState.getBlock().getMetaFromState(this.pistonState));
        compound.setInteger("facing", this.pistonFacing.getIndex());
        compound.setFloat("progress", this.lastProgress);
        compound.setBoolean("extending", this.extending);
        compound.setBoolean("source", this.shouldHeadBeRendered);
        return compound;
    }

    public void func_190609_a(World p_190609_1_, BlockPos p_190609_2_, AxisAlignedBB p_190609_3_, List<AxisAlignedBB> p_190609_4_, @Nullable Entity p_190609_5_) {
        if (!this.extending && this.shouldHeadBeRendered) {
            this.pistonState.withProperty(BlockPistonBase.EXTENDED, true).addCollisionBoxToList(p_190609_1_, p_190609_2_, p_190609_3_, p_190609_4_, p_190609_5_, false);
        }
        EnumFacing enumfacing = field_190613_i.get();
        if ((double)this.progress >= 1.0 || enumfacing != (this.extending ? this.pistonFacing : this.pistonFacing.getOpposite())) {
            int i = p_190609_4_.size();
            IBlockState iblockstate = this.shouldPistonHeadBeRendered() ? Blocks.PISTON_HEAD.getDefaultState().withProperty(BlockPistonExtension.FACING, this.pistonFacing).withProperty(BlockPistonExtension.SHORT, this.extending != 1.0f - this.progress < 0.25f) : this.pistonState;
            float f = this.getExtendedProgress(this.progress);
            double d0 = (float)this.pistonFacing.getFrontOffsetX() * f;
            double d1 = (float)this.pistonFacing.getFrontOffsetY() * f;
            double d2 = (float)this.pistonFacing.getFrontOffsetZ() * f;
            iblockstate.addCollisionBoxToList(p_190609_1_, p_190609_2_, p_190609_3_.offset(-d0, -d1, -d2), p_190609_4_, p_190609_5_, true);
            for (int j = i; j < p_190609_4_.size(); ++j) {
                p_190609_4_.set(j, p_190609_4_.get(j).offset(d0, d1, d2));
            }
        }
    }
}

