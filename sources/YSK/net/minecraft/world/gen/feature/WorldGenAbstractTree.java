package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import java.util.*;

public abstract class WorldGenAbstractTree extends WorldGenerator
{
    protected void func_175921_a(final World world, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock() != Blocks.dirt) {
            this.setBlockAndNotifyAdequately(world, blockPos, Blocks.dirt.getDefaultState());
        }
    }
    
    protected boolean func_150523_a(final Block block) {
        final Material material = block.getMaterial();
        if (material != Material.air && material != Material.leaves && block != Blocks.grass && block != Blocks.dirt && block != Blocks.log && block != Blocks.log2 && block != Blocks.sapling && block != Blocks.vine) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void func_180711_a(final World world, final Random random, final BlockPos blockPos) {
    }
    
    public WorldGenAbstractTree(final boolean b) {
        super(b);
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
}
