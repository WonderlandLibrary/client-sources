package net.minecraft.block;

import net.minecraft.world.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;

public class BlockAir extends Block
{
    @Override
    public AxisAlignedBB getCollisionBoundingBox(final World world, final BlockPos blockPos, final IBlockState blockState) {
        return null;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
    
    @Override
    public boolean isReplaceable(final World world, final BlockPos blockPos) {
        return " ".length() != 0;
    }
    
    @Override
    public void dropBlockAsItemWithChance(final World world, final BlockPos blockPos, final IBlockState blockState, final float n, final int n2) {
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getRenderType() {
        return -" ".length();
    }
    
    @Override
    public boolean canCollideCheck(final IBlockState blockState, final boolean b) {
        return "".length() != 0;
    }
    
    protected BlockAir() {
        super(Material.air);
    }
}
