package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.*;

public class WorldGenSpikes extends WorldGenerator
{
    private Block baseBlockRequired;
    
    public WorldGenSpikes(final Block baseBlockRequired) {
        this.baseBlockRequired = baseBlockRequired;
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean generate(final World world, final Random random, final BlockPos blockPos) {
        if (!world.isAirBlock(blockPos) || world.getBlockState(blockPos.down()).getBlock() != this.baseBlockRequired) {
            return "".length() != 0;
        }
        final int n = random.nextInt(0x6C ^ 0x4C) + (0x95 ^ 0x93);
        final int n2 = random.nextInt(0xC ^ 0x8) + " ".length();
        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int i = blockPos.getX() - n2;
        "".length();
        if (3 != 3) {
            throw null;
        }
        while (i <= blockPos.getX() + n2) {
            int j = blockPos.getZ() - n2;
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (j <= blockPos.getZ() + n2) {
                final int n3 = i - blockPos.getX();
                final int n4 = j - blockPos.getZ();
                if (n3 * n3 + n4 * n4 <= n2 * n2 + " ".length() && world.getBlockState(mutableBlockPos.func_181079_c(i, blockPos.getY() - " ".length(), j)).getBlock() != this.baseBlockRequired) {
                    return "".length() != 0;
                }
                ++j;
            }
            ++i;
        }
        int y = blockPos.getY();
        "".length();
        if (false == true) {
            throw null;
        }
        while (y < blockPos.getY() + n && y < 166 + 253 - 386 + 223) {
            int k = blockPos.getX() - n2;
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (k <= blockPos.getX() + n2) {
                int l = blockPos.getZ() - n2;
                "".length();
                if (!true) {
                    throw null;
                }
                while (l <= blockPos.getZ() + n2) {
                    final int n5 = k - blockPos.getX();
                    final int n6 = l - blockPos.getZ();
                    if (n5 * n5 + n6 * n6 <= n2 * n2 + " ".length()) {
                        world.setBlockState(new BlockPos(k, y, l), Blocks.obsidian.getDefaultState(), "  ".length());
                    }
                    ++l;
                }
                ++k;
            }
            ++y;
        }
        final EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal(world);
        entityEnderCrystal.setLocationAndAngles(blockPos.getX() + 0.5f, blockPos.getY() + n, blockPos.getZ() + 0.5f, random.nextFloat() * 360.0f, 0.0f);
        world.spawnEntityInWorld(entityEnderCrystal);
        world.setBlockState(blockPos.up(n), Blocks.bedrock.getDefaultState(), "  ".length());
        return " ".length() != 0;
    }
}
