package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class WorldGenWaterlily extends WorldGenerator
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
            if (1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (i < (0x44 ^ 0x4E)) {
            final int n = blockPos.getX() + random.nextInt(0xC ^ 0x4) - random.nextInt(0xA4 ^ 0xAC);
            final int n2 = blockPos.getY() + random.nextInt(0x9 ^ 0xD) - random.nextInt(0x13 ^ 0x17);
            final int n3 = blockPos.getZ() + random.nextInt(0x6F ^ 0x67) - random.nextInt(0xB5 ^ 0xBD);
            if (world.isAirBlock(new BlockPos(n, n2, n3)) && Blocks.waterlily.canPlaceBlockAt(world, new BlockPos(n, n2, n3))) {
                world.setBlockState(new BlockPos(n, n2, n3), Blocks.waterlily.getDefaultState(), "  ".length());
            }
            ++i;
        }
        return " ".length() != 0;
    }
}
