package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.*;

public class WorldGenDeadBush extends WorldGenerator
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
    public boolean generate(final World world, final Random random, BlockPos down) {
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        Block block;
        while (((block = world.getBlockState(down).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.leaves) && down.getY() > 0) {
            down = down.down();
        }
        int i = "".length();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (i < (0x85 ^ 0x81)) {
            final BlockPos add = down.add(random.nextInt(0x1A ^ 0x12) - random.nextInt(0x5E ^ 0x56), random.nextInt(0x71 ^ 0x75) - random.nextInt(0x8A ^ 0x8E), random.nextInt(0xCE ^ 0xC6) - random.nextInt(0x62 ^ 0x6A));
            if (world.isAirBlock(add) && Blocks.deadbush.canBlockStay(world, add, Blocks.deadbush.getDefaultState())) {
                world.setBlockState(add, Blocks.deadbush.getDefaultState(), "  ".length());
            }
            ++i;
        }
        return " ".length() != 0;
    }
}
