package net.minecraft.world.gen;

import net.minecraft.util.*;
import java.util.*;

public class NoiseGeneratorOctaves extends NoiseGenerator
{
    private NoiseGeneratorImproved[] generatorCollection;
    private int octaves;
    
    public double[] generateNoiseOctaves(final double[] array, final int n, final int n2, final int n3, final int n4, final double n5, final double n6, final double n7) {
        return this.generateNoiseOctaves(array, n, 0x2A ^ 0x20, n2, n3, " ".length(), n4, n5, 1.0, n6);
    }
    
    public double[] generateNoiseOctaves(double[] array, final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final double n7, final double n8, final double n9) {
        if (array == null) {
            array = new double[n4 * n5 * n6];
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        else {
            int i = "".length();
            "".length();
            if (4 != 4) {
                throw null;
            }
            while (i < array.length) {
                array[i] = 0.0;
                ++i;
            }
        }
        double n10 = 1.0;
        int j = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (j < this.octaves) {
            final double n11 = n * n10 * n7;
            final double n12 = n2 * n10 * n8;
            final double n13 = n3 * n10 * n9;
            final long floor_double_long = MathHelper.floor_double_long(n11);
            final long floor_double_long2 = MathHelper.floor_double_long(n13);
            this.generatorCollection[j].populateNoiseArray(array, n11 - floor_double_long + floor_double_long % 16777216L, n12, n13 - floor_double_long2 + floor_double_long2 % 16777216L, n4, n5, n6, n7 * n10, n8 * n10, n9 * n10, n10);
            n10 /= 2.0;
            ++j;
        }
        return array;
    }
    
    public NoiseGeneratorOctaves(final Random random, final int octaves) {
        this.octaves = octaves;
        this.generatorCollection = new NoiseGeneratorImproved[octaves];
        int i = "".length();
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (i < octaves) {
            this.generatorCollection[i] = new NoiseGeneratorImproved(random);
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
