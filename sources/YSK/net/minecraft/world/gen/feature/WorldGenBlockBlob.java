package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import java.util.*;

public class WorldGenBlockBlob extends WorldGenerator
{
    private final Block field_150545_a;
    private final int field_150544_b;
    
    public WorldGenBlockBlob(final Block field_150545_a, final int field_150544_b) {
        super("".length() != 0);
        this.field_150545_a = field_150545_a;
        this.field_150544_b = field_150544_b;
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
            if (1 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos blockPos) {
        while (blockPos.getY() > "   ".length()) {
            if (world.isAirBlock(blockPos.down())) {
                "".length();
                if (4 < 0) {
                    throw null;
                }
            }
            else {
                final Block block = world.getBlockState(blockPos.down()).getBlock();
                if (block == Blocks.grass || block == Blocks.dirt || block == Blocks.stone) {
                    break;
                }
                "".length();
                if (!true) {
                    throw null;
                }
            }
            blockPos = blockPos.down();
            "".length();
            if (false == true) {
                throw null;
            }
        }
        if (blockPos.getY() <= "   ".length()) {
            return "".length() != 0;
        }
        final int field_150544_b = this.field_150544_b;
        int length = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (field_150544_b >= 0 && length < "   ".length()) {
            final int n = field_150544_b + random.nextInt("  ".length());
            final int n2 = field_150544_b + random.nextInt("  ".length());
            final int n3 = field_150544_b + random.nextInt("  ".length());
            final float n4 = (n + n2 + n3) * 0.333f + 0.5f;
            final Iterator<BlockPos> iterator = BlockPos.getAllInBox(blockPos.add(-n, -n2, -n3), blockPos.add(n, n2, n3)).iterator();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (iterator.hasNext()) {
                final BlockPos blockPos2 = iterator.next();
                if (blockPos2.distanceSq(blockPos) <= n4 * n4) {
                    world.setBlockState(blockPos2, this.field_150545_a.getDefaultState(), 0x10 ^ 0x14);
                }
            }
            blockPos = blockPos.add(-(field_150544_b + " ".length()) + random.nextInt("  ".length() + field_150544_b * "  ".length()), "".length() - random.nextInt("  ".length()), -(field_150544_b + " ".length()) + random.nextInt("  ".length() + field_150544_b * "  ".length()));
            ++length;
        }
        return " ".length() != 0;
    }
}
