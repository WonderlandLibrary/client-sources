package net.minecraft.tileentity;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class TileEntityPiston extends TileEntity implements ITickable
{
    private IBlockState pistonState;
    private EnumFacing pistonFacing;

    /** if this piston is extending or not */
    private boolean extending;
    private boolean shouldHeadBeRendered;
    private float progress;

    /** the progress in (de)extending */
    private float lastProgress;

    public TileEntityPiston()
    {
    }

    public TileEntityPiston(IBlockState pistonStateIn, EnumFacing pistonFacingIn, boolean extendingIn, boolean shouldHeadBeRenderedIn)
    {
        this.pistonState = pistonStateIn;
        this.pistonFacing = pistonFacingIn;
        this.extending = extendingIn;
        this.shouldHeadBeRendered = shouldHeadBeRenderedIn;
    }

    public IBlockState getPistonState()
    {
        return this.pistonState;
    }

    public int getBlockMetadata()
    {
        return 0;
    }

    /**
     * Returns true if a piston is extending
     */
    public boolean isExtending()
    {
        return this.extending;
    }

    public EnumFacing getFacing()
    {
        return this.pistonFacing;
    }

    public boolean shouldPistonHeadBeRendered()
    {
        return this.shouldHeadBeRendered;
    }

    /**
     * Get interpolated progress value (between lastProgress and progress) given the fractional time between ticks as an
     * argument
     */
    public float getProgress(float ticks)
    {
        if (ticks > 1.0F)
        {
            ticks = 1.0F;
        }

        return this.lastProgress + (this.progress - this.lastProgress) * ticks;
    }

    public float getOffsetX(float ticks)
    {
        return (float)this.pistonFacing.getFrontOffsetX() * this.getExtendedProgress(this.getProgress(ticks));
    }

    public float getOffsetY(float ticks)
    {
        return (float)this.pistonFacing.getFrontOffsetY() * this.getExtendedProgress(this.getProgress(ticks));
    }

    public float getOffsetZ(float ticks)
    {
        return (float)this.pistonFacing.getFrontOffsetZ() * this.getExtendedProgress(this.getProgress(ticks));
    }

    private float getExtendedProgress(float p_184320_1_)
    {
        return this.extending ? p_184320_1_ - 1.0F : 1.0F - p_184320_1_;
    }

    public AxisAlignedBB getAABB(IBlockAccess p_184321_1_, BlockPos p_184321_2_)
    {
        return this.getAABB(p_184321_1_, p_184321_2_, this.progress).union(this.getAABB(p_184321_1_, p_184321_2_, this.lastProgress));
    }

    public AxisAlignedBB getAABB(IBlockAccess p_184319_1_, BlockPos p_184319_2_, float p_184319_3_)
    {
        p_184319_3_ = this.getExtendedProgress(p_184319_3_);
        return this.pistonState.getBoundingBox(p_184319_1_, p_184319_2_).offset((double)(p_184319_3_ * (float)this.pistonFacing.getFrontOffsetX()), (double)(p_184319_3_ * (float)this.pistonFacing.getFrontOffsetY()), (double)(p_184319_3_ * (float)this.pistonFacing.getFrontOffsetZ()));
    }

    private void moveCollidedEntities()
    {
        AxisAlignedBB axisalignedbb = this.getAABB(this.worldObj, this.pos).offset(this.pos);
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity)null, axisalignedbb);

        if (!list.isEmpty())
        {
            EnumFacing enumfacing = this.extending ? this.pistonFacing : this.pistonFacing.getOpposite();

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);

                if (entity.getPushReaction() != EnumPushReaction.IGNORE)
                {
                    if (this.pistonState.getBlock() == Blocks.SLIME_BLOCK)
                    {
                        switch (enumfacing.getAxis())
                        {
                            case X:
                                entity.motionX = (double)enumfacing.getFrontOffsetX();
                                break;

                            case Y:
                                entity.motionY = (double)enumfacing.getFrontOffsetY();
                                break;

                            case Z:
                                entity.motionZ = (double)enumfacing.getFrontOffsetZ();
                        }
                    }

                    double d0 = 0.0D;
                    double d1 = 0.0D;
                    double d2 = 0.0D;
                    AxisAlignedBB axisalignedbb1 = entity.getEntityBoundingBox();

                    switch (enumfacing.getAxis())
                    {
                        case X:
                            if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE)
                            {
                                d0 = axisalignedbb.maxX - axisalignedbb1.minX;
                            }
                            else
                            {
                                d0 = axisalignedbb1.maxX - axisalignedbb.minX;
                            }

                            d0 = d0 + 0.01D;
                            break;

                        case Y:
                            if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE)
                            {
                                d1 = axisalignedbb.maxY - axisalignedbb1.minY;
                            }
                            else
                            {
                                d1 = axisalignedbb1.maxY - axisalignedbb.minY;
                            }

                            d1 = d1 + 0.01D;
                            break;

                        case Z:
                            if (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE)
                            {
                                d2 = axisalignedbb.maxZ - axisalignedbb1.minZ;
                            }
                            else
                            {
                                d2 = axisalignedbb1.maxZ - axisalignedbb.minZ;
                            }

                            d2 = d2 + 0.01D;
                    }

                    entity.moveEntity(d0 * (double)enumfacing.getFrontOffsetX(), d1 * (double)enumfacing.getFrontOffsetY(), d2 * (double)enumfacing.getFrontOffsetZ());
                }
            }
        }
    }

    /**
     * removes a piston's tile entity (and if the piston is moving, stops it)
     */
    public void clearPistonTileEntity()
    {
        if (this.lastProgress < 1.0F && this.worldObj != null)
        {
            this.progress = 1.0F;
            this.lastProgress = this.progress;
            this.worldObj.removeTileEntity(this.pos);
            this.invalidate();

            if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.PISTON_EXTENSION)
            {
                this.worldObj.setBlockState(this.pos, this.pistonState, 3);
                this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
            }
        }
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
        this.lastProgress = this.progress;

        if (this.lastProgress >= 1.0F)
        {
            this.moveCollidedEntities();
            this.worldObj.removeTileEntity(this.pos);
            this.invalidate();

            if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.PISTON_EXTENSION)
            {
                this.worldObj.setBlockState(this.pos, this.pistonState, 3);
                this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
            }
        }
        else
        {
            this.progress += 0.5F;

            if (this.progress >= 1.0F)
            {
                this.progress = 1.0F;
            }

            this.moveCollidedEntities();
        }
    }

    public static void func_189685_a(DataFixer p_189685_0_)
    {
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.pistonState = Block.getBlockById(compound.getInteger("blockId")).getStateFromMeta(compound.getInteger("blockData"));
        this.pistonFacing = EnumFacing.getFront(compound.getInteger("facing"));
        this.progress = compound.getFloat("progress");
        this.lastProgress = this.progress;
        this.extending = compound.getBoolean("extending");
    }

    public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_)
    {
        super.func_189515_b(p_189515_1_);
        p_189515_1_.setInteger("blockId", Block.getIdFromBlock(this.pistonState.getBlock()));
        p_189515_1_.setInteger("blockData", this.pistonState.getBlock().getMetaFromState(this.pistonState));
        p_189515_1_.setInteger("facing", this.pistonFacing.getIndex());
        p_189515_1_.setFloat("progress", this.lastProgress);
        p_189515_1_.setBoolean("extending", this.extending);
        return p_189515_1_;
    }
}
