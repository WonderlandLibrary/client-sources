package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class WorldGenClay extends WorldGenerator
{
    private int numberOfBlocks;
    private Block field_150546_a;
    
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.water) {
            return "".length() != 0;
        }
        final int n = random.nextInt(this.numberOfBlocks - "  ".length()) + "  ".length();
        final int length = " ".length();
        int i = blockPos.getX() - n;
        "".length();
        if (true != true) {
            throw null;
        }
        while (i <= blockPos.getX() + n) {
            int j = blockPos.getZ() - n;
            "".length();
            if (4 == 0) {
                throw null;
            }
            while (j <= blockPos.getZ() + n) {
                final int n2 = i - blockPos.getX();
                final int n3 = j - blockPos.getZ();
                if (n2 * n2 + n3 * n3 <= n * n) {
                    int k = blockPos.getY() - length;
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                    while (k <= blockPos.getY() + length) {
                        final BlockPos blockPos2 = new BlockPos(i, k, j);
                        final Block block = world.getBlockState(blockPos2).getBlock();
                        if (block == Blocks.dirt || block == Blocks.clay) {
                            world.setBlockState(blockPos2, this.field_150546_a.getDefaultState(), "  ".length());
                        }
                        ++k;
                    }
                }
                ++j;
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    public WorldGenClay(final int numberOfBlocks) {
        this.field_150546_a = Blocks.clay;
        this.numberOfBlocks = numberOfBlocks;
    }
}
