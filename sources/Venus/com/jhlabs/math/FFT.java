/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

public class FFT {
    protected float[] w1;
    protected float[] w2;
    protected float[] w3;

    public FFT(int n) {
        this.w1 = new float[n];
        this.w2 = new float[n];
        this.w3 = new float[n];
        int n2 = 1;
        for (int i = 0; i < n; ++i) {
            double d = Math.PI * -2 / (double)(n2 <<= 1);
            this.w1[i] = (float)Math.sin(0.5 * d);
            this.w2[i] = -2.0f * this.w1[i] * this.w1[i];
            this.w3[i] = (float)Math.sin(d);
        }
    }

    private void scramble(int n, float[] fArray, float[] fArray2) {
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3;
            if (i > n2) {
                float f = fArray[n2];
                fArray[n2] = fArray[i];
                fArray[i] = f;
                f = fArray2[n2];
                fArray2[n2] = fArray2[i];
                fArray2[i] = f;
            }
            for (n3 = n >> 1; n2 >= n3 && n3 >= 2; n2 -= n3, n3 >>= 1) {
            }
            n2 += n3;
        }
    }

    private void butterflies(int n, int n2, int n3, float[] fArray, float[] fArray2) {
        int n4 = 1;
        for (int i = 0; i < n2; ++i) {
            int n5 = n4;
            n4 <<= 1;
            float f = (float)n3 * this.w1[i];
            float f2 = this.w2[i];
            float f3 = (float)n3 * this.w3[i];
            float f4 = 1.0f;
            float f5 = 0.0f;
            for (int j = 0; j < n5; ++j) {
                for (int k = j; k < n; k += n4) {
                    int n6 = k + n5;
                    float f6 = fArray[n6];
                    float f7 = fArray2[n6];
                    float f8 = f4 * f6 - f5 * f7;
                    float f9 = f5 * f6 + f4 * f7;
                    fArray[n6] = fArray[k] - f8;
                    int n7 = k;
                    fArray[n7] = fArray[n7] + f8;
                    fArray2[n6] = fArray2[k] - f9;
                    int n8 = k;
                    fArray2[n8] = fArray2[n8] + f9;
                }
                f = f4;
                f4 = f * f2 - f5 * f3 + f4;
                f5 = f5 * f2 + f * f3 + f5;
            }
        }
        if (n3 == -1) {
            float f = 1.0f / (float)n;
            int n9 = 0;
            while (n9 < n) {
                int n10 = n9;
                fArray[n10] = fArray[n10] * f;
                int n11 = n9++;
                fArray2[n11] = fArray2[n11] * f;
            }
        }
    }

    public void transform1D(float[] fArray, float[] fArray2, int n, int n2, boolean bl) {
        this.scramble(n2, fArray, fArray2);
        this.butterflies(n2, n, bl ? 1 : -1, fArray, fArray2);
    }

    public void transform2D(float[] fArray, float[] fArray2, int n, int n2, boolean bl) {
        int n3;
        int n4;
        int n5 = this.log2(n);
        int n6 = this.log2(n2);
        int n7 = Math.max(n2, n);
        float[] fArray3 = new float[n7];
        float[] fArray4 = new float[n7];
        for (n4 = 0; n4 < n2; ++n4) {
            n3 = n4 * n;
            System.arraycopy(fArray, n3, fArray3, 0, n);
            System.arraycopy(fArray2, n3, fArray4, 0, n);
            this.transform1D(fArray3, fArray4, n5, n, bl);
            System.arraycopy(fArray3, 0, fArray, n3, n);
            System.arraycopy(fArray4, 0, fArray2, n3, n);
        }
        for (n4 = 0; n4 < n; ++n4) {
            int n8;
            n3 = n4;
            for (n8 = 0; n8 < n2; ++n8) {
                fArray3[n8] = fArray[n3];
                fArray4[n8] = fArray2[n3];
                n3 += n;
            }
            this.transform1D(fArray3, fArray4, n6, n2, bl);
            n3 = n4;
            for (n8 = 0; n8 < n2; ++n8) {
                fArray[n3] = fArray3[n8];
                fArray2[n3] = fArray4[n8];
                n3 += n;
            }
        }
    }

    private int log2(int n) {
        int n2 = 1;
        int n3 = 0;
        while (n2 < n) {
            n2 *= 2;
            ++n3;
        }
        return n2 == n ? n3 : -1;
    }
}

