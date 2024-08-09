/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

public class ImageMath {
    public static final float PI = (float)Math.PI;
    public static final float HALF_PI = 1.5707964f;
    public static final float QUARTER_PI = 0.7853982f;
    public static final float TWO_PI = (float)Math.PI * 2;
    private static final float m00 = -0.5f;
    private static final float m01 = 1.5f;
    private static final float m02 = -1.5f;
    private static final float m03 = 0.5f;
    private static final float m10 = 1.0f;
    private static final float m11 = -2.5f;
    private static final float m12 = 2.0f;
    private static final float m13 = -0.5f;
    private static final float m20 = -0.5f;
    private static final float m21 = 0.0f;
    private static final float m22 = 0.5f;
    private static final float m23 = 0.0f;
    private static final float m30 = 0.0f;
    private static final float m31 = 1.0f;
    private static final float m32 = 0.0f;
    private static final float m33 = 0.0f;

    public static float bias(float f, float f2) {
        return f / ((1.0f / f2 - 2.0f) * (1.0f - f) + 1.0f);
    }

    public static float gain(float f, float f2) {
        float f3 = (1.0f / f2 - 2.0f) * (1.0f - 2.0f * f);
        if ((double)f < 0.5) {
            return f / (f3 + 1.0f);
        }
        return (f3 - f) / (f3 - 1.0f);
    }

    public static float step(float f, float f2) {
        return f2 < f ? 0.0f : 1.0f;
    }

    public static float pulse(float f, float f2, float f3) {
        return f3 < f || f3 >= f2 ? 0.0f : 1.0f;
    }

    public static float smoothPulse(float f, float f2, float f3, float f4, float f5) {
        if (f5 < f || f5 >= f4) {
            return 0.0f;
        }
        if (f5 >= f2) {
            if (f5 < f3) {
                return 1.0f;
            }
            f5 = (f5 - f3) / (f4 - f3);
            return 1.0f - f5 * f5 * (3.0f - 2.0f * f5);
        }
        f5 = (f5 - f) / (f2 - f);
        return f5 * f5 * (3.0f - 2.0f * f5);
    }

    public static float smoothStep(float f, float f2, float f3) {
        if (f3 < f) {
            return 0.0f;
        }
        if (f3 >= f2) {
            return 1.0f;
        }
        f3 = (f3 - f) / (f2 - f);
        return f3 * f3 * (3.0f - 2.0f * f3);
    }

    public static float circleUp(float f) {
        f = 1.0f - f;
        return (float)Math.sqrt(1.0f - f * f);
    }

    public static float circleDown(float f) {
        return 1.0f - (float)Math.sqrt(1.0f - f * f);
    }

    public static float clamp(float f, float f2, float f3) {
        return f < f2 ? f2 : (f > f3 ? f3 : f);
    }

    public static int clamp(int n, int n2, int n3) {
        return n < n2 ? n2 : (n > n3 ? n3 : n);
    }

    public static double mod(double d, double d2) {
        int n;
        if ((d -= (double)(n = (int)(d / d2)) * d2) < 0.0) {
            return d + d2;
        }
        return d;
    }

    public static float mod(float f, float f2) {
        int n;
        if ((f -= (float)(n = (int)(f / f2)) * f2) < 0.0f) {
            return f + f2;
        }
        return f;
    }

    public static int mod(int n, int n2) {
        int n3;
        if ((n -= (n3 = n / n2) * n2) < 0) {
            return n + n2;
        }
        return n;
    }

    public static float triangle(float f) {
        float f2 = ImageMath.mod(f, 1.0f);
        return 2.0f * ((double)f2 < 0.5 ? f2 : 1.0f - f2);
    }

    public static float lerp(float f, float f2, float f3) {
        return f2 + f * (f3 - f2);
    }

    public static int lerp(float f, int n, int n2) {
        return (int)((float)n + f * (float)(n2 - n));
    }

    public static int mixColors(float f, int n, int n2) {
        int n3 = n >> 24 & 0xFF;
        int n4 = n >> 16 & 0xFF;
        int n5 = n >> 8 & 0xFF;
        int n6 = n & 0xFF;
        int n7 = n2 >> 24 & 0xFF;
        int n8 = n2 >> 16 & 0xFF;
        int n9 = n2 >> 8 & 0xFF;
        int n10 = n2 & 0xFF;
        n3 = ImageMath.lerp(f, n3, n7);
        n4 = ImageMath.lerp(f, n4, n8);
        n5 = ImageMath.lerp(f, n5, n9);
        n6 = ImageMath.lerp(f, n6, n10);
        return n3 << 24 | n4 << 16 | n5 << 8 | n6;
    }

    public static int bilinearInterpolate(float f, float f2, int n, int n2, int n3, int n4) {
        int n5 = n >> 24 & 0xFF;
        int n6 = n >> 16 & 0xFF;
        int n7 = n >> 8 & 0xFF;
        int n8 = n & 0xFF;
        int n9 = n2 >> 24 & 0xFF;
        int n10 = n2 >> 16 & 0xFF;
        int n11 = n2 >> 8 & 0xFF;
        int n12 = n2 & 0xFF;
        int n13 = n3 >> 24 & 0xFF;
        int n14 = n3 >> 16 & 0xFF;
        int n15 = n3 >> 8 & 0xFF;
        int n16 = n3 & 0xFF;
        int n17 = n4 >> 24 & 0xFF;
        int n18 = n4 >> 16 & 0xFF;
        int n19 = n4 >> 8 & 0xFF;
        int n20 = n4 & 0xFF;
        float f3 = 1.0f - f;
        float f4 = 1.0f - f2;
        float f5 = f3 * (float)n5 + f * (float)n9;
        float f6 = f3 * (float)n13 + f * (float)n17;
        int n21 = (int)(f4 * f5 + f2 * f6);
        f5 = f3 * (float)n6 + f * (float)n10;
        f6 = f3 * (float)n14 + f * (float)n18;
        int n22 = (int)(f4 * f5 + f2 * f6);
        f5 = f3 * (float)n7 + f * (float)n11;
        f6 = f3 * (float)n15 + f * (float)n19;
        int n23 = (int)(f4 * f5 + f2 * f6);
        f5 = f3 * (float)n8 + f * (float)n12;
        f6 = f3 * (float)n16 + f * (float)n20;
        int n24 = (int)(f4 * f5 + f2 * f6);
        return n21 << 24 | n22 << 16 | n23 << 8 | n24;
    }

    public static int brightnessNTSC(int n) {
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        return (int)((float)n2 * 0.299f + (float)n3 * 0.587f + (float)n4 * 0.114f);
    }

    public static float spline(float f, int n, float[] fArray) {
        int n2 = n - 3;
        if (n2 < 1) {
            throw new IllegalArgumentException("Too few knots in spline");
        }
        int n3 = (int)(f = ImageMath.clamp(f, 0.0f, 1.0f) * (float)n2);
        if (n3 > n - 4) {
            n3 = n - 4;
        }
        f -= (float)n3;
        float f2 = fArray[n3];
        float f3 = fArray[n3 + 1];
        float f4 = fArray[n3 + 2];
        float f5 = fArray[n3 + 3];
        float f6 = -0.5f * f2 + 1.5f * f3 + -1.5f * f4 + 0.5f * f5;
        float f7 = 1.0f * f2 + -2.5f * f3 + 2.0f * f4 + -0.5f * f5;
        float f8 = -0.5f * f2 + 0.0f * f3 + 0.5f * f4 + 0.0f * f5;
        float f9 = 0.0f * f2 + 1.0f * f3 + 0.0f * f4 + 0.0f * f5;
        return ((f6 * f + f7) * f + f8) * f + f9;
    }

    public static float spline(float f, int n, int[] nArray, int[] nArray2) {
        int n2;
        int n3 = n - 3;
        if (n3 < 1) {
            throw new IllegalArgumentException("Too few knots in spline");
        }
        for (n2 = 0; n2 < n3 && !((float)nArray[n2 + 1] > f); ++n2) {
        }
        if (n2 > n - 3) {
            n2 = n - 3;
        }
        float f2 = (f - (float)nArray[n2]) / (float)(nArray[n2 + 1] - nArray[n2]);
        if (--n2 < 0) {
            n2 = 0;
            f2 = 0.0f;
        }
        float f3 = nArray2[n2];
        float f4 = nArray2[n2 + 1];
        float f5 = nArray2[n2 + 2];
        float f6 = nArray2[n2 + 3];
        float f7 = -0.5f * f3 + 1.5f * f4 + -1.5f * f5 + 0.5f * f6;
        float f8 = 1.0f * f3 + -2.5f * f4 + 2.0f * f5 + -0.5f * f6;
        float f9 = -0.5f * f3 + 0.0f * f4 + 0.5f * f5 + 0.0f * f6;
        float f10 = 0.0f * f3 + 1.0f * f4 + 0.0f * f5 + 0.0f * f6;
        return ((f7 * f2 + f8) * f2 + f9) * f2 + f10;
    }

    public static int colorSpline(float f, int n, int[] nArray) {
        int n2 = n - 3;
        if (n2 < 1) {
            throw new IllegalArgumentException("Too few knots in spline");
        }
        int n3 = (int)(f = ImageMath.clamp(f, 0.0f, 1.0f) * (float)n2);
        if (n3 > n - 4) {
            n3 = n - 4;
        }
        f -= (float)n3;
        int n4 = 0;
        for (int i = 0; i < 4; ++i) {
            int n5 = i * 8;
            float f2 = nArray[n3] >> n5 & 0xFF;
            float f3 = nArray[n3 + 1] >> n5 & 0xFF;
            float f4 = nArray[n3 + 2] >> n5 & 0xFF;
            float f5 = nArray[n3 + 3] >> n5 & 0xFF;
            float f6 = -0.5f * f2 + 1.5f * f3 + -1.5f * f4 + 0.5f * f5;
            float f7 = 1.0f * f2 + -2.5f * f3 + 2.0f * f4 + -0.5f * f5;
            float f8 = -0.5f * f2 + 0.0f * f3 + 0.5f * f4 + 0.0f * f5;
            float f9 = 0.0f * f2 + 1.0f * f3 + 0.0f * f4 + 0.0f * f5;
            int n6 = (int)(((f6 * f + f7) * f + f8) * f + f9);
            if (n6 < 0) {
                n6 = 0;
            } else if (n6 > 255) {
                n6 = 255;
            }
            n4 |= n6 << n5;
        }
        return n4;
    }

    public static int colorSpline(int n, int n2, int[] nArray, int[] nArray2) {
        int n3;
        int n4 = n2 - 3;
        if (n4 < 1) {
            throw new IllegalArgumentException("Too few knots in spline");
        }
        for (n3 = 0; n3 < n4 && nArray[n3 + 1] <= n; ++n3) {
        }
        if (n3 > n2 - 3) {
            n3 = n2 - 3;
        }
        float f = (float)(n - nArray[n3]) / (float)(nArray[n3 + 1] - nArray[n3]);
        if (--n3 < 0) {
            n3 = 0;
            f = 0.0f;
        }
        int n5 = 0;
        for (int i = 0; i < 4; ++i) {
            int n6 = i * 8;
            float f2 = nArray2[n3] >> n6 & 0xFF;
            float f3 = nArray2[n3 + 1] >> n6 & 0xFF;
            float f4 = nArray2[n3 + 2] >> n6 & 0xFF;
            float f5 = nArray2[n3 + 3] >> n6 & 0xFF;
            float f6 = -0.5f * f2 + 1.5f * f3 + -1.5f * f4 + 0.5f * f5;
            float f7 = 1.0f * f2 + -2.5f * f3 + 2.0f * f4 + -0.5f * f5;
            float f8 = -0.5f * f2 + 0.0f * f3 + 0.5f * f4 + 0.0f * f5;
            float f9 = 0.0f * f2 + 1.0f * f3 + 0.0f * f4 + 0.0f * f5;
            int n7 = (int)(((f6 * f + f7) * f + f8) * f + f9);
            if (n7 < 0) {
                n7 = 0;
            } else if (n7 > 255) {
                n7 = 255;
            }
            n5 |= n7 << n6;
        }
        return n5;
    }

    public static void resample(int[] nArray, int[] nArray2, int n, int n2, int n3, float[] fArray) {
        float f;
        int n4 = n2;
        int n5 = n2;
        int n6 = nArray.length;
        float[] fArray2 = new float[n + 2];
        int n7 = 0;
        for (int i = 0; i < n; ++i) {
            while (fArray[n7 + 1] < (float)i) {
                ++n7;
            }
            fArray2[i] = (float)n7 + ((float)i - fArray[n7]) / (fArray[n7 + 1] - fArray[n7]);
        }
        fArray2[n] = n;
        fArray2[n + 1] = n;
        float f2 = 1.0f;
        float f3 = f = fArray2[1];
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        float f7 = 0.0f;
        int n8 = nArray[n4];
        int n9 = n8 >> 24 & 0xFF;
        int n10 = n8 >> 16 & 0xFF;
        int n11 = n8 >> 8 & 0xFF;
        int n12 = n8 & 0xFF;
        n8 = nArray[n4 += n3];
        int n13 = n8 >> 24 & 0xFF;
        int n14 = n8 >> 16 & 0xFF;
        int n15 = n8 >> 8 & 0xFF;
        int n16 = n8 & 0xFF;
        n4 += n3;
        n7 = 1;
        while (n7 <= n) {
            float f8 = f2 * (float)n9 + (1.0f - f2) * (float)n13;
            float f9 = f2 * (float)n10 + (1.0f - f2) * (float)n14;
            float f10 = f2 * (float)n11 + (1.0f - f2) * (float)n15;
            float f11 = f2 * (float)n12 + (1.0f - f2) * (float)n16;
            if (f2 < f) {
                f7 += f8 * f2;
                f6 += f9 * f2;
                f5 += f10 * f2;
                f4 += f11 * f2;
                f -= f2;
                f2 = 1.0f;
                n9 = n13;
                n10 = n14;
                n11 = n15;
                n12 = n16;
                if (n4 < n6) {
                    n8 = nArray[n4];
                }
                n13 = n8 >> 24 & 0xFF;
                n14 = n8 >> 16 & 0xFF;
                n15 = n8 >> 8 & 0xFF;
                n16 = n8 & 0xFF;
                n4 += n3;
                continue;
            }
            nArray2[n5] = (int)Math.min((f7 += f8 * f) / f3, 255.0f) << 24 | (int)Math.min((f6 += f9 * f) / f3, 255.0f) << 16 | (int)Math.min((f5 += f10 * f) / f3, 255.0f) << 8 | (int)Math.min((f4 += f11 * f) / f3, 255.0f);
            n5 += n3;
            f4 = 0.0f;
            f5 = 0.0f;
            f6 = 0.0f;
            f7 = 0.0f;
            f2 -= f;
            f3 = f = fArray2[n7 + 1] - fArray2[n7];
            ++n7;
        }
    }

    public static void premultiply(int[] nArray, int n, int n2) {
        n2 += n;
        for (int i = n; i < n2; ++i) {
            int n3 = nArray[i];
            int n4 = n3 >> 24 & 0xFF;
            int n5 = n3 >> 16 & 0xFF;
            int n6 = n3 >> 8 & 0xFF;
            int n7 = n3 & 0xFF;
            float f = (float)n4 * 0.003921569f;
            n5 = (int)((float)n5 * f);
            n6 = (int)((float)n6 * f);
            n7 = (int)((float)n7 * f);
            nArray[i] = n4 << 24 | n5 << 16 | n6 << 8 | n7;
        }
    }

    public static void unpremultiply(int[] nArray, int n, int n2) {
        n2 += n;
        for (int i = n; i < n2; ++i) {
            int n3 = nArray[i];
            int n4 = n3 >> 24 & 0xFF;
            int n5 = n3 >> 16 & 0xFF;
            int n6 = n3 >> 8 & 0xFF;
            int n7 = n3 & 0xFF;
            if (n4 == 0 || n4 == 255) continue;
            float f = 255.0f / (float)n4;
            n5 = (int)((float)n5 * f);
            n6 = (int)((float)n6 * f);
            n7 = (int)((float)n7 * f);
            if (n5 > 255) {
                n5 = 255;
            }
            if (n6 > 255) {
                n6 = 255;
            }
            if (n7 > 255) {
                n7 = 255;
            }
            nArray[i] = n4 << 24 | n5 << 16 | n6 << 8 | n7;
        }
    }
}

