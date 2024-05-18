package net.minecraft.world.gen;

import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import java.util.*;
import net.minecraft.init.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class MapGenRavine extends MapGenBase
{
    private float[] field_75046_d;
    
    @Override
    protected void recursiveGenerate(final World world, final int n, final int n2, final int n3, final int n4, final ChunkPrimer chunkPrimer) {
        if (this.rand.nextInt(0x93 ^ 0xA1) == 0) {
            final double n5 = n * (0x24 ^ 0x34) + this.rand.nextInt(0x68 ^ 0x78);
            final double n6 = this.rand.nextInt(this.rand.nextInt(0xBF ^ 0x97) + (0x35 ^ 0x3D)) + (0x15 ^ 0x1);
            final double n7 = n2 * (0x3D ^ 0x2D) + this.rand.nextInt(0x51 ^ 0x41);
            final int length = " ".length();
            int i = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (i < length) {
                this.func_180707_a(this.rand.nextLong(), n3, n4, chunkPrimer, n5, n6, n7, (this.rand.nextFloat() * 2.0f + this.rand.nextFloat()) * 2.0f, this.rand.nextFloat() * 3.1415927f * 2.0f, (this.rand.nextFloat() - 0.5f) * 2.0f / 8.0f, "".length(), "".length(), 3.0);
                ++i;
            }
        }
    }
    
    protected void func_180707_a(final long n, final int n2, final int n3, final ChunkPrimer chunkPrimer, double n4, double n5, double n6, final float n7, float n8, float n9, int i, int n10, final double n11) {
        final Random random = new Random(n);
        final double n12 = n2 * (0x6B ^ 0x7B) + (0x21 ^ 0x29);
        final double n13 = n3 * (0x7E ^ 0x6E) + (0x16 ^ 0x1E);
        float n14 = 0.0f;
        float n15 = 0.0f;
        if (n10 <= 0) {
            final int n16 = this.range * (0x9 ^ 0x19) - (0x9B ^ 0x8B);
            n10 = n16 - random.nextInt(n16 / (0x9E ^ 0x9A));
        }
        int n17 = "".length();
        if (i == -" ".length()) {
            i = n10 / "  ".length();
            n17 = " ".length();
        }
        float n18 = 1.0f;
        int j = "".length();
        "".length();
        if (4 == 1) {
            throw null;
        }
        while (j < 244 + 80 - 166 + 98) {
            if (j == 0 || random.nextInt("   ".length()) == 0) {
                n18 = 1.0f + random.nextFloat() * random.nextFloat() * 1.0f;
            }
            this.field_75046_d[j] = n18 * n18;
            ++j;
        }
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (i < n10) {
            final double n19 = 1.5 + MathHelper.sin(i * 3.1415927f / n10) * n7 * 1.0f;
            final double n20 = n19 * n11;
            final double n21 = n19 * (random.nextFloat() * 0.25 + 0.75);
            final double n22 = n20 * (random.nextFloat() * 0.25 + 0.75);
            final float cos = MathHelper.cos(n9);
            final float sin = MathHelper.sin(n9);
            n4 += MathHelper.cos(n8) * cos;
            n5 += sin;
            n6 += MathHelper.sin(n8) * cos;
            n9 *= 0.7f;
            n9 += n15 * 0.05f;
            n8 += n14 * 0.05f;
            final float n23 = n15 * 0.8f;
            final float n24 = n14 * 0.5f;
            n15 = n23 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0f;
            n14 = n24 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0f;
            if (n17 != 0 || random.nextInt(0x26 ^ 0x22) != 0) {
                final double n25 = n4 - n12;
                final double n26 = n6 - n13;
                final double n27 = n10 - i;
                final double n28 = n7 + 2.0f + 16.0f;
                if (n25 * n25 + n26 * n26 - n27 * n27 > n28 * n28) {
                    return;
                }
                if (n4 >= n12 - 16.0 - n21 * 2.0 && n6 >= n13 - 16.0 - n21 * 2.0 && n4 <= n12 + 16.0 + n21 * 2.0 && n6 <= n13 + 16.0 + n21 * 2.0) {
                    int length = MathHelper.floor_double(n4 - n21) - n2 * (0x47 ^ 0x57) - " ".length();
                    int n29 = MathHelper.floor_double(n4 + n21) - n2 * (0x84 ^ 0x94) + " ".length();
                    int length2 = MathHelper.floor_double(n5 - n22) - " ".length();
                    int n30 = MathHelper.floor_double(n5 + n22) + " ".length();
                    int length3 = MathHelper.floor_double(n6 - n21) - n3 * (0xB ^ 0x1B) - " ".length();
                    int n31 = MathHelper.floor_double(n6 + n21) - n3 * (0x1B ^ 0xB) + " ".length();
                    if (length < 0) {
                        length = "".length();
                    }
                    if (n29 > (0xBA ^ 0xAA)) {
                        n29 = (0x33 ^ 0x23);
                    }
                    if (length2 < " ".length()) {
                        length2 = " ".length();
                    }
                    if (n30 > 186 + 216 - 372 + 218) {
                        n30 = 114 + 179 - 228 + 183;
                    }
                    if (length3 < 0) {
                        length3 = "".length();
                    }
                    if (n31 > (0xA ^ 0x1A)) {
                        n31 = (0x87 ^ 0x97);
                    }
                    int n32 = "".length();
                    int n33 = length;
                    "".length();
                    if (!true) {
                        throw null;
                    }
                    while (n32 == 0 && n33 < n29) {
                        int n34 = length3;
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                        while (n32 == 0 && n34 < n31) {
                            int n35 = n30 + " ".length();
                            "".length();
                            if (2 >= 3) {
                                throw null;
                            }
                            while (n32 == 0 && n35 >= length2 - " ".length()) {
                                if (n35 >= 0 && n35 < 216 + 249 - 323 + 114) {
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
                        int k = length;
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                        while (k < n29) {
                            final double n36 = (k + n2 * (0xD3 ^ 0xC3) + 0.5 - n4) / n21;
                            int l = length3;
                            "".length();
                            if (2 <= -1) {
                                throw null;
                            }
                            while (l < n31) {
                                final double n37 = (l + n3 * (0x77 ^ 0x67) + 0.5 - n6) / n21;
                                int n38 = "".length();
                                if (n36 * n36 + n37 * n37 < 1.0) {
                                    int n39 = n30;
                                    "".length();
                                    if (4 < 1) {
                                        throw null;
                                    }
                                    while (n39 > length2) {
                                        final double n40 = (n39 - " ".length() + 0.5 - n5) / n22;
                                        if ((n36 * n36 + n37 * n37) * this.field_75046_d[n39 - " ".length()] + n40 * n40 / 6.0 < 1.0) {
                                            final IBlockState blockState2 = chunkPrimer.getBlockState(k, n39, l);
                                            if (blockState2.getBlock() == Blocks.grass) {
                                                n38 = " ".length();
                                            }
                                            if (blockState2.getBlock() == Blocks.stone || blockState2.getBlock() == Blocks.dirt || blockState2.getBlock() == Blocks.grass) {
                                                if (n39 - " ".length() < (0x1A ^ 0x10)) {
                                                    chunkPrimer.setBlockState(k, n39, l, Blocks.flowing_lava.getDefaultState());
                                                    "".length();
                                                    if (false) {
                                                        throw null;
                                                    }
                                                }
                                                else {
                                                    chunkPrimer.setBlockState(k, n39, l, Blocks.air.getDefaultState());
                                                    if (n38 != 0 && chunkPrimer.getBlockState(k, n39 - " ".length(), l).getBlock() == Blocks.dirt) {
                                                        mutableBlockPos.func_181079_c(k + n2 * (0x49 ^ 0x59), "".length(), l + n3 * (0x0 ^ 0x10));
                                                        chunkPrimer.setBlockState(k, n39 - " ".length(), l, this.worldObj.getBiomeGenForCoords(mutableBlockPos).topBlock);
                                                    }
                                                }
                                            }
                                        }
                                        --n39;
                                    }
                                }
                                ++l;
                            }
                            ++k;
                        }
                        if (n17 != 0) {
                            "".length();
                            if (0 == 3) {
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
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public MapGenRavine() {
        this.field_75046_d = new float[294 + 359 - 365 + 736];
    }
}
