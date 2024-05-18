package net.minecraft.world.gen;

import net.minecraft.world.chunk.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.state.*;
import net.minecraft.world.*;

public class MapGenCavesHell extends MapGenBase
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
            if (0 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void func_180704_a(final long n, final int n2, final int n3, final ChunkPrimer chunkPrimer, double n4, double n5, double n6, final float n7, float n8, float n9, int i, int n10, final double n11) {
        final double n12 = n2 * (0xA1 ^ 0xB1) + (0x4 ^ 0xC);
        final double n13 = n3 * (0x33 ^ 0x23) + (0xCE ^ 0xC6);
        float n14 = 0.0f;
        float n15 = 0.0f;
        final Random random = new Random(n);
        if (n10 <= 0) {
            final int n16 = this.range * (0x36 ^ 0x26) - (0xD3 ^ 0xC3);
            n10 = n16 - random.nextInt(n16 / (0xA3 ^ 0xA7));
        }
        int n17 = "".length();
        if (i == -" ".length()) {
            i = n10 / "  ".length();
            n17 = " ".length();
        }
        final int n18 = random.nextInt(n10 / "  ".length()) + n10 / (0xC5 ^ 0xC1);
        int n19;
        if (random.nextInt(0x51 ^ 0x57) == 0) {
            n19 = " ".length();
            "".length();
            if (3 == 1) {
                throw null;
            }
        }
        else {
            n19 = "".length();
        }
        final int n20 = n19;
        "".length();
        if (3 < 1) {
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
                if (0 < -1) {
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
            if (n17 == 0 && i == n18 && n7 > 1.0f) {
                this.func_180704_a(random.nextLong(), n2, n3, chunkPrimer, n4, n5, n6, random.nextFloat() * 0.5f + 0.5f, n8 - 1.5707964f, n9 / 3.0f, i, n10, 1.0);
                this.func_180704_a(random.nextLong(), n2, n3, chunkPrimer, n4, n5, n6, random.nextFloat() * 0.5f + 0.5f, n8 + 1.5707964f, n9 / 3.0f, i, n10, 1.0);
                return;
            }
            if (n17 != 0 || random.nextInt(0x38 ^ 0x3C) != 0) {
                final double n25 = n4 - n12;
                final double n26 = n6 - n13;
                final double n27 = n10 - i;
                final double n28 = n7 + 2.0f + 16.0f;
                if (n25 * n25 + n26 * n26 - n27 * n27 > n28 * n28) {
                    return;
                }
                if (n4 >= n12 - 16.0 - n21 * 2.0 && n6 >= n13 - 16.0 - n21 * 2.0 && n4 <= n12 + 16.0 + n21 * 2.0 && n6 <= n13 + 16.0 + n21 * 2.0) {
                    int length = MathHelper.floor_double(n4 - n21) - n2 * (0x22 ^ 0x32) - " ".length();
                    int n29 = MathHelper.floor_double(n4 + n21) - n2 * (0x24 ^ 0x34) + " ".length();
                    int length2 = MathHelper.floor_double(n5 - n22) - " ".length();
                    int n30 = MathHelper.floor_double(n5 + n22) + " ".length();
                    int length3 = MathHelper.floor_double(n6 - n21) - n3 * (0x5B ^ 0x4B) - " ".length();
                    int n31 = MathHelper.floor_double(n6 + n21) - n3 * (0xA9 ^ 0xB9) + " ".length();
                    if (length < 0) {
                        length = "".length();
                    }
                    if (n29 > (0x70 ^ 0x60)) {
                        n29 = (0x37 ^ 0x27);
                    }
                    if (length2 < " ".length()) {
                        length2 = " ".length();
                    }
                    if (n30 > (0x7E ^ 0x6)) {
                        n30 = (0x16 ^ 0x6E);
                    }
                    if (length3 < 0) {
                        length3 = "".length();
                    }
                    if (n31 > (0x7A ^ 0x6A)) {
                        n31 = (0x45 ^ 0x55);
                    }
                    int n32 = "".length();
                    int n33 = length;
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                    while (n32 == 0 && n33 < n29) {
                        int n34 = length3;
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                        while (n32 == 0 && n34 < n31) {
                            int n35 = n30 + " ".length();
                            "".length();
                            if (2 == 4) {
                                throw null;
                            }
                            while (n32 == 0 && n35 >= length2 - " ".length()) {
                                if (n35 >= 0 && n35 < 44 + 84 - 10 + 10) {
                                    final IBlockState blockState = chunkPrimer.getBlockState(n33, n35, n34);
                                    if (blockState.getBlock() == Blocks.flowing_lava || blockState.getBlock() == Blocks.lava) {
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
                        int j = length;
                        "".length();
                        if (1 <= 0) {
                            throw null;
                        }
                        while (j < n29) {
                            final double n36 = (j + n2 * (0x2A ^ 0x3A) + 0.5 - n4) / n21;
                            int k = length3;
                            "".length();
                            if (3 == 1) {
                                throw null;
                            }
                            while (k < n31) {
                                final double n37 = (k + n3 * (0xAC ^ 0xBC) + 0.5 - n6) / n21;
                                int l = n30;
                                "".length();
                                if (2 != 2) {
                                    throw null;
                                }
                                while (l > length2) {
                                    final double n38 = (l - " ".length() + 0.5 - n5) / n22;
                                    if (n38 > -0.7 && n36 * n36 + n38 * n38 + n37 * n37 < 1.0) {
                                        final IBlockState blockState2 = chunkPrimer.getBlockState(j, l, k);
                                        if (blockState2.getBlock() == Blocks.netherrack || blockState2.getBlock() == Blocks.dirt || blockState2.getBlock() == Blocks.grass) {
                                            chunkPrimer.setBlockState(j, l, k, Blocks.air.getDefaultState());
                                        }
                                    }
                                    --l;
                                }
                                ++k;
                            }
                            ++j;
                        }
                        if (n17 != 0) {
                            "".length();
                            if (3 != 3) {
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
    
    protected void func_180705_a(final long n, final int n2, final int n3, final ChunkPrimer chunkPrimer, final double n4, final double n5, final double n6) {
        this.func_180704_a(n, n2, n3, chunkPrimer, n4, n5, n6, 1.0f + this.rand.nextFloat() * 6.0f, 0.0f, 0.0f, -" ".length(), -" ".length(), 0.5);
    }
    
    @Override
    protected void recursiveGenerate(final World world, final int n, final int n2, final int n3, final int n4, final ChunkPrimer chunkPrimer) {
        int n5 = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(0xBA ^ 0xB0) + " ".length()) + " ".length());
        if (this.rand.nextInt(0xF ^ 0xA) != 0) {
            n5 = "".length();
        }
        int i = "".length();
        "".length();
        if (-1 == 1) {
            throw null;
        }
        while (i < n5) {
            final double n6 = n * (0x2A ^ 0x3A) + this.rand.nextInt(0x8D ^ 0x9D);
            final double n7 = this.rand.nextInt(37 + 65 - 12 + 38);
            final double n8 = n2 * (0x8 ^ 0x18) + this.rand.nextInt(0x1F ^ 0xF);
            int length = " ".length();
            if (this.rand.nextInt(0x3A ^ 0x3E) == 0) {
                this.func_180705_a(this.rand.nextLong(), n3, n4, chunkPrimer, n6, n7, n8);
                length += this.rand.nextInt(0x33 ^ 0x37);
            }
            int j = "".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
            while (j < length) {
                this.func_180704_a(this.rand.nextLong(), n3, n4, chunkPrimer, n6, n7, n8, (this.rand.nextFloat() * 2.0f + this.rand.nextFloat()) * 2.0f, this.rand.nextFloat() * 3.1415927f * 2.0f, (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f, "".length(), "".length(), 0.5);
                ++j;
            }
            ++i;
        }
    }
}
