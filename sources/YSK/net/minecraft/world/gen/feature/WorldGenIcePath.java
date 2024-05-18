package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;

public class WorldGenIcePath extends WorldGenerator
{
    private Block block;
    private int basePathWidth;
    
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
            if (-1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public WorldGenIcePath(final int basePathWidth) {
        this.block = Blocks.packed_ice;
        this.basePathWidth = basePathWidth;
    }
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos down) {
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (world.isAirBlock(down) && down.getY() > "  ".length()) {
            down = down.down();
        }
        if (world.getBlockState(down).getBlock() != Blocks.snow) {
            return "".length() != 0;
        }
        final int n = random.nextInt(this.basePathWidth - "  ".length()) + "  ".length();
        final int length = " ".length();
        int i = down.getX() - n;
        "".length();
        if (3 < 3) {
            throw null;
        }
        while (i <= down.getX() + n) {
            int j = down.getZ() - n;
            "".length();
            if (2 >= 4) {
                throw null;
            }
            while (j <= down.getZ() + n) {
                final int n2 = i - down.getX();
                final int n3 = j - down.getZ();
                if (n2 * n2 + n3 * n3 <= n * n) {
                    int k = down.getY() - length;
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                    while (k <= down.getY() + length) {
                        final BlockPos blockPos = new BlockPos(i, k, j);
                        final Block block = world.getBlockState(blockPos).getBlock();
                        if (block == Blocks.dirt || block == Blocks.snow || block == Blocks.ice) {
                            world.setBlockState(blockPos, this.block.getDefaultState(), "  ".length());
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
}
