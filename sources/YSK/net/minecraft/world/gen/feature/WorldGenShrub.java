package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class WorldGenShrub extends WorldGenTrees
{
    private final IBlockState leavesMetadata;
    private final IBlockState woodMetadata;
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos blockPos) {
        "".length();
        if (2 == 0) {
            throw null;
        }
        Block block;
        while (((block = world.getBlockState(blockPos).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.leaves) && blockPos.getY() > 0) {
            blockPos = blockPos.down();
        }
        final Block block2 = world.getBlockState(blockPos).getBlock();
        if (block2 == Blocks.dirt || block2 == Blocks.grass) {
            blockPos = blockPos.up();
            this.setBlockAndNotifyAdequately(world, blockPos, this.woodMetadata);
            int i = blockPos.getY();
            "".length();
            if (3 < 2) {
                throw null;
            }
            while (i <= blockPos.getY() + "  ".length()) {
                final int n = "  ".length() - (i - blockPos.getY());
                int j = blockPos.getX() - n;
                "".length();
                if (0 >= 2) {
                    throw null;
                }
                while (j <= blockPos.getX() + n) {
                    final int n2 = j - blockPos.getX();
                    int k = blockPos.getZ() - n;
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                    while (k <= blockPos.getZ() + n) {
                        final int n3 = k - blockPos.getZ();
                        if (Math.abs(n2) != n || Math.abs(n3) != n || random.nextInt("  ".length()) != 0) {
                            final BlockPos blockPos2 = new BlockPos(j, i, k);
                            if (!world.getBlockState(blockPos2).getBlock().isFullBlock()) {
                                this.setBlockAndNotifyAdequately(world, blockPos2, this.leavesMetadata);
                            }
                        }
                        ++k;
                    }
                    ++j;
                }
                ++i;
            }
        }
        return " ".length() != 0;
    }
    
    public WorldGenShrub(final IBlockState woodMetadata, final IBlockState leavesMetadata) {
        super("".length() != 0);
        this.woodMetadata = woodMetadata;
        this.leavesMetadata = leavesMetadata;
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
            if (-1 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
