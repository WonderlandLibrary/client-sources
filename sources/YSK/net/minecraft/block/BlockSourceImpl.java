package net.minecraft.block;

import net.minecraft.dispenser.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.tileentity.*;

public class BlockSourceImpl implements IBlockSource
{
    private final BlockPos pos;
    private final World worldObj;
    
    @Override
    public double getY() {
        return this.pos.getY() + 0.5;
    }
    
    public BlockSourceImpl(final World worldObj, final BlockPos pos) {
        this.worldObj = worldObj;
        this.pos = pos;
    }
    
    @Override
    public double getZ() {
        return this.pos.getZ() + 0.5;
    }
    
    @Override
    public World getWorld() {
        return this.worldObj;
    }
    
    @Override
    public BlockPos getBlockPos() {
        return this.pos;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getBlockMetadata() {
        final IBlockState blockState = this.worldObj.getBlockState(this.pos);
        return blockState.getBlock().getMetaFromState(blockState);
    }
    
    @Override
    public double getX() {
        return this.pos.getX() + 0.5;
    }
    
    @Override
    public <T extends TileEntity> T getBlockTileEntity() {
        return (T)this.worldObj.getTileEntity(this.pos);
    }
}
