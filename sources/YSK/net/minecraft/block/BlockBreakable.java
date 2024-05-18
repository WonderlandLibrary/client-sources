package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;

public class BlockBreakable extends Block
{
    private boolean ignoreSimilarity;
    
    protected BlockBreakable(final Material material, final boolean ignoreSimilarity, final MapColor mapColor) {
        super(material, mapColor);
        this.ignoreSimilarity = ignoreSimilarity;
    }
    
    protected BlockBreakable(final Material material, final boolean b) {
        this(material, b, material.getMaterialMapColor());
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        final IBlockState blockState = blockAccess.getBlockState(blockPos);
        final Block block = blockState.getBlock();
        if (this == Blocks.glass || this == Blocks.stained_glass) {
            if (blockAccess.getBlockState(blockPos.offset(enumFacing.getOpposite())) != blockState) {
                return " ".length() != 0;
            }
            if (block == this) {
                return "".length() != 0;
            }
        }
        int n;
        if (!this.ignoreSimilarity && block == this) {
            n = "".length();
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            n = (super.shouldSideBeRendered(blockAccess, blockPos, enumFacing) ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    public boolean isOpaqueCube() {
        return "".length() != 0;
    }
}
