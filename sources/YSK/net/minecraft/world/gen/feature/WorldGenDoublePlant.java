package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;

public class WorldGenDoublePlant extends WorldGenerator
{
    private BlockDoublePlant.EnumPlantType field_150549_a;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        int n = "".length();
        int i = "".length();
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (i < (0xD5 ^ 0x95)) {
            final BlockPos add = blockPos.add(random.nextInt(0x3E ^ 0x36) - random.nextInt(0xB4 ^ 0xBC), random.nextInt(0x36 ^ 0x32) - random.nextInt(0x31 ^ 0x35), random.nextInt(0x2E ^ 0x26) - random.nextInt(0x6F ^ 0x67));
            if (world.isAirBlock(add) && (!world.provider.getHasNoSky() || add.getY() < 216 + 188 - 257 + 107) && Blocks.double_plant.canPlaceBlockAt(world, add)) {
                Blocks.double_plant.placeAt(world, add, this.field_150549_a, "  ".length());
                n = " ".length();
            }
            ++i;
        }
        return n != 0;
    }
    
    public void setPlantType(final BlockDoublePlant.EnumPlantType field_150549_a) {
        this.field_150549_a = field_150549_a;
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
