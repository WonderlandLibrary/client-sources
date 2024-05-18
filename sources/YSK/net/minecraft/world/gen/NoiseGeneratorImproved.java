package net.minecraft.world.gen;

import java.util.*;

public class NoiseGeneratorImproved extends NoiseGenerator
{
    private static final double[] field_152385_i;
    private int[] permutations;
    private static final double[] field_152384_h;
    public double xCoord;
    private static final double[] field_152382_f;
    private static final double[] field_152383_g;
    public double yCoord;
    public double zCoord;
    private static final double[] field_152381_e;
    
    public final double grad(final int n, final double n2, final double n3, final double n4) {
        final int n5 = n & (0xBB ^ 0xB4);
        return NoiseGeneratorImproved.field_152381_e[n5] * n2 + NoiseGeneratorImproved.field_152382_f[n5] * n3 + NoiseGeneratorImproved.field_152383_g[n5] * n4;
    }
    
    public final double func_76309_a(final int n, final double n2, final double n3) {
        final int n4 = n & (0x5D ^ 0x52);
        return NoiseGeneratorImproved.field_152384_h[n4] * n2 + NoiseGeneratorImproved.field_152385_i[n4] * n3;
    }
    
    public NoiseGeneratorImproved(final Random random) {
        this.permutations = new int[260 + 93 - 190 + 349];
        this.xCoord = random.nextDouble() * 256.0;
        this.yCoord = random.nextDouble() * 256.0;
        this.zCoord = random.nextDouble() * 256.0;
        int i = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (i < 193 + 201 - 314 + 176) {
            this.permutations[i] = i++;
        }
        int j = "".length();
        "".length();
        if (3 < 0) {
            throw null;
        }
        while (j < 19 + 110 - 12 + 139) {
            final int n = random.nextInt(224 + 127 - 127 + 32 - j) + j;
            final int n2 = this.permutations[j];
            this.permutations[j] = this.permutations[n];
            this.permutations[n] = n2;
            this.permutations[j + (33 + 130 + 79 + 14)] = this.permutations[j];
            ++j;
        }
    }
    
    public void populateNoiseArray(final double[] array, final double n, final double n2, final double n3, final int n4, final int n5, final int n6, final double n7, final double n8, final double n9, final double n10) {
        if (n5 == " ".length()) {
            "".length();
            "".length();
            "".length();
            "".length();
            int length = "".length();
            final double n11 = 1.0 / n10;
            int i = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
            while (i < n4) {
                final double n12 = n + i * n7 + this.xCoord;
                int n13 = (int)n12;
                if (n12 < n13) {
                    --n13;
                }
                final int n14 = n13 & 35 + 64 + 100 + 56;
                final double n15 = n12 - n13;
                final double n16 = n15 * n15 * n15 * (n15 * (n15 * 6.0 - 15.0) + 10.0);
                int j = "".length();
                "".length();
                if (4 == -1) {
                    throw null;
                }
                while (j < n6) {
                    final double n17 = n3 + j * n9 + this.zCoord;
                    int n18 = (int)n17;
                    if (n17 < n18) {
                        --n18;
                    }
                    final int n19 = n18 & 193 + 214 - 226 + 74;
                    final double n20 = n17 - n18;
                    final double n21 = n20 * n20 * n20 * (n20 * (n20 * 6.0 - 15.0) + 10.0);
                    final int n22 = this.permutations[this.permutations[n14] + "".length()] + n19;
                    final int n23 = this.permutations[this.permutations[n14 + " ".length()] + "".length()] + n19;
                    final double lerp = this.lerp(n21, this.lerp(n16, this.func_76309_a(this.permutations[n22], n15, n20), this.grad(this.permutations[n23], n15 - 1.0, 0.0, n20)), this.lerp(n16, this.grad(this.permutations[n22 + " ".length()], n15, 0.0, n20 - 1.0), this.grad(this.permutations[n23 + " ".length()], n15 - 1.0, 0.0, n20 - 1.0)));
                    final int n24 = length++;
                    array[n24] += lerp * n11;
                    ++j;
                }
                ++i;
            }
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            int length2 = "".length();
            final double n25 = 1.0 / n10;
            int n26 = -" ".length();
            "".length();
            "".length();
            "".length();
            "".length();
            "".length();
            "".length();
            double lerp2 = 0.0;
            double lerp3 = 0.0;
            double lerp4 = 0.0;
            double lerp5 = 0.0;
            int k = "".length();
            "".length();
            if (2 < -1) {
                throw null;
            }
            while (k < n4) {
                final double n27 = n + k * n7 + this.xCoord;
                int n28 = (int)n27;
                if (n27 < n28) {
                    --n28;
                }
                final int n29 = n28 & 64 + 141 - 103 + 153;
                final double n30 = n27 - n28;
                final double n31 = n30 * n30 * n30 * (n30 * (n30 * 6.0 - 15.0) + 10.0);
                int l = "".length();
                "".length();
                if (0 >= 2) {
                    throw null;
                }
                while (l < n6) {
                    final double n32 = n3 + l * n9 + this.zCoord;
                    int n33 = (int)n32;
                    if (n32 < n33) {
                        --n33;
                    }
                    final int n34 = n33 & 207 + 202 - 204 + 50;
                    final double n35 = n32 - n33;
                    final double n36 = n35 * n35 * n35 * (n35 * (n35 * 6.0 - 15.0) + 10.0);
                    int length3 = "".length();
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                    while (length3 < n5) {
                        final double n37 = n2 + length3 * n8 + this.yCoord;
                        int n38 = (int)n37;
                        if (n37 < n38) {
                            --n38;
                        }
                        final int n39 = n38 & 30 + 73 - 58 + 210;
                        final double n40 = n37 - n38;
                        final double n41 = n40 * n40 * n40 * (n40 * (n40 * 6.0 - 15.0) + 10.0);
                        if (length3 == 0 || n39 != n26) {
                            n26 = n39;
                            final int n42 = this.permutations[n29] + n39;
                            final int n43 = this.permutations[n42] + n34;
                            final int n44 = this.permutations[n42 + " ".length()] + n34;
                            final int n45 = this.permutations[n29 + " ".length()] + n39;
                            final int n46 = this.permutations[n45] + n34;
                            final int n47 = this.permutations[n45 + " ".length()] + n34;
                            lerp2 = this.lerp(n31, this.grad(this.permutations[n43], n30, n40, n35), this.grad(this.permutations[n46], n30 - 1.0, n40, n35));
                            lerp3 = this.lerp(n31, this.grad(this.permutations[n44], n30, n40 - 1.0, n35), this.grad(this.permutations[n47], n30 - 1.0, n40 - 1.0, n35));
                            lerp4 = this.lerp(n31, this.grad(this.permutations[n43 + " ".length()], n30, n40, n35 - 1.0), this.grad(this.permutations[n46 + " ".length()], n30 - 1.0, n40, n35 - 1.0));
                            lerp5 = this.lerp(n31, this.grad(this.permutations[n44 + " ".length()], n30, n40 - 1.0, n35 - 1.0), this.grad(this.permutations[n47 + " ".length()], n30 - 1.0, n40 - 1.0, n35 - 1.0));
                        }
                        final double lerp6 = this.lerp(n36, this.lerp(n41, lerp2, lerp3), this.lerp(n41, lerp4, lerp5));
                        final int n48 = length2++;
                        array[n48] += lerp6 * n25;
                        ++length3;
                    }
                    ++l;
                }
                ++k;
            }
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
            if (3 != 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public NoiseGeneratorImproved() {
        this(new Random());
    }
    
    public final double lerp(final double n, final double n2, final double n3) {
        return n2 + n * (n3 - n2);
    }
    
    static {
        final double[] field_152381_e2 = new double[0x67 ^ 0x77];
        field_152381_e2["".length()] = 1.0;
        field_152381_e2[" ".length()] = -1.0;
        field_152381_e2["  ".length()] = 1.0;
        field_152381_e2["   ".length()] = -1.0;
        field_152381_e2[0x83 ^ 0x87] = 1.0;
        field_152381_e2[0x80 ^ 0x85] = -1.0;
        field_152381_e2[0x82 ^ 0x84] = 1.0;
        field_152381_e2[0x4 ^ 0x3] = -1.0;
        field_152381_e2[0x7C ^ 0x75] = (field_152381_e2[0x7A ^ 0x72] = 0.0);
        field_152381_e2[0x73 ^ 0x78] = (field_152381_e2[0x9E ^ 0x94] = 0.0);
        field_152381_e2[0xC8 ^ 0xC4] = 1.0;
        field_152381_e2[0xBE ^ 0xB3] = 0.0;
        field_152381_e2[0xB0 ^ 0xBE] = -1.0;
        field_152381_e2[0x5D ^ 0x52] = 0.0;
        field_152381_e = field_152381_e2;
        final double[] field_152382_f2 = new double[0x59 ^ 0x49];
        field_152382_f2["".length()] = 1.0;
        field_152382_f2[" ".length()] = 1.0;
        field_152382_f2["  ".length()] = -1.0;
        field_152382_f2["   ".length()] = -1.0;
        field_152382_f2[0xB1 ^ 0xB4] = (field_152382_f2[0x12 ^ 0x16] = 0.0);
        field_152382_f2[0x1D ^ 0x1A] = (field_152382_f2[0x2 ^ 0x4] = 0.0);
        field_152382_f2[0xA6 ^ 0xAE] = 1.0;
        field_152382_f2[0xBD ^ 0xB4] = -1.0;
        field_152382_f2[0xAA ^ 0xA0] = 1.0;
        field_152382_f2[0xC8 ^ 0xC3] = -1.0;
        field_152382_f2[0x20 ^ 0x2C] = 1.0;
        field_152382_f2[0x7A ^ 0x77] = -1.0;
        field_152382_f2[0xB5 ^ 0xBB] = 1.0;
        field_152382_f2[0x3A ^ 0x35] = -1.0;
        field_152382_f = field_152382_f2;
        final double[] field_152383_g2 = new double[0xC ^ 0x1C];
        field_152383_g2["".length()] = 0.0;
        field_152383_g2[" ".length()] = 0.0;
        field_152383_g2["  ".length()] = 0.0;
        field_152383_g2["   ".length()] = 0.0;
        field_152383_g2[0x5D ^ 0x58] = (field_152383_g2[0x97 ^ 0x93] = 1.0);
        field_152383_g2[0xB7 ^ 0xB0] = (field_152383_g2[0xB5 ^ 0xB3] = -1.0);
        field_152383_g2[0x4E ^ 0x47] = (field_152383_g2[0xAE ^ 0xA6] = 1.0);
        field_152383_g2[0x69 ^ 0x62] = (field_152383_g2[0x20 ^ 0x2A] = -1.0);
        field_152383_g2[0x47 ^ 0x4B] = 0.0;
        field_152383_g2[0x3F ^ 0x32] = 1.0;
        field_152383_g2[0x4C ^ 0x42] = 0.0;
        field_152383_g2[0x5A ^ 0x55] = -1.0;
        field_152383_g = field_152383_g2;
        final double[] field_152384_h2 = new double[0x41 ^ 0x51];
        field_152384_h2["".length()] = 1.0;
        field_152384_h2[" ".length()] = -1.0;
        field_152384_h2["  ".length()] = 1.0;
        field_152384_h2["   ".length()] = -1.0;
        field_152384_h2[0xB8 ^ 0xBC] = 1.0;
        field_152384_h2[0x2C ^ 0x29] = -1.0;
        field_152384_h2[0xA7 ^ 0xA1] = 1.0;
        field_152384_h2[0x1 ^ 0x6] = -1.0;
        field_152384_h2[0x16 ^ 0x1F] = (field_152384_h2[0xCF ^ 0xC7] = 0.0);
        field_152384_h2[0x6F ^ 0x64] = (field_152384_h2[0x5B ^ 0x51] = 0.0);
        field_152384_h2[0x54 ^ 0x58] = 1.0;
        field_152384_h2[0x31 ^ 0x3C] = 0.0;
        field_152384_h2[0x5C ^ 0x52] = -1.0;
        field_152384_h2[0x7D ^ 0x72] = 0.0;
        field_152384_h = field_152384_h2;
        final double[] field_152385_i2 = new double[0x78 ^ 0x68];
        field_152385_i2["".length()] = 0.0;
        field_152385_i2[" ".length()] = 0.0;
        field_152385_i2["  ".length()] = 0.0;
        field_152385_i2["   ".length()] = 0.0;
        field_152385_i2[0x6 ^ 0x3] = (field_152385_i2[0x69 ^ 0x6D] = 1.0);
        field_152385_i2[0x65 ^ 0x62] = (field_152385_i2[0x35 ^ 0x33] = -1.0);
        field_152385_i2[0xAD ^ 0xA4] = (field_152385_i2[0x15 ^ 0x1D] = 1.0);
        field_152385_i2[0x7 ^ 0xC] = (field_152385_i2[0x39 ^ 0x33] = -1.0);
        field_152385_i2[0x6C ^ 0x60] = 0.0;
        field_152385_i2[0x1 ^ 0xC] = 1.0;
        field_152385_i2[0x73 ^ 0x7D] = 0.0;
        field_152385_i2[0x2B ^ 0x24] = -1.0;
        field_152385_i = field_152385_i2;
    }
}
