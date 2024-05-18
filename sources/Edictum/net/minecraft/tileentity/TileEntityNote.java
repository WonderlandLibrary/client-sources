package net.minecraft.tileentity;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class TileEntityNote extends TileEntity
{
    /** Note to play */
    public byte note;

    /** stores the latest redstone state */
    public boolean previousRedstoneState;

    public NBTTagCompound func_189515_b(NBTTagCompound p_189515_1_)
    {
        super.func_189515_b(p_189515_1_);
        p_189515_1_.setByte("note", this.note);
        p_189515_1_.setBoolean("powered", this.previousRedstoneState);
        return p_189515_1_;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.note = compound.getByte("note");
        this.note = (byte)MathHelper.clamp_int(this.note, 0, 24);
        this.previousRedstoneState = compound.getBoolean("powered");
    }

    /**
     * change pitch by -> (currentPitch + 1) % 25
     */
    public void changePitch()
    {
        this.note = (byte)((this.note + 1) % 25);
        this.markDirty();
    }

    public void triggerNote(World worldIn, BlockPos posIn)
    {
        if (worldIn.getBlockState(posIn.up()).getMaterial() == Material.AIR)
        {
            Material material = worldIn.getBlockState(posIn.down()).getMaterial();
            int i = 0;

            if (material == Material.ROCK)
            {
                i = 1;
            }

            if (material == Material.SAND)
            {
                i = 2;
            }

            if (material == Material.GLASS)
            {
                i = 3;
            }

            if (material == Material.WOOD)
            {
                i = 4;
            }

            worldIn.addBlockEvent(posIn, Blocks.NOTEBLOCK, i, this.note);
        }
    }
}
