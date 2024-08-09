/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.composite.MiscComposite;
import com.jhlabs.image.Colormap;
import com.jhlabs.image.MotionBlurOp;
import com.jhlabs.image.PixelUtils;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RaysFilter
extends MotionBlurOp {
    private float opacity = 1.0f;
    private float threshold = 0.0f;
    private float strength = 0.5f;
    private boolean raysOnly = false;
    private Colormap colormap;

    public void setOpacity(float f) {
        this.opacity = f;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setThreshold(float f) {
        this.threshold = f;
    }

    public float getThreshold() {
        return this.threshold;
    }

    public void setStrength(float f) {
        this.strength = f;
    }

    public float getStrength() {
        return this.strength;
    }

    public void setRaysOnly(boolean bl) {
        this.raysOnly = bl;
    }

    public boolean getRaysOnly() {
        return this.raysOnly;
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
        int n9 = bufferedImage.getWidth();
        int n10 = bufferedImage.getHeight();
        int[] nArray = new int[n9];
        int[] nArray2 = new int[n9];
        BufferedImage bufferedImage3 = new BufferedImage(n9, n10, 2);
        int n11 = (int)(this.threshold * 3.0f * 255.0f);
        for (n8 = 0; n8 < n10; ++n8) {
            this.getRGB(bufferedImage, 0, n8, n9, 1, nArray);
            for (n7 = 0; n7 < n9; ++n7) {
                n6 = nArray[n7];
                n5 = n6 & 0xFF000000;
                n4 = n6 >> 16 & 0xFF;
                n3 = n6 >> 8 & 0xFF;
                n2 = n6 & 0xFF;
                n = n4 + n3 + n2;
                nArray[n7] = n < n11 ? -16777216 : n5 | (n /= 3) << 16 | n << 8 | n;
            }
            this.setRGB(bufferedImage3, 0, n8, n9, 1, nArray);
        }
        bufferedImage3 = super.filter(bufferedImage3, null);
        for (n8 = 0; n8 < n10; ++n8) {
            this.getRGB(bufferedImage3, 0, n8, n9, 1, nArray);
            this.getRGB(bufferedImage, 0, n8, n9, 1, nArray2);
            for (n7 = 0; n7 < n9; ++n7) {
                n6 = nArray[n7];
                n5 = n6 & 0xFF000000;
                n4 = n6 >> 16 & 0xFF;
                n3 = n6 >> 8 & 0xFF;
                n2 = n6 & 0xFF;
                if (this.colormap != null) {
                    n = n4 + n3 + n2;
                    n6 = this.colormap.getColor((float)n * this.strength * 0.33333334f);
                } else {
                    n4 = PixelUtils.clamp((int)((float)n4 * this.strength));
                    n3 = PixelUtils.clamp((int)((float)n3 * this.strength));
                    n2 = PixelUtils.clamp((int)((float)n2 * this.strength));
                    n6 = n5 | n4 << 16 | n3 << 8 | n2;
                }
                nArray[n7] = n6;
            }
            this.setRGB(bufferedImage3, 0, n8, n9, 1, nArray);
        }
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        if (!this.raysOnly) {
            graphics2D.setComposite(AlphaComposite.SrcOver);
            graphics2D.drawRenderedImage(bufferedImage, null);
        }
        graphics2D.setComposite(MiscComposite.getInstance(1, this.opacity));
        graphics2D.drawRenderedImage(bufferedImage3, null);
        graphics2D.dispose();
        return bufferedImage2;
    }

    @Override
    public String toString() {
        return "Stylize/Rays...";
    }
}

