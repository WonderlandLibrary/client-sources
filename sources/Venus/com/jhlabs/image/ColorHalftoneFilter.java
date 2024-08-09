/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ColorHalftoneFilter
extends AbstractBufferedImageOp {
    private float dotRadius = 2.0f;
    private float cyanScreenAngle = (float)Math.toRadians(108.0);
    private float magentaScreenAngle = (float)Math.toRadians(162.0);
    private float yellowScreenAngle = (float)Math.toRadians(90.0);

    public void setdotRadius(float f) {
        this.dotRadius = f;
    }

    public float getdotRadius() {
        return this.dotRadius;
    }

    public float getCyanScreenAngle() {
        return this.cyanScreenAngle;
    }

    public void setCyanScreenAngle(float f) {
        this.cyanScreenAngle = f;
    }

    public float getMagentaScreenAngle() {
        return this.magentaScreenAngle;
    }

    public void setMagentaScreenAngle(float f) {
        this.magentaScreenAngle = f;
    }

    public float getYellowScreenAngle() {
        return this.yellowScreenAngle;
    }

    public void setYellowScreenAngle(float f) {
        this.yellowScreenAngle = f;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        int n3 = bufferedImage.getType();
        WritableRaster writableRaster = bufferedImage.getRaster();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        float f = 2.0f * this.dotRadius * 1.414f;
        float[] fArray = new float[]{this.cyanScreenAngle, this.magentaScreenAngle, this.yellowScreenAngle};
        float[] fArray2 = new float[]{0.0f, -1.0f, 1.0f, 0.0f, 0.0f};
        float[] fArray3 = new float[]{0.0f, 0.0f, 0.0f, -1.0f, 1.0f};
        float f2 = f / 2.0f;
        int[] nArray = new int[n];
        int[] nArray2 = this.getRGB(bufferedImage, 0, 0, n, n2, null);
        for (int i = 0; i < n2; ++i) {
            int n4 = 0;
            int n5 = i * n;
            while (n4 < n) {
                nArray[n4] = nArray2[n5] & 0xFF000000 | 0xFFFFFF;
                ++n4;
                ++n5;
            }
            for (n4 = 0; n4 < 3; ++n4) {
                n5 = 16 - 8 * n4;
                int n6 = 255 << n5;
                float f3 = fArray[n4];
                float f4 = (float)Math.sin(f3);
                float f5 = (float)Math.cos(f3);
                int n7 = 0;
                while (n7 < n) {
                    int n8;
                    float f6 = (float)n7 * f5 + (float)i * f4;
                    float f7 = (float)(-n7) * f4 + (float)i * f5;
                    f6 = f6 - ImageMath.mod(f6 - f2, f) + f2;
                    f7 = f7 - ImageMath.mod(f7 - f2, f) + f2;
                    float f8 = 1.0f;
                    for (n8 = 0; n8 < 5; ++n8) {
                        float f9 = f6 + fArray2[n8] * f;
                        float f10 = f7 + fArray3[n8] * f;
                        float f11 = f9 * f5 - f10 * f4;
                        float f12 = f9 * f4 + f10 * f5;
                        int n9 = ImageMath.clamp((int)f11, 0, n - 1);
                        int n10 = ImageMath.clamp((int)f12, 0, n2 - 1);
                        int n11 = nArray2[n10 * n + n9];
                        int n12 = n11 >> n5 & 0xFF;
                        float f13 = (float)n12 / 255.0f;
                        f13 = 1.0f - f13 * f13;
                        f13 = (float)((double)f13 * ((double)f2 * 1.414));
                        float f14 = (float)n7 - f11;
                        float f15 = (float)i - f12;
                        float f16 = f14 * f14;
                        float f17 = f15 * f15;
                        float f18 = (float)Math.sqrt(f16 + f17);
                        float f19 = 1.0f - ImageMath.smoothStep(f18, f18 + 1.0f, f13);
                        f8 = Math.min(f8, f19);
                    }
                    n8 = (int)(255.0f * f8);
                    n8 <<= n5;
                    n8 ^= ~n6;
                    int n13 = n7++;
                    nArray[n13] = nArray[n13] & (n8 |= 0xFF000000);
                }
            }
            this.setRGB(bufferedImage2, 0, i, n, 1, nArray);
        }
        return bufferedImage2;
    }

    public String toString() {
        return "Pixellate/Color Halftone...";
    }
}

