/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;

public class EmbossFilter
extends WholeImageFilter {
    private static final float pixelScale = 255.9f;
    private float azimuth = 2.3561945f;
    private float elevation = 0.5235988f;
    private boolean emboss = false;
    private float width45 = 3.0f;

    public void setAzimuth(float f) {
        this.azimuth = f;
    }

    public float getAzimuth() {
        return this.azimuth;
    }

    public void setElevation(float f) {
        this.elevation = f;
    }

    public float getElevation() {
        return this.elevation;
    }

    public void setBumpHeight(float f) {
        this.width45 = 3.0f * f;
    }

    public float getBumpHeight() {
        return this.width45 / 3.0f;
    }

    public void setEmboss(boolean bl) {
        this.emboss = bl;
    }

    public boolean getEmboss() {
        return this.emboss;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3;
        int n4 = 0;
        int[] nArray2 = new int[n * n2];
        int n5 = n;
        int n6 = n2;
        int[] nArray3 = new int[n5 * n6];
        for (n3 = 0; n3 < nArray.length; ++n3) {
            nArray3[n3] = PixelUtils.brightness(nArray[n3]);
        }
        int n7 = (int)(Math.cos(this.azimuth) * Math.cos(this.elevation) * (double)255.9f);
        int n8 = (int)(Math.sin(this.azimuth) * Math.cos(this.elevation) * (double)255.9f);
        int n9 = (int)(Math.sin(this.elevation) * (double)255.9f);
        int n10 = (int)(1530.0f / this.width45);
        int n11 = n10 * n10;
        int n12 = n10 * n9;
        int n13 = n9;
        int n14 = 0;
        int n15 = 0;
        while (n15 < n2) {
            int n16 = n14;
            int n17 = n16 + n5;
            int n18 = n17 + n5;
            int n19 = 0;
            while (n19 < n) {
                int n20;
                if (n15 != 0 && n15 < n2 - 2 && n19 != 0 && n19 < n - 2) {
                    int n21;
                    n3 = nArray3[n16 - 1] + nArray3[n17 - 1] + nArray3[n18 - 1] - nArray3[n16 + 1] - nArray3[n17 + 1] - nArray3[n18 + 1];
                    int n22 = nArray3[n18 - 1] + nArray3[n18] + nArray3[n18 + 1] - nArray3[n16 - 1] - nArray3[n16] - nArray3[n16 + 1];
                    n20 = n3 == 0 && n22 == 0 ? n13 : ((n21 = n3 * n7 + n22 * n8 + n12) < 0 ? 0 : (int)((double)n21 / Math.sqrt(n3 * n3 + n22 * n22 + n11)));
                } else {
                    n20 = n13;
                }
                if (this.emboss) {
                    int n23 = nArray[n4];
                    int n24 = n23 & 0xFF000000;
                    int n25 = n23 >> 16 & 0xFF;
                    int n26 = n23 >> 8 & 0xFF;
                    int n27 = n23 & 0xFF;
                    n25 = n25 * n20 >> 8;
                    n26 = n26 * n20 >> 8;
                    n27 = n27 * n20 >> 8;
                    nArray2[n4++] = n24 | n25 << 16 | n26 << 8 | n27;
                } else {
                    nArray2[n4++] = 0xFF000000 | n20 << 16 | n20 << 8 | n20;
                }
                ++n19;
                ++n16;
                ++n17;
                ++n18;
            }
            ++n15;
            n14 += n5;
        }
        return nArray2;
    }

    public String toString() {
        return "Stylize/Emboss...";
    }
}

