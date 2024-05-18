package net.minecraft.world.gen;

import java.util.*;

public class NoiseGeneratorPerlin extends NoiseGenerator
{
    private int field_151602_b;
    private NoiseGeneratorSimplex[] field_151603_a;
    
    public double[] func_151600_a(double[] array, final double n, final double n2, final int n3, final int n4, final double n5, final double n6, final double n7, final double n8) {
        if (array != null && array.length >= n3 * n4) {
            int i = "".length();
            "".length();
            if (2 < 0) {
                throw null;
            }
            while (i < array.length) {
                array[i] = 0.0;
                ++i;
            }
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            array = new double[n3 * n4];
        }
        double n9 = 1.0;
        double n10 = 1.0;
        int j = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (j < this.field_151602_b) {
            this.field_151603_a[j].func_151606_a(array, n, n2, n3, n4, n5 * n10 * n9, n6 * n10 * n9, 0.55 / n9);
            n10 *= n7;
            n9 *= n8;
            ++j;
        }
        return array;
    }
    
    public double func_151601_a(final double n, final double n2) {
        double n3 = 0.0;
        double n4 = 1.0;
        int i = "".length();
        "".length();
        if (-1 >= 0) {
            throw null;
        }
        while (i < this.field_151602_b) {
            n3 += this.field_151603_a[i].func_151605_a(n * n4, n2 * n4) / n4;
            n4 /= 2.0;
            ++i;
        }
        return n3;
    }
    
    public NoiseGeneratorPerlin(final Random random, final int field_151602_b) {
        this.field_151602_b = field_151602_b;
        this.field_151603_a = new NoiseGeneratorSimplex[field_151602_b];
        int i = "".length();
        "".length();
        if (4 <= 2) {
            throw null;
        }
        while (i < field_151602_b) {
            this.field_151603_a[i] = new NoiseGeneratorSimplex(random);
            ++i;
        }
    }
    
    public double[] func_151599_a(final double[] array, final double n, final double n2, final int n3, final int n4, final double n5, final double n6, final double n7) {
        return this.func_151600_a(array, n, n2, n3, n4, n5, n6, n7, 0.5);
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
}
