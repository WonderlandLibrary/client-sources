package net.minecraft.world.gen;

import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.block.material.*;
import java.util.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class MapGenCaves extends MapGenBase
{
    @Override
    protected void recursiveGenerate(final World world, final int n, final int n2, final int n3, final int n4, final ChunkPrimer chunkPrimer) {
        int n5 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(0x43 ^ 0x4C) + " ".length()) + " ".length());
        if (this.rand.nextInt(0x59 ^ 0x5E) != 0) {
            n5 = "".length();
        }
        int i = "".length();
        "".length();
        if (true != true) {
            throw null;
        }
        while (i < n5) {
            final double n6 = n * (0x57 ^ 0x47) + this.rand.nextInt(0x84 ^ 0x94);
            final double n7 = this.rand.nextInt(this.rand.nextInt(0xF9 ^ 0x81) + (0x83 ^ 0x8B));
            final double n8 = n2 * (0xD2 ^ 0xC2) + this.rand.nextInt(0xA7 ^ 0xB7);
            int length = " ".length();
            if (this.rand.nextInt(0x4F ^ 0x4B) == 0) {
                this.func_180703_a(this.rand.nextLong(), n3, n4, chunkPrimer, n6, n7, n8);
                length += this.rand.nextInt(0x95 ^ 0x91);
            }
            int j = "".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
            while (j < length) {
                final float n9 = this.rand.nextFloat() * 3.1415927f * 2.0f;
                final float n10 = (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f;
                float n11 = this.rand.nextFloat() * 2.0f + this.rand.nextFloat();
                if (this.rand.nextInt(0xA6 ^ 0xAC) == 0) {
                    n11 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0f + 1.0f;
                }
                this.func_180702_a(this.rand.nextLong(), n3, n4, chunkPrimer, n6, n7, n8, n11, n9, n10, "".length(), "".length(), 1.0);
                ++j;
            }
            ++i;
        }
    }
    
    protected void func_180703_a(final long n, final int n2, final int n3, final ChunkPrimer chunkPrimer, final double n4, final double n5, final double n6) {
        this.func_180702_a(n, n2, n3, chunkPrimer, n4, n5, n6, 1.0f + this.rand.nextFloat() * 6.0f, 0.0f, 0.0f, -" ".length(), -" ".length(), 0.5);
    }
    
    protected boolean func_175793_a(final IBlockState blockState, final IBlockState blockState2) {
        int n;
        if (blockState.getBlock() == Blocks.stone) {
            n = " ".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else if (blockState.getBlock() == Blocks.dirt) {
            n = " ".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else if (blockState.getBlock() == Blocks.grass) {
            n = " ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else if (blockState.getBlock() == Blocks.hardened_clay) {
            n = " ".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (blockState.getBlock() == Blocks.stained_hardened_clay) {
            n = " ".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else if (blockState.getBlock() == Blocks.sandstone) {
            n = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else if (blockState.getBlock() == Blocks.red_sandstone) {
            n = " ".length();
            "".length();
            if (2 <= -1) {
                throw null;
            }
        }
        else if (blockState.getBlock() == Blocks.mycelium) {
            n = " ".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (blockState.getBlock() == Blocks.snow_layer) {
            n = " ".length();
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if ((blockState.getBlock() == Blocks.sand || blockState.getBlock() == Blocks.gravel) && blockState2.getBlock().getMaterial() != Material.water) {
            n = " ".length();
            "".length();
            if (3 < 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    protected void func_180702_a(final long n, final int n2, final int n3, final ChunkPrimer chunkPrimer, double n4, double n5, double n6, final float n7, float n8, float n9, int i, int n10, final double n11) {
        final double n12 = n2 * (0x65 ^ 0x75) + (0x5 ^ 0xD);
        final double n13 = n3 * (0xAD ^ 0xBD) + (0x0 ^ 0x8);
        float n14 = 0.0f;
        float n15 = 0.0f;
        final Random random = new Random(n);
        if (n10 <= 0) {
            final int n16 = this.range * (0x4D ^ 0x5D) - (0xBE ^ 0xAE);
            n10 = n16 - random.nextInt(n16 / (0x58 ^ 0x5C));
        }
        int n17 = "".length();
        if (i == -" ".length()) {
            i = n10 / "  ".length();
            n17 = " ".length();
        }
        final int n18 = random.nextInt(n10 / "  ".length()) + n10 / (0x47 ^ 0x43);
        int n19;
        if (random.nextInt(0x12 ^ 0x14) == 0) {
            n19 = " ".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            n19 = "".length();
        }
        final int n20 = n19;
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i < n10) {
            final double n21 = 1.5 + MathHelper.sin(i * 3.1415927f / n10) * n7 * 1.0f;
            final double n22 = n21 * n11;
            final float cos = MathHelper.cos(n9);
            final float sin = MathHelper.sin(n9);
            n4 += MathHelper.cos(n8) * cos;
            n5 += sin;
            n6 += MathHelper.sin(n8) * cos;
            if (n20 != 0) {
                n9 *= 0.92f;
                "".length();
                if (3 < -1) {
                    throw null;
                }
            }
            else {
                n9 *= 0.7f;
            }
            n9 += n15 * 0.1f;
            n8 += n14 * 0.1f;
            final float n23 = n15 * 0.9f;
            final float n24 = n14 * 0.75f;
            n15 = n23 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0f;
            n14 = n24 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0f;
            if (n17 == 0 && i == n18 && n7 > 1.0f && n10 > 0) {
                this.func_180702_a(random.nextLong(), n2, n3, chunkPrimer, n4, n5, n6, random.nextFloat() * 0.5f + 0.5f, n8 - 1.5707964f, n9 / 3.0f, i, n10, 1.0);
                this.func_180702_a(random.nextLong(), n2, n3, chunkPrimer, n4, n5, n6, random.nextFloat() * 0.5f + 0.5f, n8 + 1.5707964f, n9 / 3.0f, i, n10, 1.0);
                return;
            }
            if (n17 != 0 || random.nextInt(0x59 ^ 0x5D) != 0) {
                final double n25 = n4 - n12;
                final double n26 = n6 - n13;
                final double n27 = n10 - i;
                final double n28 = n7 + 2.0f + 16.0f;
                if (n25 * n25 + n26 * n26 - n27 * n27 > n28 * n28) {
                    return;
                }
                if (n4 >= n12 - 16.0 - n21 * 2.0 && n6 >= n13 - 16.0 - n21 * 2.0 && n4 <= n12 + 16.0 + n21 * 2.0 && n6 <= n13 + 16.0 + n21 * 2.0) {
                    int length = MathHelper.floor_double(n4 - n21) - n2 * (0xB1 ^ 0xA1) - " ".length();
                    int n29 = MathHelper.floor_double(n4 + n21) - n2 * (0x98 ^ 0x88) + " ".length();
                    int length2 = MathHelper.floor_double(n5 - n22) - " ".length();
                    int n30 = MathHelper.floor_double(n5 + n22) + " ".length();
                    int length3 = MathHelper.floor_double(n6 - n21) - n3 * (0x4C ^ 0x5C) - " ".length();
                    int n31 = MathHelper.floor_double(n6 + n21) - n3 * (0x1 ^ 0x11) + " ".length();
                    if (length < 0) {
                        length = "".length();
                    }
                    if (n29 > (0x32 ^ 0x22)) {
                        n29 = (0x76 ^ 0x66);
                    }
                    if (length2 < " ".length()) {
                        length2 = " ".length();
                    }
                    if (n30 > 188 + 116 - 168 + 112) {
                        n30 = 113 + 8 - 11 + 138;
                    }
                    if (length3 < 0) {
                        length3 = "".length();
                    }
                    if (n31 > (0xB4 ^ 0xA4)) {
                        n31 = (0x36 ^ 0x26);
                    }
                    int n32 = "".length();
                    int n33 = length;
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                    while (n32 == 0 && n33 < n29) {
                        int n34 = length3;
                        "".length();
                        if (4 == 0) {
                            throw null;
                        }
                        while (n32 == 0 && n34 < n31) {
                            int n35 = n30 + " ".length();
                            "".length();
                            if (3 == 1) {
                                throw null;
                            }
                            while (n32 == 0 && n35 >= length2 - " ".length()) {
                                if (n35 >= 0 && n35 < 232 + 159 - 174 + 39) {
                                    final IBlockState blockState = chunkPrimer.getBlockState(n33, n35, n34);
                                    if (blockState.getBlock() == Blocks.flowing_water || blockState.getBlock() == Blocks.water) {
                                        n32 = " ".length();
                                    }
                                    if (n35 != length2 - " ".length() && n33 != length && n33 != n29 - " ".length() && n34 != length3 && n34 != n31 - " ".length()) {
                                        n35 = length2;
                                    }
                                }
                                --n35;
                            }
                            ++n34;
                        }
                        ++n33;
                    }
                    if (n32 == 0) {
                        final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
                        int j = length;
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                        while (j < n29) {
                            final double n36 = (j + n2 * (0x6B ^ 0x7B) + 0.5 - n4) / n21;
                            int k = length3;
                            "".length();
                            if (2 >= 3) {
                                throw null;
                            }
                            while (k < n31) {
                                final double n37 = (k + n3 * (0x13 ^ 0x3) + 0.5 - n6) / n21;
                                int n38 = "".length();
                                if (n36 * n36 + n37 * n37 < 1.0) {
                                    int l = n30;
                                    "".length();
                                    if (3 != 3) {
                                        throw null;
                                    }
                                    while (l > length2) {
                                        final double n39 = (l - " ".length() + 0.5 - n5) / n22;
                                        if (n39 > -0.7 && n36 * n36 + n39 * n39 + n37 * n37 < 1.0) {
                                            final IBlockState blockState2 = chunkPrimer.getBlockState(j, l, k);
                                            final IBlockState blockState3 = (IBlockState)Objects.firstNonNull((Object)chunkPrimer.getBlockState(j, l + " ".length(), k), (Object)Blocks.air.getDefaultState());
                                            if (blockState2.getBlock() == Blocks.grass || blockState2.getBlock() == Blocks.mycelium) {
                                                n38 = " ".length();
                                            }
                                            if (this.func_175793_a(blockState2, blockState3)) {
                                                if (l - " ".length() < (0x86 ^ 0x8C)) {
                                                    chunkPrimer.setBlockState(j, l, k, Blocks.lava.getDefaultState());
                                                    "".length();
                                                    if (2 == 4) {
                                                        throw null;
                                                    }
                                                }
                                                else {
                                                    chunkPrimer.setBlockState(j, l, k, Blocks.air.getDefaultState());
                                                    if (blockState3.getBlock() == Blocks.sand) {
                                                        final int n40 = j;
                                                        final int n41 = l + " ".length();
                                                        final int n42 = k;
                                                        IBlockState blockState4;
                                                        if (blockState3.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) {
                                                            blockState4 = Blocks.red_sandstone.getDefaultState();
                                                            "".length();
                                                            if (4 < -1) {
                                                                throw null;
                                                            }
                                                        }
                                                        else {
                                                            blockState4 = Blocks.sandstone.getDefaultState();
                                                        }
                                                        chunkPrimer.setBlockState(n40, n41, n42, blockState4);
                                                    }
                                                    if (n38 != 0 && chunkPrimer.getBlockState(j, l - " ".length(), k).getBlock() == Blocks.dirt) {
                                                        mutableBlockPos.func_181079_c(j + n2 * (0x2C ^ 0x3C), "".length(), k + n3 * (0xB5 ^ 0xA5));
                                                        chunkPrimer.setBlockState(j, l - " ".length(), k, this.worldObj.getBiomeGenForCoords(mutableBlockPos).topBlock.getBlock().getDefaultState());
                                                    }
                                                }
                                            }
                                        }
                                        --l;
                                    }
                                }
                                ++k;
                            }
                            ++j;
                        }
                        if (n17 != 0) {
                            "".length();
                            if (3 < 1) {
                                throw null;
                            }
                            break;
                        }
                    }
                }
            }
            ++i;
        }
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
}
