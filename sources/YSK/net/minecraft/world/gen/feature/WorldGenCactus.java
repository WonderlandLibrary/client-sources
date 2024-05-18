package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class WorldGenCactus extends WorldGenerator
{
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (3 <= 0) {
            throw null;
        }
        while (i < (0x5B ^ 0x51)) {
            final BlockPos add = blockPos.add(random.nextInt(0x76 ^ 0x7E) - random.nextInt(0xBD ^ 0xB5), random.nextInt(0x6 ^ 0x2) - random.nextInt(0x51 ^ 0x55), random.nextInt(0x3C ^ 0x34) - random.nextInt(0x76 ^ 0x7E));
            if (world.isAirBlock(add)) {
                final int n = " ".length() + random.nextInt(random.nextInt("   ".length()) + " ".length());
                int j = "".length();
                "".length();
                if (3 != 3) {
                    throw null;
                }
                while (j < n) {
                    if (Blocks.cactus.canBlockStay(world, add)) {
                        world.setBlockState(add.up(j), Blocks.cactus.getDefaultState(), "  ".length());
                    }
                    ++j;
                }
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
