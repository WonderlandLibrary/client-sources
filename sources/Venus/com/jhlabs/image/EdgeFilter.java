/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class EdgeFilter
extends WholeImageFilter {
    public static final float R2 = (float)Math.sqrt(2.0);
    public static final float[] ROBERTS_V = new float[]{0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    public static final float[] ROBERTS_H = new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    public static final float[] PREWITT_V = new float[]{-1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f};
    public static final float[] PREWITT_H = new float[]{-1.0f, -1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
    public static final float[] SOBEL_V = new float[]{-1.0f, 0.0f, 1.0f, -2.0f, 0.0f, 2.0f, -1.0f, 0.0f, 1.0f};
    public static float[] SOBEL_H = new float[]{-1.0f, -2.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 2.0f, 1.0f};
    public static final float[] FREI_CHEN_V = new float[]{-1.0f, 0.0f, 1.0f, -R2, 0.0f, R2, -1.0f, 0.0f, 1.0f};
    public static float[] FREI_CHEN_H = new float[]{-1.0f, -R2, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, R2, 1.0f};
    protected float[] vEdgeMatrix = SOBEL_V;
    protected float[] hEdgeMatrix = SOBEL_H;

    public void setVEdgeMatrix(float[] fArray) {
        this.vEdgeMatrix = fArray;
    }

    public float[] getVEdgeMatrix() {
        return this.vEdgeMatrix;
    }

    public void setHEdgeMatrix(float[] fArray) {
        this.hEdgeMatrix = fArray;
    }

    public float[] getHEdgeMatrix() {
        return this.hEdgeMatrix;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3 = 0;
        int[] nArray2 = new int[n * n2];
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n4 = 0;
                int n5 = 0;
                int n6 = 0;
                int n7 = 0;
                int n8 = 0;
                int n9 = 0;
                int n10 = 0;
                int n11 = 0;
                int n12 = 0;
                int n13 = nArray[i * n + j] & 0xFF000000;
                for (int k = -1; k <= 1; ++k) {
                    int n14 = i + k;
                    int n15 = 0 <= n14 && n14 < n2 ? n14 * n : i * n;
                    int n16 = 3 * (k + 1) + 1;
                    for (int i2 = -1; i2 <= 1; ++i2) {
                        int n17 = j + i2;
                        if (0 > n17 || n17 >= n) {
                            n17 = j;
                        }
                        int n18 = nArray[n15 + n17];
                        float f = this.hEdgeMatrix[n16 + i2];
                        float f2 = this.vEdgeMatrix[n16 + i2];
                        n4 = (n18 & 0xFF0000) >> 16;
                        n5 = (n18 & 0xFF00) >> 8;
                        n6 = n18 & 0xFF;
                        n7 += (int)(f * (float)n4);
                        n8 += (int)(f * (float)n5);
                        n9 += (int)(f * (float)n6);
                        n10 += (int)(f2 * (float)n4);
                        n11 += (int)(f2 * (float)n5);
                        n12 += (int)(f2 * (float)n6);
                    }
                }
                n4 = (int)(Math.sqrt(n7 * n7 + n10 * n10) / 1.8);
                n5 = (int)(Math.sqrt(n8 * n8 + n11 * n11) / 1.8);
                n6 = (int)(Math.sqrt(n9 * n9 + n12 * n12) / 1.8);
                n4 = PixelUtils.clamp(n4);
                n5 = PixelUtils.clamp(n5);
                n6 = PixelUtils.clamp(n6);
                nArray2[n3++] = n13 | n4 << 16 | n5 << 8 | n6;
            }
        }
        return nArray2;
    }

    public String toString() {
        return "Edges/Detect Edges";
    }
}

