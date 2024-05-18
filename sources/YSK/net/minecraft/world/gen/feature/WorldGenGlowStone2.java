package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class WorldGenGlowStone2 extends WorldGenerator
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
            if (3 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (!world.isAirBlock(blockPos)) {
            return "".length() != 0;
        }
        if (world.getBlockState(blockPos.up()).getBlock() != Blocks.netherrack) {
            return "".length() != 0;
        }
        world.setBlockState(blockPos, Blocks.glowstone.getDefaultState(), "  ".length());
        int i = "".length();
        "".length();
        if (2 >= 3) {
            throw null;
        }
        while (i < 1185 + 18 - 491 + 788) {
            final BlockPos add = blockPos.add(random.nextInt(0x9B ^ 0x93) - random.nextInt(0x7E ^ 0x76), -random.nextInt(0x81 ^ 0x8D), random.nextInt(0x19 ^ 0x11) - random.nextInt(0x9B ^ 0x93));
            if (world.getBlockState(add).getBlock().getMaterial() == Material.air) {
                int length = "".length();
                final EnumFacing[] values;
                final int length2 = (values = EnumFacing.values()).length;
                int j = "".length();
                "".length();
                if (4 == -1) {
                    throw null;
                }
                while (j < length2) {
                    if (world.getBlockState(add.offset(values[j])).getBlock() == Blocks.glowstone) {
                        ++length;
                    }
                    if (length > " ".length()) {
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++j;
                    }
                }
                if (length == " ".length()) {
                    world.setBlockState(add, Blocks.glowstone.getDefaultState(), "  ".length());
                }
            }
            ++i;
        }
        return " ".length() != 0;
    }
}
