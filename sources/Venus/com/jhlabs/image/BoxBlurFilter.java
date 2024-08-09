/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import java.awt.image.BufferedImage;

public class BoxBlurFilter
extends AbstractBufferedImageOp {
    private float hRadius;
    private float vRadius;
    private int iterations = 1;
    private boolean premultiplyAlpha = true;

    public BoxBlurFilter() {
    }

    public BoxBlurFilter(float f, float f2, int n) {
        this.hRadius = f;
        this.vRadius = f2;
        this.iterations = n;
    }

    public void setPremultiplyAlpha(boolean bl) {
        this.premultiplyAlpha = bl;
    }

    public boolean getPremultiplyAlpha() {
        return this.premultiplyAlpha;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        int[] nArray = new int[n * n2];
        int[] nArray2 = new int[n * n2];
        this.getRGB(bufferedImage, 0, 0, n, n2, nArray);
        if (this.premultiplyAlpha) {
            ImageMath.premultiply(nArray, 0, nArray.length);
        }
        for (int i = 0; i < this.iterations; ++i) {
            BoxBlurFilter.blur(nArray, nArray2, n, n2, this.hRadius);
            BoxBlurFilter.blur(nArray2, nArray, n2, n, this.vRadius);
        }
        BoxBlurFilter.blurFractional(nArray, nArray2, n, n2, this.hRadius);
        BoxBlurFilter.blurFractional(nArray2, nArray, n2, n, this.vRadius);
        if (this.premultiplyAlpha) {
            ImageMath.unpremultiply(nArray, 0, nArray.length);
        }
        this.setRGB(bufferedImage2, 0, 0, n, n2, nArray);
        return bufferedImage2;
    }

    public static void blur(int[] nArray, int[] nArray2, int n, int n2, float f) {
        int n3;
        int n4 = n - 1;
        int n5 = (int)f;
        int n6 = 2 * n5 + 1;
        int[] nArray3 = new int[256 * n6];
        for (n3 = 0; n3 < 256 * n6; ++n3) {
            nArray3[n3] = n3 / n6;
        }
        n3 = 0;
        for (int i = 0; i < n2; ++i) {
            int n7;
            int n8;
            int n9 = i;
            int n10 = 0;
            int n11 = 0;
            int n12 = 0;
            int n13 = 0;
            for (n8 = -n5; n8 <= n5; ++n8) {
                n7 = nArray[n3 + ImageMath.clamp(n8, 0, n - 1)];
                n10 += n7 >> 24 & 0xFF;
                n11 += n7 >> 16 & 0xFF;
                n12 += n7 >> 8 & 0xFF;
                n13 += n7 & 0xFF;
            }
            for (n8 = 0; n8 < n; ++n8) {
                int n14;
                nArray2[n9] = nArray3[n10] << 24 | nArray3[n11] << 16 | nArray3[n12] << 8 | nArray3[n13];
                n7 = n8 + n5 + 1;
                if (n7 > n4) {
                    n7 = n4;
                }
                if ((n14 = n8 - n5) < 0) {
                    n14 = 0;
                }
                int n15 = nArray[n3 + n7];
                int n16 = nArray[n3 + n14];
                n10 += (n15 >> 24 & 0xFF) - (n16 >> 24 & 0xFF);
                n11 += (n15 & 0xFF0000) - (n16 & 0xFF0000) >> 16;
                n12 += (n15 & 0xFF00) - (n16 & 0xFF00) >> 8;
                n13 += (n15 & 0xFF) - (n16 & 0xFF);
                n9 += n2;
            }
            n3 += n;
        }
    }

    public static void blurFractional(int[] nArray, int[] nArray2, int n, int n2, float f) {
        f -= (float)((int)f);
        float f2 = 1.0f / (1.0f + 2.0f * f);
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            int n4 = i;
            nArray2[n4] = nArray[0];
            n4 += n2;
            for (int j = 1; j < n - 1; ++j) {
                int n5 = n3 + j;
                int n6 = nArray[n5 - 1];
                int n7 = nArray[n5];
                int n8 = nArray[n5 + 1];
                int n9 = n6 >> 24 & 0xFF;
                int n10 = n6 >> 16 & 0xFF;
                int n11 = n6 >> 8 & 0xFF;
                int n12 = n6 & 0xFF;
                int n13 = n7 >> 24 & 0xFF;
                int n14 = n7 >> 16 & 0xFF;
                int n15 = n7 >> 8 & 0xFF;
                int n16 = n7 & 0xFF;
                int n17 = n8 >> 24 & 0xFF;
                int n18 = n8 >> 16 & 0xFF;
                int n19 = n8 >> 8 & 0xFF;
                int n20 = n8 & 0xFF;
                n9 = n13 + (int)((float)(n9 + n17) * f);
                n10 = n14 + (int)((float)(n10 + n18) * f);
                n11 = n15 + (int)((float)(n11 + n19) * f);
                n12 = n16 + (int)((float)(n12 + n20) * f);
                n9 = (int)((float)n9 * f2);
                n10 = (int)((float)n10 * f2);
                n11 = (int)((float)n11 * f2);
                n12 = (int)((float)n12 * f2);
                nArray2[n4] = n9 << 24 | n10 << 16 | n11 << 8 | n12;
                n4 += n2;
            }
            nArray2[n4] = nArray[n - 1];
            n3 += n;
        }
    }

    public void setHRadius(float f) {
        this.hRadius = f;
    }

    public float getHRadius() {
        return this.hRadius;
    }

    public void setVRadius(float f) {
        this.vRadius = f;
    }

    public float getVRadius() {
        return this.vRadius;
    }

    public void setRadius(float f) {
        this.hRadius = this.vRadius = f;
    }

    public float getRadius() {
        return this.hRadius;
    }

    public void setIterations(int n) {
        this.iterations = n;
    }

    public int getIterations() {
        return this.iterations;
    }

    public String toString() {
        return "Blur/Box Blur...";
    }
}

