package net.minecraft.world.gen.feature;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;

public class WorldGenTallGrass extends WorldGenerator
{
    private final IBlockState tallGrassState;
    
    public WorldGenTallGrass(final BlockTallGrass.EnumType enumType) {
        this.tallGrassState = Blocks.tallgrass.getDefaultState().withProperty(BlockTallGrass.TYPE, enumType);
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
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos down) {
        "".length();
        if (2 != 2) {
            throw null;
        }
        Block block;
        while (((block = world.getBlockState(down).getBlock()).getMaterial() == Material.air || block.getMaterial() == Material.leaves) && down.getY() > 0) {
            down = down.down();
        }
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < 63 + 99 - 100 + 66) {
            final BlockPos add = down.add(random.nextInt(0x29 ^ 0x21) - random.nextInt(0xCD ^ 0xC5), random.nextInt(0xA2 ^ 0xA6) - random.nextInt(0xC0 ^ 0xC4), random.nextInt(0x9 ^ 0x1) - random.nextInt(0x6A ^ 0x62));
            if (world.isAirBlock(add) && Blocks.tallgrass.canBlockStay(world, add, this.tallGrassState)) {
                world.setBlockState(add, this.tallGrassState, "  ".length());
            }
            ++i;
        }
        return " ".length() != 0;
    }
}
