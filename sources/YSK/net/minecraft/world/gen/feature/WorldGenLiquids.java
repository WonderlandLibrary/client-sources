package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;

public class WorldGenLiquids extends WorldGenerator
{
    private Block block;
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (world.getBlockState(blockPos.up()).getBlock() != Blocks.stone) {
            return "".length() != 0;
        }
        if (world.getBlockState(blockPos.down()).getBlock() != Blocks.stone) {
            return "".length() != 0;
        }
        if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.air && world.getBlockState(blockPos).getBlock() != Blocks.stone) {
            return "".length() != 0;
        }
        int length = "".length();
        if (world.getBlockState(blockPos.west()).getBlock() == Blocks.stone) {
            ++length;
        }
        if (world.getBlockState(blockPos.east()).getBlock() == Blocks.stone) {
            ++length;
        }
        if (world.getBlockState(blockPos.north()).getBlock() == Blocks.stone) {
            ++length;
        }
        if (world.getBlockState(blockPos.south()).getBlock() == Blocks.stone) {
            ++length;
        }
        int length2 = "".length();
        if (world.isAirBlock(blockPos.west())) {
            ++length2;
        }
        if (world.isAirBlock(blockPos.east())) {
            ++length2;
        }
        if (world.isAirBlock(blockPos.north())) {
            ++length2;
        }
        if (world.isAirBlock(blockPos.south())) {
            ++length2;
        }
        if (length == "   ".length() && length2 == " ".length()) {
            world.setBlockState(blockPos, this.block.getDefaultState(), "  ".length());
            world.forceBlockUpdateTick(this.block, blockPos, random);
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
            if (0 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public WorldGenLiquids(final Block block) {
        this.block = block;
    }
}
