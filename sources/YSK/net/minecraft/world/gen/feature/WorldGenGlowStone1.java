package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import net.minecraft.util.*;

public class WorldGenGlowStone1 extends WorldGenerator
{
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
        if (4 <= 2) {
            throw null;
        }
        while (i < 285 + 651 - 649 + 1213) {
            final BlockPos add = blockPos.add(random.nextInt(0x46 ^ 0x4E) - random.nextInt(0xB2 ^ 0xBA), -random.nextInt(0x4F ^ 0x43), random.nextInt(0xB3 ^ 0xBB) - random.nextInt(0xBE ^ 0xB6));
            if (world.getBlockState(add).getBlock().getMaterial() == Material.air) {
                int length = "".length();
                final EnumFacing[] values;
                final int length2 = (values = EnumFacing.values()).length;
                int j = "".length();
                "".length();
                if (false) {
                    throw null;
                }
                while (j < length2) {
                    if (world.getBlockState(add.offset(values[j])).getBlock() == Blocks.glowstone) {
                        ++length;
                    }
                    if (length > " ".length()) {
                        "".length();
                        if (-1 == 4) {
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
