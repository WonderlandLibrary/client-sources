package net.minecraft.block;

import net.minecraft.block.material.*;
import net.minecraft.world.*;
import net.minecraft.util.*;

public class BlockLeavesBase extends Block
{
    protected boolean fancyGraphics;
    
    protected BlockLeavesBase(final Material material, final boolean fancyGraphics) {
        super(material);
        this.fancyGraphics = fancyGraphics;
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldSideBeRendered(final IBlockAccess blockAccess, final BlockPos blockPos, final EnumFacing enumFacing) {
        int n;
        if (!this.fancyGraphics && blockAccess.getBlockState(blockPos).getBlock() == this) {
            n = "".length();
            "".length();
            if (4 == -1) {
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
