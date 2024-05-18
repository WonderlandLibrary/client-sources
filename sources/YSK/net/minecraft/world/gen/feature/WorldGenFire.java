package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class WorldGenFire extends WorldGenerator
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
            if (3 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (2 < 2) {
            throw null;
        }
        while (i < (0xEC ^ 0xAC)) {
            final BlockPos add = blockPos.add(random.nextInt(0x2D ^ 0x25) - random.nextInt(0x2B ^ 0x23), random.nextInt(0x7E ^ 0x7A) - random.nextInt(0xA7 ^ 0xA3), random.nextInt(0x6B ^ 0x63) - random.nextInt(0xC9 ^ 0xC1));
            if (world.isAirBlock(add) && world.getBlockState(add.down()).getBlock() == Blocks.netherrack) {
                world.setBlockState(add, Blocks.fire.getDefaultState(), "  ".length());
            }
            ++i;
        }
        return " ".length() != 0;
    }
}
