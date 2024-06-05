package net.minecraft.block;

import net.minecraft.dispenser.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;

public class BlockSourceImpl implements IBlockSource
{
    private final World worldObj;
    private final BlockPos pos;
    
    public BlockSourceImpl(final World worldIn, final BlockPos p_i46023_2_) {
        this.worldObj = worldIn;
        this.pos = p_i46023_2_;
    }
    
    @Override
    public World getWorld() {
        return this.worldObj;
    }
    
    @Override
    public double getX() {
        return this.pos.getX() + 0.5;
    }
    
    @Override
    public double getY() {
        return this.pos.getY() + 0.5;
    }
    
    @Override
    public double getZ() {
        return this.pos.getZ() + 0.5;
    }
    
    @Override
    public BlockPos getBlockPos() {
        return this.pos;
    }
    
    @Override
    public Block getBlock() {
        return this.worldObj.getBlockState(this.pos).getBlock();
    }
    
    @Override
    public int getBlockMetadata() {
        final IBlockState var1 = this.worldObj.getBlockState(this.pos);
        return var1.getBlock().getMetaFromState(var1);
    }
    
    @Override
    public TileEntity getBlockTileEntity() {
        return this.worldObj.getTileEntity(this.pos);
    }
}
