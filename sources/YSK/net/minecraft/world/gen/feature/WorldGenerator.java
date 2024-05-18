package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;
import java.util.*;

public abstract class WorldGenerator
{
    private final boolean doBlockNotify;
    
    public WorldGenerator() {
        this("".length() != 0);
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void setBlockAndNotifyAdequately(final World world, final BlockPos blockPos, final IBlockState blockState) {
        if (this.doBlockNotify) {
            world.setBlockState(blockPos, blockState, "   ".length());
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        else {
            world.setBlockState(blockPos, blockState, "  ".length());
        }
    }
    
    public void func_175904_e() {
    }
    
    public WorldGenerator(final boolean doBlockNotify) {
        this.doBlockNotify = doBlockNotify;
    }
    
    public abstract boolean generate(final World p0, final Random p1, final BlockPos p2);
}
