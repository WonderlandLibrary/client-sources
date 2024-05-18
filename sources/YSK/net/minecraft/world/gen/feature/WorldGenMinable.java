package net.minecraft.world.gen.feature;

import com.google.common.base.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.pattern.*;

public class WorldGenMinable extends WorldGenerator
{
    private final Predicate<IBlockState> predicate;
    private final int numberOfBlocks;
    private final IBlockState oreBlock;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        final float n = random.nextFloat() * 3.1415927f;
        final double n2 = blockPos.getX() + (0x54 ^ 0x5C) + MathHelper.sin(n) * this.numberOfBlocks / 8.0f;
        final double n3 = blockPos.getX() + (0xBA ^ 0xB2) - MathHelper.sin(n) * this.numberOfBlocks / 8.0f;
        final double n4 = blockPos.getZ() + (0x7D ^ 0x75) + MathHelper.cos(n) * this.numberOfBlocks / 8.0f;
        final double n5 = blockPos.getZ() + (0x7F ^ 0x77) - MathHelper.cos(n) * this.numberOfBlocks / 8.0f;
        final double n6 = blockPos.getY() + random.nextInt("   ".length()) - "  ".length();
        final double n7 = blockPos.getY() + random.nextInt("   ".length()) - "  ".length();
        int i = "".length();
        "".length();
        if (1 < -1) {
            throw null;
        }
        while (i < this.numberOfBlocks) {
            final float n8 = i / this.numberOfBlocks;
            final double n9 = n2 + (n3 - n2) * n8;
            final double n10 = n6 + (n7 - n6) * n8;
            final double n11 = n4 + (n5 - n4) * n8;
            final double n12 = random.nextDouble() * this.numberOfBlocks / 16.0;
            final double n13 = (MathHelper.sin(3.1415927f * n8) + 1.0f) * n12 + 1.0;
            final double n14 = (MathHelper.sin(3.1415927f * n8) + 1.0f) * n12 + 1.0;
            final int floor_double = MathHelper.floor_double(n9 - n13 / 2.0);
            final int floor_double2 = MathHelper.floor_double(n10 - n14 / 2.0);
            final int floor_double3 = MathHelper.floor_double(n11 - n13 / 2.0);
            final int floor_double4 = MathHelper.floor_double(n9 + n13 / 2.0);
            final int floor_double5 = MathHelper.floor_double(n10 + n14 / 2.0);
            final int floor_double6 = MathHelper.floor_double(n11 + n13 / 2.0);
            int j = floor_double;
            "".length();
            if (0 < -1) {
                throw null;
            }
            while (j <= floor_double4) {
                final double n15 = (j + 0.5 - n9) / (n13 / 2.0);
                if (n15 * n15 < 1.0) {
                    int k = floor_double2;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    while (k <= floor_double5) {
                        final double n16 = (k + 0.5 - n10) / (n14 / 2.0);
                        if (n15 * n15 + n16 * n16 < 1.0) {
                            int l = floor_double3;
                            "".length();
                            if (3 < 3) {
                                throw null;
                            }
                            while (l <= floor_double6) {
                                final double n17 = (l + 0.5 - n11) / (n13 / 2.0);
                                if (n15 * n15 + n16 * n16 + n17 * n17 < 1.0) {
                                    final BlockPos blockPos2 = new BlockPos(j, k, l);
                                    if (this.predicate.apply((Object)world.getBlockState(blockPos2))) {
                                        world.setBlockState(blockPos2, this.oreBlock, "  ".length());
                                    }
                                }
                                ++l;
                            }
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
    
    public WorldGenMinable(final IBlockState oreBlock, final int numberOfBlocks, final Predicate<IBlockState> predicate) {
        this.oreBlock = oreBlock;
        this.numberOfBlocks = numberOfBlocks;
        this.predicate = predicate;
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
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public WorldGenMinable(final IBlockState blockState, final int n) {
        this(blockState, n, (Predicate<IBlockState>)BlockHelper.forBlock(Blocks.stone));
    }
}
