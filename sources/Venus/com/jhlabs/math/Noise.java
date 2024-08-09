/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.Function1D;
import com.jhlabs.math.Function2D;
import com.jhlabs.math.Function3D;
import java.util.Random;

public class Noise
implements Function1D,
Function2D,
Function3D {
    private static Random randomGenerator = new Random();
    private static final int B = 256;
    private static final int BM = 255;
    private static final int N = 4096;
    static int[] p = new int[514];
    static float[][] g3 = new float[514][3];
    static float[][] g2 = new float[514][2];
    static float[] g1 = new float[514];
    static boolean start = true;

    @Override
    public float evaluate(float f) {
        return Noise.noise1(f);
    }

    @Override
    public float evaluate(float f, float f2) {
        return Noise.noise2(f, f2);
    }

    @Override
    public float evaluate(float f, float f2, float f3) {
        return Noise.noise3(f, f2, f3);
    }

    public static float turbulence2(float f, float f2, float f3) {
        float f4 = 0.0f;
        for (float f5 = 1.0f; f5 <= f3; f5 *= 2.0f) {
            f4 += Math.abs(Noise.noise2(f5 * f, f5 * f2)) / f5;
        }
        return f4;
    }

    public static float turbulence3(float f, float f2, float f3, float f4) {
        float f5 = 0.0f;
        for (float f6 = 1.0f; f6 <= f4; f6 *= 2.0f) {
            f5 += Math.abs(Noise.noise3(f6 * f, f6 * f2, f6 * f3)) / f6;
        }
        return f5;
    }

    private static float sCurve(float f) {
        return f * f * (3.0f - 2.0f * f);
    }

    public static float noise1(float f) {
        if (start) {
            start = false;
            Noise.init();
        }
        float f2 = f + 4096.0f;
        int n = (int)f2 & 0xFF;
        int n2 = n + 1 & 0xFF;
        float f3 = f2 - (float)((int)f2);
        float f4 = f3 - 1.0f;
        float f5 = Noise.sCurve(f3);
        float f6 = f3 * g1[p[n]];
        float f7 = f4 * g1[p[n2]];
        return 2.3f * Noise.lerp(f5, f6, f7);
    }

    public static float noise2(float f, float f2) {
        if (start) {
            start = false;
            Noise.init();
        }
        float f3 = f + 4096.0f;
        int n = (int)f3 & 0xFF;
        int n2 = n + 1 & 0xFF;
        float f4 = f3 - (float)((int)f3);
        float f5 = f4 - 1.0f;
        f3 = f2 + 4096.0f;
        int n3 = (int)f3 & 0xFF;
        int n4 = n3 + 1 & 0xFF;
        float f6 = f3 - (float)((int)f3);
        float f7 = f6 - 1.0f;
        int n5 = p[n];
        int n6 = p[n2];
        int n7 = p[n5 + n3];
        int n8 = p[n6 + n3];
        int n9 = p[n5 + n4];
        int n10 = p[n6 + n4];
        float f8 = Noise.sCurve(f4);
        float f9 = Noise.sCurve(f6);
        float[] fArray = g2[n7];
        float f10 = f4 * fArray[0] + f6 * fArray[1];
        fArray = g2[n8];
        float f11 = f5 * fArray[0] + f6 * fArray[1];
        float f12 = Noise.lerp(f8, f10, f11);
        fArray = g2[n9];
        f10 = f4 * fArray[0] + f7 * fArray[1];
        fArray = g2[n10];
        f11 = f5 * fArray[0] + f7 * fArray[1];
        float f13 = Noise.lerp(f8, f10, f11);
        return 1.5f * Noise.lerp(f9, f12, f13);
    }

    public static float noise3(float f, float f2, float f3) {
        if (start) {
            start = false;
            Noise.init();
        }
        float f4 = f + 4096.0f;
        int n = (int)f4 & 0xFF;
        int n2 = n + 1 & 0xFF;
        float f5 = f4 - (float)((int)f4);
        float f6 = f5 - 1.0f;
        f4 = f2 + 4096.0f;
        int n3 = (int)f4 & 0xFF;
        int n4 = n3 + 1 & 0xFF;
        float f7 = f4 - (float)((int)f4);
        float f8 = f7 - 1.0f;
        f4 = f3 + 4096.0f;
        int n5 = (int)f4 & 0xFF;
        int n6 = n5 + 1 & 0xFF;
        float f9 = f4 - (float)((int)f4);
        float f10 = f9 - 1.0f;
        int n7 = p[n];
        int n8 = p[n2];
        int n9 = p[n7 + n3];
        int n10 = p[n8 + n3];
        int n11 = p[n7 + n4];
        int n12 = p[n8 + n4];
        f4 = Noise.sCurve(f5);
        float f11 = Noise.sCurve(f7);
        float f12 = Noise.sCurve(f9);
        float[] fArray = g3[n9 + n5];
        float f13 = f5 * fArray[0] + f7 * fArray[1] + f9 * fArray[2];
        fArray = g3[n10 + n5];
        float f14 = f6 * fArray[0] + f7 * fArray[1] + f9 * fArray[2];
        float f15 = Noise.lerp(f4, f13, f14);
        fArray = g3[n11 + n5];
        f13 = f5 * fArray[0] + f8 * fArray[1] + f9 * fArray[2];
        fArray = g3[n12 + n5];
        f14 = f6 * fArray[0] + f8 * fArray[1] + f9 * fArray[2];
        float f16 = Noise.lerp(f4, f13, f14);
        float f17 = Noise.lerp(f11, f15, f16);
        fArray = g3[n9 + n6];
        f13 = f5 * fArray[0] + f7 * fArray[1] + f10 * fArray[2];
        fArray = g3[n10 + n6];
        f14 = f6 * fArray[0] + f7 * fArray[1] + f10 * fArray[2];
        f15 = Noise.lerp(f4, f13, f14);
        fArray = g3[n11 + n6];
        f13 = f5 * fArray[0] + f8 * fArray[1] + f10 * fArray[2];
        fArray = g3[n12 + n6];
        f14 = f6 * fArray[0] + f8 * fArray[1] + f10 * fArray[2];
        f16 = Noise.lerp(f4, f13, f14);
        float f18 = Noise.lerp(f11, f15, f16);
        return 1.5f * Noise.lerp(f12, f17, f18);
    }

    public static float lerp(float f, float f2, float f3) {
        return f2 + f * (f3 - f2);
    }

    private static void normalize2(float[] fArray) {
        float f = (float)Math.sqrt(fArray[0] * fArray[0] + fArray[1] * fArray[1]);
        fArray[0] = fArray[0] / f;
        fArray[1] = fArray[1] / f;
    }

    static void normalize3(float[] fArray) {
        float f = (float)Math.sqrt(fArray[0] * fArray[0] + fArray[1] * fArray[1] + fArray[2] * fArray[2]);
        fArray[0] = fArray[0] / f;
        fArray[1] = fArray[1] / f;
        fArray[2] = fArray[2] / f;
    }

    private static int random() {
        return randomGenerator.nextInt() & Integer.MAX_VALUE;
    }

    private static void init() {
        int n;
        int n2;
        for (n2 = 0; n2 < 256; ++n2) {
            Noise.p[n2] = n2;
            Noise.g1[n2] = (float)(Noise.random() % 512 - 256) / 256.0f;
            for (n = 0; n < 2; ++n) {
                Noise.g2[n2][n] = (float)(Noise.random() % 512 - 256) / 256.0f;
            }
            Noise.normalize2(g2[n2]);
            for (n = 0; n < 3; ++n) {
                Noise.g3[n2][n] = (float)(Noise.random() % 512 - 256) / 256.0f;
            }
            Noise.normalize3(g3[n2]);
        }
        for (n2 = 255; n2 >= 0; --n2) {
            int n3 = p[n2];
            n = Noise.random() % 256;
            Noise.p[n2] = p[n];
            Noise.p[n] = n3;
        }
        for (n2 = 0; n2 < 258; ++n2) {
            Noise.p[256 + n2] = p[n2];
            Noise.g1[256 + n2] = g1[n2];
            for (n = 0; n < 2; ++n) {
                Noise.g2[256 + n2][n] = g2[n2][n];
            }
            for (n = 0; n < 3; ++n) {
                Noise.g3[256 + n2][n] = g3[n2][n];
            }
        }
    }

    public static float[] findRange(Function1D function1D, float[] fArray) {
        if (fArray == null) {
            fArray = new float[2];
        }
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = -100.0f;
        while (f3 < 100.0f) {
            float f4 = function1D.evaluate(f3);
            f = Math.min(f, f4);
            f2 = Math.max(f2, f4);
            f3 = (float)((double)f3 + 1.27139);
        }
        fArray[0] = f;
        fArray[1] = f2;
        return fArray;
    }

    public static float[] findRange(Function2D function2D, float[] fArray) {
        if (fArray == null) {
            fArray = new float[2];
        }
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = -100.0f;
        while (f3 < 100.0f) {
            float f4 = -100.0f;
            while (f4 < 100.0f) {
                float f5 = function2D.evaluate(f4, f3);
                f = Math.min(f, f5);
                f2 = Math.max(f2, f5);
                f4 = (float)((double)f4 + 10.77139);
            }
            f3 = (float)((double)f3 + 10.35173);
        }
        fArray[0] = f;
        fArray[1] = f2;
        return fArray;
    }
}

