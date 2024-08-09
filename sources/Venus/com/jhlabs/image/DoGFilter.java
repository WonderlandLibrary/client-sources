/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.composite.SubtractComposite;
import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.BoxBlurFilter;
import com.jhlabs.image.InvertFilter;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class DoGFilter
extends AbstractBufferedImageOp {
    private float radius1 = 1.0f;
    private float radius2 = 2.0f;
    private boolean normalize = true;
    private boolean invert;

    public void setRadius1(float f) {
        this.radius1 = f;
    }

    public float getRadius1() {
        return this.radius1;
    }

    public void setRadius2(float f) {
        this.radius2 = f;
    }

    public float getRadius2() {
        return this.radius2;
    }

    public void setNormalize(boolean bl) {
        this.normalize = bl;
    }

    public boolean getNormalize() {
        return this.normalize;
    }

    public void setInvert(boolean bl) {
        this.invert = bl;
    }

    public boolean getInvert() {
        return this.invert;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        BufferedImage bufferedImage3 = new BoxBlurFilter(this.radius1, this.radius1, 3).filter(bufferedImage, null);
        BufferedImage bufferedImage4 = new BoxBlurFilter(this.radius2, this.radius2, 3).filter(bufferedImage, null);
        Graphics2D graphics2D = bufferedImage4.createGraphics();
        graphics2D.setComposite(new SubtractComposite(1.0f));
        graphics2D.drawImage((Image)bufferedImage3, 0, 0, null);
        graphics2D.dispose();
        if (this.normalize && this.radius1 != this.radius2) {
            int n3;
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int[] nArray = null;
            int n9 = 0;
            for (n8 = 0; n8 < n2; ++n8) {
                nArray = this.getRGB(bufferedImage4, 0, n8, n, 1, nArray);
                for (n7 = 0; n7 < n; ++n7) {
                    n6 = nArray[n7];
                    n5 = n6 >> 16 & 0xFF;
                    n4 = n6 >> 8 & 0xFF;
                    n3 = n6 & 0xFF;
                    if (n5 > n9) {
                        n9 = n5;
                    }
                    if (n4 > n9) {
                        n9 = n4;
                    }
                    if (n3 <= n9) continue;
                    n9 = n3;
                }
            }
            for (n8 = 0; n8 < n2; ++n8) {
                nArray = this.getRGB(bufferedImage4, 0, n8, n, 1, nArray);
                for (n7 = 0; n7 < n; ++n7) {
                    n6 = nArray[n7];
                    n5 = n6 >> 16 & 0xFF;
                    n4 = n6 >> 8 & 0xFF;
                    n3 = n6 & 0xFF;
                    n5 = n5 * 255 / n9;
                    n4 = n4 * 255 / n9;
                    n3 = n3 * 255 / n9;
                    nArray[n7] = n6 & 0xFF000000 | n5 << 16 | n4 << 8 | n3;
                }
                this.setRGB(bufferedImage4, 0, n8, n, 1, nArray);
            }
        }
        if (this.invert) {
            bufferedImage4 = new InvertFilter().filter(bufferedImage4, bufferedImage4);
        }
        return bufferedImage4;
    }

    public String toString() {
        return "Edges/Difference of Gaussians...";
    }
}

