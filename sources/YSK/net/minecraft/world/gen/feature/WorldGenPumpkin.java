package net.minecraft.world.gen.feature;

import net.minecraft.world.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;

public class WorldGenPumpkin extends WorldGenerator
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
            if (-1 >= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (1 <= 0) {
            throw null;
        }
        while (i < (0xD7 ^ 0x97)) {
            final BlockPos add = blockPos.add(random.nextInt(0x14 ^ 0x1C) - random.nextInt(0x18 ^ 0x10), random.nextInt(0x1F ^ 0x1B) - random.nextInt(0x11 ^ 0x15), random.nextInt(0x2A ^ 0x22) - random.nextInt(0x19 ^ 0x11));
            if (world.isAirBlock(add) && world.getBlockState(add.down()).getBlock() == Blocks.grass && Blocks.pumpkin.canPlaceBlockAt(world, add)) {
                world.setBlockState(add, Blocks.pumpkin.getDefaultState().withProperty((IProperty<Comparable>)BlockPumpkin.FACING, EnumFacing.Plane.HORIZONTAL.random(random)), "  ".length());
            }
            ++i;
        }
        return " ".length() != 0;
    }
}
