/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class ContourFilter
extends WholeImageFilter {
    private float levels = 5.0f;
    private float scale = 1.0f;
    private float offset = 0.0f;
    private int contourColor = -16777216;

    public void setLevels(float f) {
        this.levels = f;
    }

    public float getLevels() {
        return this.levels;
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public float getScale() {
        return this.scale;
    }

    public void setOffset(float f) {
        this.offset = f;
    }

    public float getOffset() {
        return this.offset;
    }

    public void setContourColor(int n) {
        this.contourColor = n;
    }

    public int getContourColor() {
        return this.contourColor;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3;
        int n4;
        int n5 = 0;
        short[][] sArray = new short[3][n];
        int[] nArray2 = new int[n * n2];
        short[] sArray2 = new short[256];
        int n6 = (int)(this.offset * 256.0f / this.levels);
        for (n4 = 0; n4 < 256; ++n4) {
            sArray2[n4] = (short)PixelUtils.clamp((int)(255.0 * Math.floor(this.levels * (float)(n4 + n6) / 256.0f) / (double)(this.levels - 1.0f) - (double)n6));
        }
        for (n4 = 0; n4 < n; ++n4) {
            n3 = nArray[n4];
            sArray[0][n4] = (short)PixelUtils.brightness(n3);
        }
        for (n4 = 0; n4 < n2; ++n4) {
            int n7;
            int n8;
            n3 = n4 > 0 && n4 < n2 - 1 ? 1 : 0;
            int n9 = n5 + n;
            if (n4 < n2 - 1) {
                for (n8 = 0; n8 < n; ++n8) {
                    n7 = nArray[n9++];
                    sArray[5][n8] = (short)PixelUtils.brightness(n7);
                }
            }
            for (n8 = 0; n8 < n; ++n8) {
                n7 = n8 > 0 && n8 < n - 1 ? 1 : 0;
                int n10 = n8 - 1;
                int n11 = n8 + 1;
                int n12 = 0;
                if (n3 != 0 && n7 != 0) {
                    short s = sArray[5][n10];
                    short s2 = sArray[5][n8];
                    short s3 = sArray[0][n10];
                    short s4 = sArray[0][n8];
                    short s5 = sArray2[s];
                    short s6 = sArray2[s2];
                    short s7 = sArray2[s3];
                    short s8 = sArray2[s4];
                    if ((s5 != s6 || s5 != s7 || s6 != s8 || s7 != s8) && (n12 = (int)(this.scale * (float)(Math.abs(s - s2) + Math.abs(s - s3) + Math.abs(s2 - s4) + Math.abs(s3 - s4)))) > 255) {
                        n12 = 255;
                    }
                }
                nArray2[n5] = n12 != 0 ? PixelUtils.combinePixels(nArray[n5], this.contourColor, 1, n12) : nArray[n5];
                ++n5;
            }
            short[] sArray3 = sArray[5];
            sArray[0] = sArray[0];
            sArray[1] = sArray[5];
            sArray[2] = sArray3;
        }
        return nArray2;
    }

    public String toString() {
        return "Stylize/Contour...";
    }
}

