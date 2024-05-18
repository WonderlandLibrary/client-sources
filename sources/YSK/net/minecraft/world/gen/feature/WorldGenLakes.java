package net.minecraft.world.gen.feature;

import net.minecraft.block.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.block.material.*;
import net.minecraft.block.state.*;

public class WorldGenLakes extends WorldGenerator
{
    private Block block;
    
    @Override
    public boolean generate(final World world, final Random random, BlockPos blockPos) {
        blockPos = blockPos.add(-(0xC8 ^ 0xC0), "".length(), -(0x4D ^ 0x45));
        "".length();
        if (-1 == 4) {
            throw null;
        }
        while (blockPos.getY() > (0xC4 ^ 0xC1) && world.isAirBlock(blockPos)) {
            blockPos = blockPos.down();
        }
        if (blockPos.getY() <= (0xAF ^ 0xAB)) {
            return "".length() != 0;
        }
        blockPos = blockPos.down(0x6E ^ 0x6A);
        final boolean[] array = new boolean[1288 + 1658 - 1328 + 430];
        final int n = random.nextInt(0x26 ^ 0x22) + (0xAD ^ 0xA9);
        int i = "".length();
        "".length();
        if (3 == 2) {
            throw null;
        }
        while (i < n) {
            final double n2 = random.nextDouble() * 6.0 + 3.0;
            final double n3 = random.nextDouble() * 4.0 + 2.0;
            final double n4 = random.nextDouble() * 6.0 + 3.0;
            final double n5 = random.nextDouble() * (16.0 - n2 - 2.0) + 1.0 + n2 / 2.0;
            final double n6 = random.nextDouble() * (8.0 - n3 - 4.0) + 2.0 + n3 / 2.0;
            final double n7 = random.nextDouble() * (16.0 - n4 - 2.0) + 1.0 + n4 / 2.0;
            int j = " ".length();
            "".length();
            if (3 == 4) {
                throw null;
            }
            while (j < (0xAC ^ 0xA3)) {
                int k = " ".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
                while (k < (0x7E ^ 0x71)) {
                    int l = " ".length();
                    "".length();
                    if (2 <= 0) {
                        throw null;
                    }
                    while (l < (0x64 ^ 0x63)) {
                        final double n8 = (j - n5) / (n2 / 2.0);
                        final double n9 = (l - n6) / (n3 / 2.0);
                        final double n10 = (k - n7) / (n4 / 2.0);
                        if (n8 * n8 + n9 * n9 + n10 * n10 < 1.0) {
                            array[(j * (0x44 ^ 0x54) + k) * (0xB6 ^ 0xBE) + l] = (" ".length() != 0);
                        }
                        ++l;
                    }
                    ++k;
                }
                ++j;
            }
            ++i;
        }
        int length = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (length < (0x7F ^ 0x6F)) {
            int length2 = "".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
            while (length2 < (0x84 ^ 0x94)) {
                int length3 = "".length();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
                while (length3 < (0x78 ^ 0x70)) {
                    int n11;
                    if (!array[(length * (0x55 ^ 0x45) + length2) * (0x1C ^ 0x14) + length3] && ((length < (0x8C ^ 0x83) && array[((length + " ".length()) * (0x9C ^ 0x8C) + length2) * (0x28 ^ 0x20) + length3]) || (length > 0 && array[((length - " ".length()) * (0x4D ^ 0x5D) + length2) * (0x3D ^ 0x35) + length3]) || (length2 < (0x41 ^ 0x4E) && array[(length * (0x93 ^ 0x83) + length2 + " ".length()) * (0x4E ^ 0x46) + length3]) || (length2 > 0 && array[(length * (0x1E ^ 0xE) + (length2 - " ".length())) * (0x37 ^ 0x3F) + length3]) || (length3 < (0x16 ^ 0x11) && array[(length * (0x42 ^ 0x52) + length2) * (0x32 ^ 0x3A) + length3 + " ".length()]) || (length3 > 0 && array[(length * (0x15 ^ 0x5) + length2) * (0x1 ^ 0x9) + (length3 - " ".length())]))) {
                        n11 = " ".length();
                        "".length();
                        if (4 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        n11 = "".length();
                    }
                    if (n11 != 0) {
                        final Material material = world.getBlockState(blockPos.add(length, length3, length2)).getBlock().getMaterial();
                        if (length3 >= (0x86 ^ 0x82) && material.isLiquid()) {
                            return "".length() != 0;
                        }
                        if (length3 < (0x95 ^ 0x91) && !material.isSolid() && world.getBlockState(blockPos.add(length, length3, length2)).getBlock() != this.block) {
                            return "".length() != 0;
                        }
                    }
                    ++length3;
                }
                ++length2;
            }
            ++length;
        }
        int length4 = "".length();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (length4 < (0x11 ^ 0x1)) {
            int length5 = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (length5 < (0x43 ^ 0x53)) {
                int length6 = "".length();
                "".length();
                if (1 >= 3) {
                    throw null;
                }
                while (length6 < (0x77 ^ 0x7F)) {
                    if (array[(length4 * (0x18 ^ 0x8) + length5) * (0x13 ^ 0x1B) + length6]) {
                        final BlockPos add = blockPos.add(length4, length6, length5);
                        IBlockState blockState;
                        if (length6 >= (0x2D ^ 0x29)) {
                            blockState = Blocks.air.getDefaultState();
                            "".length();
                            if (-1 >= 3) {
                                throw null;
                            }
                        }
                        else {
                            blockState = this.block.getDefaultState();
                        }
                        world.setBlockState(add, blockState, "  ".length());
                    }
                    ++length6;
                }
                ++length5;
            }
            ++length4;
        }
        int length7 = "".length();
        "".length();
        if (1 == 2) {
            throw null;
        }
        while (length7 < (0x3B ^ 0x2B)) {
            int length8 = "".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (length8 < (0x82 ^ 0x92)) {
                int n12 = 0x39 ^ 0x3D;
                "".length();
                if (2 < -1) {
                    throw null;
                }
                while (n12 < (0x1C ^ 0x14)) {
                    if (array[(length7 * (0xA9 ^ 0xB9) + length8) * (0x2D ^ 0x25) + n12]) {
                        final BlockPos add2 = blockPos.add(length7, n12 - " ".length(), length8);
                        if (world.getBlockState(add2).getBlock() == Blocks.dirt && world.getLightFor(EnumSkyBlock.SKY, blockPos.add(length7, n12, length8)) > 0) {
                            if (world.getBiomeGenForCoords(add2).topBlock.getBlock() == Blocks.mycelium) {
                                world.setBlockState(add2, Blocks.mycelium.getDefaultState(), "  ".length());
                                "".length();
                                if (3 <= 2) {
                                    throw null;
                                }
                            }
                            else {
                                world.setBlockState(add2, Blocks.grass.getDefaultState(), "  ".length());
                            }
                        }
                    }
                    ++n12;
                }
                ++length8;
            }
            ++length7;
        }
        if (this.block.getMaterial() == Material.lava) {
            int length9 = "".length();
            "".length();
            if (3 <= 0) {
                throw null;
            }
            while (length9 < (0xAA ^ 0xBA)) {
                int length10 = "".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                while (length10 < (0x59 ^ 0x49)) {
                    int length11 = "".length();
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                    while (length11 < (0xCF ^ 0xC7)) {
                        int n13;
                        if (!array[(length9 * (0xD6 ^ 0xC6) + length10) * (0x68 ^ 0x60) + length11] && ((length9 < (0x4C ^ 0x43) && array[((length9 + " ".length()) * (0x18 ^ 0x8) + length10) * (0x7F ^ 0x77) + length11]) || (length9 > 0 && array[((length9 - " ".length()) * (0xB6 ^ 0xA6) + length10) * (0x51 ^ 0x59) + length11]) || (length10 < (0xB7 ^ 0xB8) && array[(length9 * (0x65 ^ 0x75) + length10 + " ".length()) * (0x15 ^ 0x1D) + length11]) || (length10 > 0 && array[(length9 * (0x85 ^ 0x95) + (length10 - " ".length())) * (0x57 ^ 0x5F) + length11]) || (length11 < (0x37 ^ 0x30) && array[(length9 * (0x13 ^ 0x3) + length10) * (0x63 ^ 0x6B) + length11 + " ".length()]) || (length11 > 0 && array[(length9 * (0x7F ^ 0x6F) + length10) * (0x6F ^ 0x67) + (length11 - " ".length())]))) {
                            n13 = " ".length();
                            "".length();
                            if (3 == 2) {
                                throw null;
                            }
                        }
                        else {
                            n13 = "".length();
                        }
                        if (n13 != 0 && (length11 < (0x4E ^ 0x4A) || random.nextInt("  ".length()) != 0) && world.getBlockState(blockPos.add(length9, length11, length10)).getBlock().getMaterial().isSolid()) {
                            world.setBlockState(blockPos.add(length9, length11, length10), Blocks.stone.getDefaultState(), "  ".length());
                        }
                        ++length11;
                    }
                    ++length10;
                }
                ++length9;
            }
        }
        if (this.block.getMaterial() == Material.water) {
            int length12 = "".length();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (length12 < (0xBF ^ 0xAF)) {
                int length13 = "".length();
                "".length();
                if (2 != 2) {
                    throw null;
                }
                while (length13 < (0x66 ^ 0x76)) {
                    final int n14 = 0x21 ^ 0x25;
                    if (world.canBlockFreezeWater(blockPos.add(length12, n14, length13))) {
                        world.setBlockState(blockPos.add(length12, n14, length13), Blocks.ice.getDefaultState(), "  ".length());
                    }
                    ++length13;
                }
                ++length12;
            }
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public WorldGenLakes(final Block block) {
        this.block = block;
    }
}
