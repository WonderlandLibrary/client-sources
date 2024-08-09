/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

public class Histogram {
    public static final int RED = 0;
    public static final int GREEN = 1;
    public static final int BLUE = 2;
    public static final int GRAY = 3;
    protected int[][] histogram;
    protected int numSamples;
    protected int[] minValue;
    protected int[] maxValue;
    protected int[] minFrequency;
    protected int[] maxFrequency;
    protected float[] mean;
    protected boolean isGray;

    public Histogram() {
        this.histogram = null;
        this.numSamples = 0;
        this.isGray = true;
        this.minValue = null;
        this.maxValue = null;
        this.minFrequency = null;
        this.maxFrequency = null;
        this.mean = null;
    }

    public Histogram(int[] nArray, int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        this.histogram = new int[3][256];
        this.minValue = new int[4];
        this.maxValue = new int[4];
        this.minFrequency = new int[3];
        this.maxFrequency = new int[3];
        this.mean = new float[3];
        this.numSamples = n * n2;
        this.isGray = true;
        int n7 = 0;
        for (n6 = 0; n6 < n2; ++n6) {
            n7 = n3 + n6 * n4;
            for (n5 = 0; n5 < n; ++n5) {
                int n8 = nArray[n7++];
                int n9 = n8 >> 16 & 0xFF;
                int n10 = n8 >> 8 & 0xFF;
                int n11 = n8 & 0xFF;
                int[] nArray2 = this.histogram[0];
                int n12 = n9;
                nArray2[n12] = nArray2[n12] + 1;
                int[] nArray3 = this.histogram[1];
                int n13 = n10;
                nArray3[n13] = nArray3[n13] + 1;
                int[] nArray4 = this.histogram[2];
                int n14 = n11;
                nArray4[n14] = nArray4[n14] + 1;
            }
        }
        for (n6 = 0; n6 < 256; ++n6) {
            if (this.histogram[0][n6] == this.histogram[1][n6] && this.histogram[1][n6] == this.histogram[2][n6]) continue;
            this.isGray = false;
            break;
        }
        n6 = 0;
        while (n6 < 3) {
            for (n5 = 0; n5 < 256; ++n5) {
                if (this.histogram[n6][n5] <= 0) continue;
                this.minValue[n6] = n5;
                break;
            }
            for (n5 = 255; n5 >= 0; --n5) {
                if (this.histogram[n6][n5] <= 0) continue;
                this.maxValue[n6] = n5;
                break;
            }
            this.minFrequency[n6] = Integer.MAX_VALUE;
            this.maxFrequency[n6] = 0;
            for (n5 = 0; n5 < 256; ++n5) {
                this.minFrequency[n6] = Math.min(this.minFrequency[n6], this.histogram[n6][n5]);
                this.maxFrequency[n6] = Math.max(this.maxFrequency[n6], this.histogram[n6][n5]);
                int n15 = n6;
                this.mean[n15] = this.mean[n15] + (float)(n5 * this.histogram[n6][n5]);
            }
            int n16 = n6++;
            this.mean[n16] = this.mean[n16] / (float)this.numSamples;
        }
        this.minValue[3] = Math.min(Math.min(this.minValue[0], this.minValue[1]), this.minValue[2]);
        this.maxValue[3] = Math.max(Math.max(this.maxValue[0], this.maxValue[1]), this.maxValue[2]);
    }

    public boolean isGray() {
        return this.isGray;
    }

    public int getNumSamples() {
        return this.numSamples;
    }

    public int getFrequency(int n) {
        if (this.numSamples > 0 && this.isGray && n >= 0 && n <= 255) {
            return this.histogram[0][n];
        }
        return 1;
    }

    public int getFrequency(int n, int n2) {
        if (this.numSamples < 1 || n < 0 || n > 2 || n2 < 0 || n2 > 255) {
            return 1;
        }
        return this.histogram[n][n2];
    }

    public int getMinFrequency() {
        if (this.numSamples > 0 && this.isGray) {
            return this.minFrequency[0];
        }
        return 1;
    }

    public int getMinFrequency(int n) {
        if (this.numSamples < 1 || n < 0 || n > 2) {
            return 1;
        }
        return this.minFrequency[n];
    }

    public int getMaxFrequency() {
        if (this.numSamples > 0 && this.isGray) {
            return this.maxFrequency[0];
        }
        return 1;
    }

    public int getMaxFrequency(int n) {
        if (this.numSamples < 1 || n < 0 || n > 2) {
            return 1;
        }
        return this.maxFrequency[n];
    }

    public int getMinValue() {
        if (this.numSamples > 0 && this.isGray) {
            return this.minValue[0];
        }
        return 1;
    }

    public int getMinValue(int n) {
        return this.minValue[n];
    }

    public int getMaxValue() {
        if (this.numSamples > 0 && this.isGray) {
            return this.maxValue[0];
        }
        return 1;
    }

    public int getMaxValue(int n) {
        return this.maxValue[n];
    }

    public float getMeanValue() {
        if (this.numSamples > 0 && this.isGray) {
            return this.mean[0];
        }
        return -1.0f;
    }

    public float getMeanValue(int n) {
        if (this.numSamples > 0 && 0 <= n && n <= 2) {
            return this.mean[n];
        }
        return -1.0f;
    }
}

