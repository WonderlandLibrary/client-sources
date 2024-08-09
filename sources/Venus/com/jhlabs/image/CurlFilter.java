/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.ImageUtils;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.TransformFilter;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class CurlFilter
extends TransformFilter {
    private float angle = 0.0f;
    private float transition = 0.0f;
    private float width;
    private float height;
    private float radius;

    public CurlFilter() {
        this.setEdgeAction(0);
    }

    public void setTransition(float f) {
        this.transition = f;
    }

    public float getTransition() {
        return this.transition;
    }

    public void setAngle(float f) {
        this.angle = f;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setRadius(float f) {
        this.radius = f;
    }

    public float getRadius() {
        return this.radius;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        Object object;
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        int n3 = bufferedImage.getType();
        this.originalSpace = new Rectangle(0, 0, n, n2);
        this.transformedSpace = new Rectangle(0, 0, n, n2);
        this.transformSpace(this.transformedSpace);
        if (bufferedImage2 == null) {
            object = bufferedImage.getColorModel();
            bufferedImage2 = new BufferedImage((ColorModel)object, ((ColorModel)object).createCompatibleWritableRaster(this.transformedSpace.width, this.transformedSpace.height), ((ColorModel)object).isAlphaPremultiplied(), null);
        }
        object = bufferedImage2.getRaster();
        int[] nArray = this.getRGB(bufferedImage, 0, 0, n, n2, null);
        if (this.interpolation == 0) {
            return this.filterPixelsNN(bufferedImage2, n, n2, nArray, this.transformedSpace);
        }
        int n4 = n;
        int n5 = n2;
        int n6 = n - 1;
        int n7 = n2 - 1;
        int n8 = this.transformedSpace.width;
        int n9 = this.transformedSpace.height;
        boolean bl = false;
        int[] nArray2 = new int[n8];
        int n10 = this.transformedSpace.x;
        int n11 = this.transformedSpace.y;
        float[] fArray = new float[4];
        for (int i = 0; i < n9; ++i) {
            for (int j = 0; j < n8; ++j) {
                int n12;
                int n13;
                int n14;
                int n15;
                int n16;
                this.transformInverse(n10 + j, n11 + i, fArray);
                int n17 = (int)Math.floor(fArray[0]);
                int n18 = (int)Math.floor(fArray[1]);
                float f = fArray[0] - (float)n17;
                float f2 = fArray[1] - (float)n18;
                if (n17 >= 0 && n17 < n6 && n18 >= 0 && n18 < n7) {
                    n16 = n4 * n18 + n17;
                    n15 = nArray[n16];
                    n14 = nArray[n16 + 1];
                    n13 = nArray[n16 + n4];
                    n12 = nArray[n16 + n4 + 1];
                } else {
                    n15 = this.getPixel(nArray, n17, n18, n4, n5);
                    n14 = this.getPixel(nArray, n17 + 1, n18, n4, n5);
                    n13 = this.getPixel(nArray, n17, n18 + 1, n4, n5);
                    n12 = this.getPixel(nArray, n17 + 1, n18 + 1, n4, n5);
                }
                n16 = ImageMath.bilinearInterpolate(f, f2, n15, n14, n13, n12);
                int n19 = n16 >> 16 & 0xFF;
                int n20 = n16 >> 8 & 0xFF;
                int n21 = n16 & 0xFF;
                float f3 = fArray[2];
                n19 = (int)((float)n19 * f3);
                n20 = (int)((float)n20 * f3);
                n21 = (int)((float)n21 * f3);
                n16 = n16 & 0xFF000000 | n19 << 16 | n20 << 8 | n21;
                nArray2[j] = fArray[3] != 0.0f ? PixelUtils.combinePixels(n16, nArray[n4 * i + j], 1) : n16;
            }
            this.setRGB(bufferedImage2, 0, i, this.transformedSpace.width, 1, nArray2);
        }
        return bufferedImage2;
    }

    private final int getPixel(int[] nArray, int n, int n2, int n3, int n4) {
        if (n < 0 || n >= n3 || n2 < 0 || n2 >= n4) {
            switch (this.edgeAction) {
                default: {
                    return 1;
                }
                case 2: {
                    return nArray[ImageMath.mod(n2, n4) * n3 + ImageMath.mod(n, n3)];
                }
                case 1: 
            }
            return nArray[ImageMath.clamp(n2, 0, n4 - 1) * n3 + ImageMath.clamp(n, 0, n3 - 1)];
        }
        return nArray[n2 * n3 + n];
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        boolean bl;
        float f = this.transition;
        float f2 = n;
        float f3 = n2;
        float f4 = (float)Math.sin(this.angle);
        float f5 = (float)Math.cos(this.angle);
        float f6 = f * this.width;
        f6 = f * (float)Math.sqrt(this.width * this.width + this.height * this.height);
        float f7 = f5 < 0.0f ? this.width : 0.0f;
        float f8 = f4 < 0.0f ? this.height : 0.0f;
        float f9 = (f2 -= f7) * f5 + (f3 -= f8) * f4;
        float f10 = -f2 * f4 + f3 * f5;
        boolean bl2 = f9 < f6;
        boolean bl3 = f9 > f6 * 2.0f;
        boolean bl4 = !bl2 && !bl3;
        f9 = f9 > f6 * 2.0f ? f9 : 2.0f * f6 - f9;
        f2 = f9 * f5 - f10 * f4;
        f3 = f9 * f4 + f10 * f5;
        boolean bl5 = bl = (f2 += f7) < 0.0f || (f3 += f8) < 0.0f || f2 >= this.width || f3 >= this.height;
        if (bl && bl4) {
            f2 = n;
            f3 = n2;
        }
        float f11 = !bl && bl4 ? 1.9f * (1.0f - (float)Math.cos(Math.exp((f9 - f6) / this.radius))) : 0.0f;
        fArray[2] = 1.0f - f11;
        if (bl2) {
            fArray[1] = -1.0f;
            fArray[0] = -1.0f;
        } else {
            fArray[0] = f2;
            fArray[1] = f3;
        }
        fArray[3] = !bl && bl4 ? 1.0f : 0.0f;
    }

    public String toString() {
        return "Distort/Curl...";
    }

    static class Sampler {
        private int edgeAction;
        private int width;
        private int height;
        private int[] inPixels;

        public Sampler(BufferedImage bufferedImage) {
            int n = bufferedImage.getWidth();
            int n2 = bufferedImage.getHeight();
            int n3 = bufferedImage.getType();
            this.inPixels = ImageUtils.getRGB(bufferedImage, 0, 0, n, n2, null);
        }

        public int sample(float f, float f2) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5 = (int)Math.floor(f);
            int n6 = (int)Math.floor(f2);
            float f3 = f - (float)n5;
            float f4 = f2 - (float)n6;
            if (n5 >= 0 && n5 < this.width - 1 && n6 >= 0 && n6 < this.height - 1) {
                int n7 = this.width * n6 + n5;
                n4 = this.inPixels[n7];
                n3 = this.inPixels[n7 + 1];
                n2 = this.inPixels[n7 + this.width];
                n = this.inPixels[n7 + this.width + 1];
            } else {
                n4 = this.getPixel(this.inPixels, n5, n6, this.width, this.height);
                n3 = this.getPixel(this.inPixels, n5 + 1, n6, this.width, this.height);
                n2 = this.getPixel(this.inPixels, n5, n6 + 1, this.width, this.height);
                n = this.getPixel(this.inPixels, n5 + 1, n6 + 1, this.width, this.height);
            }
            return ImageMath.bilinearInterpolate(f3, f4, n4, n3, n2, n);
        }

        private final int getPixel(int[] nArray, int n, int n2, int n3, int n4) {
            if (n < 0 || n >= n3 || n2 < 0 || n2 >= n4) {
                switch (this.edgeAction) {
                    default: {
                        return 1;
                    }
                    case 2: {
                        return nArray[ImageMath.mod(n2, n4) * n3 + ImageMath.mod(n, n3)];
                    }
                    case 1: 
                }
                return nArray[ImageMath.clamp(n2, 0, n4 - 1) * n3 + ImageMath.clamp(n, 0, n3 - 1)];
            }
            return nArray[n2 * n3 + n];
        }
    }
}

