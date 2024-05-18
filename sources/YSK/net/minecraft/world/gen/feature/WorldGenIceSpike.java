package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class WorldGenIceSpike extends WorldGenerator
{
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
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos blockPos) {
        "".length();
        if (4 < -1) {
            throw null;
        }
        while (world.isAirBlock(blockPos) && blockPos.getY() > "  ".length()) {
            blockPos = blockPos.down();
        }
        if (world.getBlockState(blockPos).getBlock() != Blocks.snow) {
            return "".length() != 0;
        }
        blockPos = blockPos.up(random.nextInt(0x40 ^ 0x44));
        final int n = random.nextInt(0x37 ^ 0x33) + (0x8D ^ 0x8A);
        final int n2 = n / (0x91 ^ 0x95) + random.nextInt("  ".length());
        if (n2 > " ".length() && random.nextInt(0x48 ^ 0x74) == 0) {
            blockPos = blockPos.up((0x1A ^ 0x10) + random.nextInt(0x70 ^ 0x6E));
        }
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < n) {
            final float n3 = (1.0f - i / n) * n2;
            final int ceiling_float_int = MathHelper.ceiling_float_int(n3);
            int j = -ceiling_float_int;
            "".length();
            if (4 < 0) {
                throw null;
            }
            while (j <= ceiling_float_int) {
                final float n4 = MathHelper.abs_int(j) - 0.25f;
                int k = -ceiling_float_int;
                "".length();
                if (4 < 3) {
                    throw null;
                }
                while (k <= ceiling_float_int) {
                    final float n5 = MathHelper.abs_int(k) - 0.25f;
                    if (((j == 0 && k == 0) || n4 * n4 + n5 * n5 <= n3 * n3) && ((j != -ceiling_float_int && j != ceiling_float_int && k != -ceiling_float_int && k != ceiling_float_int) || random.nextFloat() <= 0.75f)) {
                        final Block block = world.getBlockState(blockPos.add(j, i, k)).getBlock();
                        if (block.getMaterial() == Material.air || block == Blocks.dirt || block == Blocks.snow || block == Blocks.ice) {
                            this.setBlockAndNotifyAdequately(world, blockPos.add(j, i, k), Blocks.packed_ice.getDefaultState());
                        }
                        if (i != 0 && ceiling_float_int > " ".length()) {
                            final Block block2 = world.getBlockState(blockPos.add(j, -i, k)).getBlock();
                            if (block2.getMaterial() == Material.air || block2 == Blocks.dirt || block2 == Blocks.snow || block2 == Blocks.ice) {
                                this.setBlockAndNotifyAdequately(world, blockPos.add(j, -i, k), Blocks.packed_ice.getDefaultState());
                            }
                        }
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        int n6 = n2 - " ".length();
        if (n6 < 0) {
            n6 = "".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else if (n6 > " ".length()) {
            n6 = " ".length();
        }
        int l = -n6;
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (l <= n6) {
            int n7 = -n6;
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (n7 <= n6) {
                BlockPos blockPos2 = blockPos.add(l, -" ".length(), n7);
                int n8 = 0x3 ^ 0x31;
                if (Math.abs(l) == " ".length() && Math.abs(n7) == " ".length()) {
                    n8 = random.nextInt(0x7E ^ 0x7B);
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                while (blockPos2.getY() > (0x6C ^ 0x5E)) {
                    final Block block3 = world.getBlockState(blockPos2).getBlock();
                    if (block3.getMaterial() != Material.air && block3 != Blocks.dirt && block3 != Blocks.snow && block3 != Blocks.ice && block3 != Blocks.packed_ice) {
                        "".length();
                        if (3 <= -1) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        this.setBlockAndNotifyAdequately(world, blockPos2, Blocks.packed_ice.getDefaultState());
                        blockPos2 = blockPos2.down();
                        if (--n8 > 0) {
                            continue;
                        }
                        blockPos2 = blockPos2.down(random.nextInt(0x22 ^ 0x27) + " ".length());
                        n8 = random.nextInt(0x63 ^ 0x66);
                    }
                }
                ++n7;
            }
            ++l;
        }
        return " ".length() != 0;
    }
}
