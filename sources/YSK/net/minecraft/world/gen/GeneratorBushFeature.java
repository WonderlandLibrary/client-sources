package net.minecraft.world.gen;

import net.minecraft.world.gen.feature.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;

public class GeneratorBushFeature extends WorldGenerator
{
    private BlockBush field_175908_a;
    
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
            if (0 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (i < (0x17 ^ 0x57)) {
            final BlockPos add = blockPos.add(random.nextInt(0x80 ^ 0x88) - random.nextInt(0xAC ^ 0xA4), random.nextInt(0xB8 ^ 0xBC) - random.nextInt(0x44 ^ 0x40), random.nextInt(0x66 ^ 0x6E) - random.nextInt(0x1E ^ 0x16));
            if (world.isAirBlock(add) && (!world.provider.getHasNoSky() || add.getY() < 42 + 123 - 101 + 191) && this.field_175908_a.canBlockStay(world, add, this.field_175908_a.getDefaultState())) {
                world.setBlockState(add, this.field_175908_a.getDefaultState(), "  ".length());
            }
            ++i;
        }
        return " ".length() != 0;
    }
    
    public GeneratorBushFeature(final BlockBush field_175908_a) {
        this.field_175908_a = field_175908_a;
    }
}
