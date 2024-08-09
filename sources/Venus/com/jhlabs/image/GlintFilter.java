/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.Colormap;
import com.jhlabs.image.GaussianFilter;
import com.jhlabs.image.LinearColormap;
import com.jhlabs.image.PixelUtils;
import java.awt.image.BufferedImage;

public class GlintFilter
extends AbstractBufferedImageOp {
    private float threshold = 1.0f;
    private int length = 5;
    private float blur = 0.0f;
    private float amount = 0.1f;
    private boolean glintOnly = false;
    private Colormap colormap = new LinearColormap(-1, -16777216);

    public void setThreshold(float f) {
        this.threshold = f;
    }

    public float getThreshold() {
        return this.threshold;
    }

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setLength(int n) {
        this.length = n;
    }

    public int getLength() {
        return this.length;
    }

    public void setBlur(float f) {
        this.blur = f;
    }

    public float getBlur() {
        return this.blur;
    }

    public void setGlintOnly(boolean bl) {
        this.glintOnly = bl;
    }

    public boolean getGlintOnly() {
        return this.glintOnly;
    }

    public void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    public Colormap getColormap() {
        return this.colormap;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10 = bufferedImage.getWidth();
        int n11 = bufferedImage.getHeight();
        int[] nArray = new int[n10];
        int n12 = (int)((float)this.length / 1.414f);
        int[] nArray2 = new int[this.length + 1];
        int[] nArray3 = new int[n12 + 1];
        if (this.colormap != null) {
            int n13;
            for (n13 = 0; n13 <= this.length; ++n13) {
                n9 = this.colormap.getColor((float)n13 / (float)this.length);
                n8 = n9 >> 16 & 0xFF;
                n7 = n9 >> 8 & 0xFF;
                n6 = n9 & 0xFF;
                nArray2[n13] = n9 = n9 & 0xFF000000 | (int)(this.amount * (float)n8) << 16 | (int)(this.amount * (float)n7) << 8 | (int)(this.amount * (float)n6);
            }
            for (n13 = 0; n13 <= n12; ++n13) {
                n9 = this.colormap.getColor((float)n13 / (float)n12);
                n8 = n9 >> 16 & 0xFF;
                n7 = n9 >> 8 & 0xFF;
                n6 = n9 & 0xFF;
                nArray3[n13] = n9 = n9 & 0xFF000000 | (int)(this.amount * (float)n8) << 16 | (int)(this.amount * (float)n7) << 8 | (int)(this.amount * (float)n6);
            }
        }
        BufferedImage bufferedImage3 = new BufferedImage(n10, n11, 2);
        n9 = (int)(this.threshold * 3.0f * 255.0f);
        for (n8 = 0; n8 < n11; ++n8) {
            this.getRGB(bufferedImage, 0, n8, n10, 1, nArray);
            for (n7 = 0; n7 < n10; ++n7) {
                n6 = nArray[n7];
                n5 = n6 & 0xFF000000;
                n4 = n6 >> 16 & 0xFF;
                n3 = n6 >> 8 & 0xFF;
                n2 = n6 & 0xFF;
                n = n4 + n3 + n2;
                nArray[n7] = n < n9 ? -16777216 : n5 | (n /= 3) << 16 | n << 8 | n;
            }
            this.setRGB(bufferedImage3, 0, n8, n10, 1, nArray);
        }
        if (this.blur != 0.0f) {
            bufferedImage3 = new GaussianFilter(this.blur).filter(bufferedImage3, null);
        }
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        int[] nArray4 = this.glintOnly ? new int[n10 * n11] : this.getRGB(bufferedImage, 0, 0, n10, n11, null);
        for (n7 = 0; n7 < n11; ++n7) {
            n6 = n7 * n10;
            this.getRGB(bufferedImage3, 0, n7, n10, 1, nArray);
            n5 = Math.max(n7 - this.length, 0) - n7;
            n4 = Math.min(n7 + this.length, n11 - 1) - n7;
            n3 = Math.max(n7 - n12, 0) - n7;
            n2 = Math.min(n7 + n12, n11 - 1) - n7;
            for (n = 0; n < n10; ++n) {
                if ((float)(nArray[n] & 0xFF) > this.threshold * 255.0f) {
                    int n14 = Math.max(n - this.length, 0) - n;
                    int n15 = Math.min(n + this.length, n10 - 1) - n;
                    int n16 = Math.max(n - n12, 0) - n;
                    int n17 = Math.min(n + n12, n10 - 1) - n;
                    int n18 = 0;
                    int n19 = 0;
                    while (n18 <= n15) {
                        nArray4[n6 + n18] = PixelUtils.combinePixels(nArray4[n6 + n18], nArray2[n19], 4);
                        ++n18;
                        ++n19;
                    }
                    n18 = -1;
                    n19 = 1;
                    while (n18 >= n14) {
                        nArray4[n6 + n18] = PixelUtils.combinePixels(nArray4[n6 + n18], nArray2[n19], 4);
                        --n18;
                        ++n19;
                    }
                    n18 = 1;
                    n19 = n6 + n10;
                    int n20 = 0;
                    while (n18 <= n4) {
                        nArray4[n19] = PixelUtils.combinePixels(nArray4[n19], nArray2[n20], 4);
                        ++n18;
                        n19 += n10;
                        ++n20;
                    }
                    n18 = -1;
                    n19 = n6 - n10;
                    n20 = 0;
                    while (n18 >= n5) {
                        nArray4[n19] = PixelUtils.combinePixels(nArray4[n19], nArray2[n20], 4);
                        --n18;
                        n19 -= n10;
                        ++n20;
                    }
                    n18 = Math.max(n16, n3);
                    n19 = Math.min(n17, n2);
                    n20 = Math.min(n17, n2);
                    int n21 = 1;
                    int n22 = n6 + n10 + 1;
                    int n23 = 0;
                    while (n21 <= n20) {
                        nArray4[n22] = PixelUtils.combinePixels(nArray4[n22], nArray3[n23], 4);
                        ++n21;
                        n22 += n10 + 1;
                        ++n23;
                    }
                    n20 = Math.min(-n16, -n3);
                    n21 = 1;
                    n22 = n6 - n10 - 1;
                    n23 = 0;
                    while (n21 <= n20) {
                        nArray4[n22] = PixelUtils.combinePixels(nArray4[n22], nArray3[n23], 4);
                        ++n21;
                        n22 -= n10 + 1;
                        ++n23;
                    }
                    n20 = Math.min(n17, -n3);
                    n21 = 1;
                    n22 = n6 - n10 + 1;
                    n23 = 0;
                    while (n21 <= n20) {
                        nArray4[n22] = PixelUtils.combinePixels(nArray4[n22], nArray3[n23], 4);
                        ++n21;
                        n22 += -n10 + 1;
                        ++n23;
                    }
                    n20 = Math.min(-n16, n2);
                    n21 = 1;
                    n22 = n6 + n10 - 1;
                    n23 = 0;
                    while (n21 <= n20) {
                        nArray4[n22] = PixelUtils.combinePixels(nArray4[n22], nArray3[n23], 4);
                        ++n21;
                        n22 += n10 - 1;
                        ++n23;
                    }
                }
                ++n6;
            }
        }
        this.setRGB(bufferedImage2, 0, 0, n10, n11, nArray4);
        return bufferedImage2;
    }

    public String toString() {
        return "Effects/Glint...";
    }
}

