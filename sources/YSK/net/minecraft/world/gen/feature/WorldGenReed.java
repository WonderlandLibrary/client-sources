package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class WorldGenReed extends WorldGenerator
{
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (i < (0x7 ^ 0x13)) {
            final BlockPos add = blockPos.add(random.nextInt(0xF ^ 0xB) - random.nextInt(0x1F ^ 0x1B), "".length(), random.nextInt(0xB6 ^ 0xB2) - random.nextInt(0x17 ^ 0x13));
            if (world.isAirBlock(add)) {
                final BlockPos down = add.down();
                if (world.getBlockState(down.west()).getBlock().getMaterial() == Material.water || world.getBlockState(down.east()).getBlock().getMaterial() == Material.water || world.getBlockState(down.north()).getBlock().getMaterial() == Material.water || world.getBlockState(down.south()).getBlock().getMaterial() == Material.water) {
                    final int n = "  ".length() + random.nextInt(random.nextInt("   ".length()) + " ".length());
                    int j = "".length();
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                    while (j < n) {
                        if (Blocks.reeds.canBlockStay(world, add)) {
                            world.setBlockState(add.up(j), Blocks.reeds.getDefaultState(), "  ".length());
                        }
                        ++j;
                    }
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
            if (4 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
