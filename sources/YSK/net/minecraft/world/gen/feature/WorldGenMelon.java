package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class WorldGenMelon extends WorldGenerator
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (3 == 2) {
            throw null;
        }
        while (i < (0xCC ^ 0x8C)) {
            final BlockPos add = blockPos.add(random.nextInt(0x63 ^ 0x6B) - random.nextInt(0x4A ^ 0x42), random.nextInt(0x22 ^ 0x26) - random.nextInt(0x91 ^ 0x95), random.nextInt(0x81 ^ 0x89) - random.nextInt(0x7B ^ 0x73));
            if (Blocks.melon_block.canPlaceBlockAt(world, add) && world.getBlockState(add.down()).getBlock() == Blocks.grass) {
                world.setBlockState(add, Blocks.melon_block.getDefaultState(), "  ".length());
            }
            ++i;
        }
        return " ".length() != 0;
    }
}
