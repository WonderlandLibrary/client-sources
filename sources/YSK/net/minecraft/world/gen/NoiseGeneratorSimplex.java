package net.minecraft.world.gen;

import java.util.*;

public class NoiseGeneratorSimplex
{
    private static final double field_151609_g;
    private int[] field_151608_f;
    private static final double field_151615_h;
    public double field_151610_d;
    public double field_151612_b;
    private static int[][] field_151611_e;
    public double field_151613_c;
    public static final double field_151614_a;
    
    private static int func_151607_a(final double n) {
        int n2;
        if (n > 0.0) {
            n2 = (int)n;
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            n2 = (int)n - " ".length();
        }
        return n2;
    }
    
    public double func_151605_a(final double n, final double n2) {
        final double n3 = (n + n2) * (0.5 * (NoiseGeneratorSimplex.field_151614_a - 1.0));
        final int func_151607_a = func_151607_a(n + n3);
        final int func_151607_a2 = func_151607_a(n2 + n3);
        final double n4 = (3.0 - NoiseGeneratorSimplex.field_151614_a) / 6.0;
        final double n5 = (func_151607_a + func_151607_a2) * n4;
        final double n6 = func_151607_a - n5;
        final double n7 = func_151607_a2 - n5;
        final double n8 = n - n6;
        final double n9 = n2 - n7;
        int n10;
        int n11;
        if (n8 > n9) {
            n10 = " ".length();
            n11 = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            n10 = "".length();
            n11 = " ".length();
        }
        final double n12 = n8 - n10 + n4;
        final double n13 = n9 - n11 + n4;
        final double n14 = n8 - 1.0 + 2.0 * n4;
        final double n15 = n9 - 1.0 + 2.0 * n4;
        final int n16 = func_151607_a & 110 + 88 - 97 + 154;
        final int n17 = func_151607_a2 & 187 + 158 - 160 + 70;
        final int n18 = this.field_151608_f[n16 + this.field_151608_f[n17]] % (0x67 ^ 0x6B);
        final int n19 = this.field_151608_f[n16 + n10 + this.field_151608_f[n17 + n11]] % (0x37 ^ 0x3B);
        final int n20 = this.field_151608_f[n16 + " ".length() + this.field_151608_f[n17 + " ".length()]] % (0x2E ^ 0x22);
        final double n21 = 0.5 - n8 * n8 - n9 * n9;
        double n22;
        if (n21 < 0.0) {
            n22 = 0.0;
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else {
            final double n23 = n21 * n21;
            n22 = n23 * n23 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n18], n8, n9);
        }
        final double n24 = 0.5 - n12 * n12 - n13 * n13;
        double n25;
        if (n24 < 0.0) {
            n25 = 0.0;
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else {
            final double n26 = n24 * n24;
            n25 = n26 * n26 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n19], n12, n13);
        }
        final double n27 = 0.5 - n14 * n14 - n15 * n15;
        double n28;
        if (n27 < 0.0) {
            n28 = 0.0;
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            final double n29 = n27 * n27;
            n28 = n29 * n29 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n20], n14, n15);
        }
        return 70.0 * (n22 + n25 + n28);
    }
    
    public NoiseGeneratorSimplex(final Random random) {
        this.field_151608_f = new int[349 + 353 - 609 + 419];
        this.field_151612_b = random.nextDouble() * 256.0;
        this.field_151613_c = random.nextDouble() * 256.0;
        this.field_151610_d = random.nextDouble() * 256.0;
        int i = "".length();
        "".length();
        if (2 == 4) {
            throw null;
        }
        while (i < 138 + 191 - 199 + 126) {
            this.field_151608_f[i] = i++;
        }
        int j = "".length();
        "".length();
        if (2 != 2) {
            throw null;
        }
        while (j < 140 + 159 - 170 + 127) {
            final int n = random.nextInt(181 + 217 - 313 + 171 - j) + j;
            final int n2 = this.field_151608_f[j];
            this.field_151608_f[j] = this.field_151608_f[n];
            this.field_151608_f[n] = n2;
            this.field_151608_f[j + (221 + 112 - 328 + 251)] = this.field_151608_f[j];
            ++j;
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
            if (2 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public NoiseGeneratorSimplex() {
        this(new Random());
    }
    
    static {
        final int[][] field_151611_e = new int[0x72 ^ 0x7E][];
        final int length = "".length();
        final int[] array = new int["   ".length()];
        array["".length()] = " ".length();
        array[" ".length()] = " ".length();
        field_151611_e[length] = array;
        final int length2 = " ".length();
        final int[] array2 = new int["   ".length()];
        array2["".length()] = -" ".length();
        array2[" ".length()] = " ".length();
        field_151611_e[length2] = array2;
        final int length3 = "  ".length();
        final int[] array3 = new int["   ".length()];
        array3["".length()] = " ".length();
        array3[" ".length()] = -" ".length();
        field_151611_e[length3] = array3;
        final int length4 = "   ".length();
        final int[] array4 = new int["   ".length()];
        array4["".length()] = -" ".length();
        array4[" ".length()] = -" ".length();
        field_151611_e[length4] = array4;
        final int n = 0xA0 ^ 0xA4;
        final int[] array5 = new int["   ".length()];
        array5["".length()] = " ".length();
        array5["  ".length()] = " ".length();
        field_151611_e[n] = array5;
        final int n2 = 0xC2 ^ 0xC7;
        final int[] array6 = new int["   ".length()];
        array6["".length()] = -" ".length();
        array6["  ".length()] = " ".length();
        field_151611_e[n2] = array6;
        final int n3 = 0x3 ^ 0x5;
        final int[] array7 = new int["   ".length()];
        array7["".length()] = " ".length();
        array7["  ".length()] = -" ".length();
        field_151611_e[n3] = array7;
        final int n4 = 0x59 ^ 0x5E;
        final int[] array8 = new int["   ".length()];
        array8["".length()] = -" ".length();
        array8["  ".length()] = -" ".length();
        field_151611_e[n4] = array8;
        final int n5 = 0xA6 ^ 0xAE;
        final int[] array9 = new int["   ".length()];
        array9[" ".length()] = " ".length();
        array9["  ".length()] = " ".length();
        field_151611_e[n5] = array9;
        final int n6 = 0x6E ^ 0x67;
        final int[] array10 = new int["   ".length()];
        array10[" ".length()] = -" ".length();
        array10["  ".length()] = " ".length();
        field_151611_e[n6] = array10;
        final int n7 = 0x72 ^ 0x78;
        final int[] array11 = new int["   ".length()];
        array11[" ".length()] = " ".length();
        array11["  ".length()] = -" ".length();
        field_151611_e[n7] = array11;
        final int n8 = 0x2C ^ 0x27;
        final int[] array12 = new int["   ".length()];
        array12[" ".length()] = -" ".length();
        array12["  ".length()] = -" ".length();
        field_151611_e[n8] = array12;
        NoiseGeneratorSimplex.field_151611_e = field_151611_e;
        field_151614_a = Math.sqrt(3.0);
        field_151609_g = 0.5 * (NoiseGeneratorSimplex.field_151614_a - 1.0);
        field_151615_h = (3.0 - NoiseGeneratorSimplex.field_151614_a) / 6.0;
    }
    
    public void func_151606_a(final double[] array, final double n, final double n2, final int n3, final int n4, final double n5, final double n6, final double n7) {
        int length = "".length();
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < n4) {
            final double n8 = (n2 + i) * n6 + this.field_151613_c;
            int j = "".length();
            "".length();
            if (-1 == 0) {
                throw null;
            }
            while (j < n3) {
                final double n9 = (n + j) * n5 + this.field_151612_b;
                final double n10 = (n9 + n8) * NoiseGeneratorSimplex.field_151609_g;
                final int func_151607_a = func_151607_a(n9 + n10);
                final int func_151607_a2 = func_151607_a(n8 + n10);
                final double n11 = (func_151607_a + func_151607_a2) * NoiseGeneratorSimplex.field_151615_h;
                final double n12 = func_151607_a - n11;
                final double n13 = func_151607_a2 - n11;
                final double n14 = n9 - n12;
                final double n15 = n8 - n13;
                int n16;
                int n17;
                if (n14 > n15) {
                    n16 = " ".length();
                    n17 = "".length();
                    "".length();
                    if (4 < 2) {
                        throw null;
                    }
                }
                else {
                    n16 = "".length();
                    n17 = " ".length();
                }
                final double n18 = n14 - n16 + NoiseGeneratorSimplex.field_151615_h;
                final double n19 = n15 - n17 + NoiseGeneratorSimplex.field_151615_h;
                final double n20 = n14 - 1.0 + 2.0 * NoiseGeneratorSimplex.field_151615_h;
                final double n21 = n15 - 1.0 + 2.0 * NoiseGeneratorSimplex.field_151615_h;
                final int n22 = func_151607_a & 153 + 249 - 318 + 171;
                final int n23 = func_151607_a2 & 211 + 211 - 310 + 143;
                final int n24 = this.field_151608_f[n22 + this.field_151608_f[n23]] % (0xD ^ 0x1);
                final int n25 = this.field_151608_f[n22 + n16 + this.field_151608_f[n23 + n17]] % (0x4A ^ 0x46);
                final int n26 = this.field_151608_f[n22 + " ".length() + this.field_151608_f[n23 + " ".length()]] % (0x7E ^ 0x72);
                final double n27 = 0.5 - n14 * n14 - n15 * n15;
                double n28;
                if (n27 < 0.0) {
                    n28 = 0.0;
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else {
                    final double n29 = n27 * n27;
                    n28 = n29 * n29 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n24], n14, n15);
                }
                final double n30 = 0.5 - n18 * n18 - n19 * n19;
                double n31;
                if (n30 < 0.0) {
                    n31 = 0.0;
                    "".length();
                    if (2 >= 3) {
                        throw null;
                    }
                }
                else {
                    final double n32 = n30 * n30;
                    n31 = n32 * n32 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n25], n18, n19);
                }
                final double n33 = 0.5 - n20 * n20 - n21 * n21;
                double n34;
                if (n33 < 0.0) {
                    n34 = 0.0;
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    final double n35 = n33 * n33;
                    n34 = n35 * n35 * func_151604_a(NoiseGeneratorSimplex.field_151611_e[n26], n20, n21);
                }
                final int n36 = length++;
                array[n36] += 70.0 * (n28 + n31 + n34) * n7;
                ++j;
            }
            ++i;
        }
    }
    
    private static double func_151604_a(final int[] array, final double n, final double n2) {
        return array["".length()] * n + array[" ".length()] * n2;
    }
}
