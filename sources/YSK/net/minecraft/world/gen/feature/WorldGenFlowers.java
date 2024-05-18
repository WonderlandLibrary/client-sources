package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;

public class WorldGenFlowers extends WorldGenerator
{
    private IBlockState field_175915_b;
    private BlockFlower flower;
    
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
            if (-1 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public WorldGenFlowers(final BlockFlower blockFlower, final BlockFlower.EnumFlowerType enumFlowerType) {
        this.setGeneratedBlock(blockFlower, enumFlowerType);
    }
    
    public void setGeneratedBlock(final BlockFlower flower, final BlockFlower.EnumFlowerType enumFlowerType) {
        this.flower = flower;
        this.field_175915_b = flower.getDefaultState().withProperty(flower.getTypeProperty(), enumFlowerType);
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < (0x27 ^ 0x67)) {
            final BlockPos add = blockPos.add(random.nextInt(0x89 ^ 0x81) - random.nextInt(0x5 ^ 0xD), random.nextInt(0xB9 ^ 0xBD) - random.nextInt(0x10 ^ 0x14), random.nextInt(0x2D ^ 0x25) - random.nextInt(0x80 ^ 0x88));
            if (world.isAirBlock(add) && (!world.provider.getHasNoSky() || add.getY() < 15 + 110 - 67 + 197) && this.flower.canBlockStay(world, add, this.field_175915_b)) {
                world.setBlockState(add, this.field_175915_b, "  ".length());
            }
            ++i;
        }
        return " ".length() != 0;
    }
}
