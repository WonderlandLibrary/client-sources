/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.Function1D;
import com.jhlabs.math.Function2D;
import com.jhlabs.math.Function3D;
import java.util.Random;

public class SCNoise
implements Function1D,
Function2D,
Function3D {
    private static Random randomGenerator = new Random();
    public short[] perm = new short[]{225, 155, 210, 108, 175, 199, 221, 144, 203, 116, 70, 213, 69, 158, 33, 252, 5, 82, 173, 133, 222, 139, 174, 27, 9, 71, 90, 246, 75, 130, 91, 191, 169, 138, 2, 151, 194, 235, 81, 7, 25, 113, 228, 159, 205, 253, 134, 142, 248, 65, 224, 217, 22, 121, 229, 63, 89, 103, 96, 104, 156, 17, 201, 129, 36, 8, 165, 110, 237, 117, 231, 56, 132, 211, 152, 20, 181, 111, 239, 218, 170, 163, 51, 172, 157, 47, 80, 212, 176, 250, 87, 49, 99, 242, 136, 189, 162, 115, 44, 43, 124, 94, 150, 16, 141, 247, 32, 10, 198, 223, 255, 72, 53, 131, 84, 57, 220, 197, 58, 50, 208, 11, 241, 28, 3, 192, 62, 202, 18, 215, 153, 24, 76, 41, 15, 179, 39, 46, 55, 6, 128, 167, 23, 188, 106, 34, 187, 140, 164, 73, 112, 182, 244, 195, 227, 13, 35, 77, 196, 185, 26, 200, 226, 119, 31, 123, 168, 125, 249, 68, 183, 230, 177, 135, 160, 180, 12, 1, 243, 148, 102, 166, 38, 238, 251, 37, 240, 126, 64, 74, 161, 40, 184, 149, 171, 178, 101, 66, 29, 59, 146, 61, 254, 107, 42, 86, 154, 4, 236, 232, 120, 21, 233, 209, 45, 98, 193, 114, 78, 19, 206, 14, 118, 127, 48, 79, 147, 85, 30, 207, 219, 54, 88, 234, 190, 122, 95, 67, 143, 109, 137, 214, 145, 93, 92, 100, 245, 0, 216, 186, 60, 83, 105, 97, 204, 52};
    private static final int TABSIZE = 256;
    private static final int TABMASK = 255;
    private static final int NIMPULSES = 3;
    private static float[] impulseTab;
    private static final int SAMPRATE = 100;
    private static final int NENTRIES = 401;
    private static float[] table;

    @Override
    public float evaluate(float f) {
        return this.evaluate(f, 0.1f);
    }

    @Override
    public float evaluate(float f, float f2) {
        float f3 = 0.0f;
        if (impulseTab == null) {
            impulseTab = SCNoise.impulseTabInit(665);
        }
        int n = SCNoise.floor(f);
        float f4 = f - (float)n;
        int n2 = SCNoise.floor(f2);
        float f5 = f2 - (float)n2;
        int n3 = 2;
        for (int i = -n3; i <= n3; ++i) {
            for (int j = -n3; j <= n3; ++j) {
                int n4 = this.perm[n + i + this.perm[n2 + j & 0xFF] & 0xFF];
                for (int k = 3; k > 0; --k) {
                    int n5 = n4 * 4;
                    float f6 = f4 - ((float)i + impulseTab[n5++]);
                    float f7 = f5 - ((float)j + impulseTab[n5++]);
                    float f8 = f6 * f6 + f7 * f7;
                    f3 += this.catrom2(f8) * impulseTab[n5];
                    n4 = n4 + 1 & 0xFF;
                }
            }
        }
        return f3 / 3.0f;
    }

    @Override
    public float evaluate(float f, float f2, float f3) {
        float f4 = 0.0f;
        if (impulseTab == null) {
            impulseTab = SCNoise.impulseTabInit(665);
        }
        int n = SCNoise.floor(f);
        float f5 = f - (float)n;
        int n2 = SCNoise.floor(f2);
        float f6 = f2 - (float)n2;
        int n3 = SCNoise.floor(f3);
        float f7 = f3 - (float)n3;
        int n4 = 2;
        for (int i = -n4; i <= n4; ++i) {
            for (int j = -n4; j <= n4; ++j) {
                for (int k = -n4; k <= n4; ++k) {
                    int n5 = this.perm[n + i + this.perm[n2 + j + this.perm[n3 + k & 0xFF] & 0xFF] & 0xFF];
                    for (int i2 = 3; i2 > 0; --i2) {
                        int n6 = n5 * 4;
                        float f8 = f5 - ((float)i + impulseTab[n6++]);
                        float f9 = f6 - ((float)j + impulseTab[n6++]);
                        float f10 = f7 - ((float)k + impulseTab[n6++]);
                        float f11 = f8 * f8 + f9 * f9 + f10 * f10;
                        f4 += this.catrom2(f11) * impulseTab[n6];
                        n5 = n5 + 1 & 0xFF;
                    }
                }
            }
        }
        return f4 / 3.0f;
    }

    public static int floor(float f) {
        int n = (int)f;
        if (f < 0.0f && f != (float)n) {
            return n - 1;
        }
        return n;
    }

    public float catrom2(float f) {
        int n;
        if (f >= 4.0f) {
            return 0.0f;
        }
        if (table == null) {
            table = new float[401];
            for (n = 0; n < 401; ++n) {
                float f2 = (float)n / 100.0f;
                SCNoise.table[n] = (f2 = (float)Math.sqrt(f2)) < 1.0f ? 0.5f * (2.0f + f2 * f2 * (-5.0f + f2 * 3.0f)) : 0.5f * (4.0f + f2 * (-8.0f + f2 * (5.0f - f2)));
            }
        }
        if ((n = SCNoise.floor(f = f * 100.0f + 0.5f)) >= 401) {
            return 0.0f;
        }
        return table[n];
    }

    static float[] impulseTabInit(int n) {
        float[] fArray = new float[1024];
        randomGenerator = new Random(n);
        for (int i = 0; i < 256; ++i) {
            fArray[i++] = randomGenerator.nextFloat();
            fArray[i++] = randomGenerator.nextFloat();
            fArray[i++] = randomGenerator.nextFloat();
            fArray[i++] = 1.0f - 2.0f * randomGenerator.nextFloat();
        }
        return fArray;
    }
}

