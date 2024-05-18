package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class WorldGenSand extends WorldGenerator
{
    private int radius;
    private Block block;
    
    public WorldGenSand(final Block block, final int radius) {
        this.block = block;
        this.radius = radius;
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.water) {
            return "".length() != 0;
        }
        final int n = random.nextInt(this.radius - "  ".length()) + "  ".length();
        final int length = "  ".length();
        int i = blockPos.getX() - n;
        "".length();
        if (4 < 3) {
            throw null;
        }
        while (i <= blockPos.getX() + n) {
            int j = blockPos.getZ() - n;
            "".length();
            if (-1 == 3) {
                throw null;
            }
            while (j <= blockPos.getZ() + n) {
                final int n2 = i - blockPos.getX();
                final int n3 = j - blockPos.getZ();
                if (n2 * n2 + n3 * n3 <= n * n) {
                    int k = blockPos.getY() - length;
                    "".length();
                    if (4 < 1) {
                        throw null;
                    }
                    while (k <= blockPos.getY() + length) {
                        final BlockPos blockPos2 = new BlockPos(i, k, j);
                        final Block block = world.getBlockState(blockPos2).getBlock();
                        if (block == Blocks.dirt || block == Blocks.grass) {
                            world.setBlockState(blockPos2, this.block.getDefaultState(), "  ".length());
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
            if (2 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
