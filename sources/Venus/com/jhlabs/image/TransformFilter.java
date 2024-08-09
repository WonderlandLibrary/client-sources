/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public abstract class TransformFilter
extends AbstractBufferedImageOp {
    public static final int ZERO = 0;
    public static final int CLAMP = 1;
    public static final int WRAP = 2;
    public static final int RGB_CLAMP = 3;
    public static final int NEAREST_NEIGHBOUR = 0;
    public static final int BILINEAR = 1;
    protected int edgeAction = 3;
    protected int interpolation = 1;
    protected Rectangle transformedSpace;
    protected Rectangle originalSpace;

    public void setEdgeAction(int n) {
        this.edgeAction = n;
    }

    public int getEdgeAction() {
        return this.edgeAction;
    }

    public void setInterpolation(int n) {
        this.interpolation = n;
    }

    public int getInterpolation() {
        return this.interpolation;
    }

    protected abstract void transformInverse(int var1, int var2, float[] var3);

    protected void transformSpace(Rectangle rectangle) {
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        Object object;
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        int n3 = bufferedImage.getType();
        WritableRaster writableRaster = bufferedImage.getRaster();
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
        float[] fArray = new float[2];
        for (int i = 0; i < n9; ++i) {
            for (int j = 0; j < n8; ++j) {
                int n12;
                int n13;
                int n14;
                int n15;
                this.transformInverse(n10 + j, n11 + i, fArray);
                int n16 = (int)Math.floor(fArray[0]);
                int n17 = (int)Math.floor(fArray[1]);
                float f = fArray[0] - (float)n16;
                float f2 = fArray[1] - (float)n17;
                if (n16 >= 0 && n16 < n6 && n17 >= 0 && n17 < n7) {
                    int n18 = n4 * n17 + n16;
                    n15 = nArray[n18];
                    n14 = nArray[n18 + 1];
                    n13 = nArray[n18 + n4];
                    n12 = nArray[n18 + n4 + 1];
                } else {
                    n15 = this.getPixel(nArray, n16, n17, n4, n5);
                    n14 = this.getPixel(nArray, n16 + 1, n17, n4, n5);
                    n13 = this.getPixel(nArray, n16, n17 + 1, n4, n5);
                    n12 = this.getPixel(nArray, n16 + 1, n17 + 1, n4, n5);
                }
                nArray2[j] = ImageMath.bilinearInterpolate(f, f2, n15, n14, n13, n12);
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
                case 1: {
                    return nArray[ImageMath.clamp(n2, 0, n4 - 1) * n3 + ImageMath.clamp(n, 0, n3 - 1)];
                }
                case 3: 
            }
            return nArray[ImageMath.clamp(n2, 0, n4 - 1) * n3 + ImageMath.clamp(n, 0, n3 - 1)] & 0xFFFFFF;
        }
        return nArray[n2 * n3 + n];
    }

    protected BufferedImage filterPixelsNN(BufferedImage bufferedImage, int n, int n2, int[] nArray, Rectangle rectangle) {
        int n3 = n;
        int n4 = n2;
        int n5 = rectangle.width;
        int n6 = rectangle.height;
        int[] nArray2 = new int[n5];
        int n7 = rectangle.x;
        int n8 = rectangle.y;
        int[] nArray3 = new int[4];
        float[] fArray = new float[2];
        for (int i = 0; i < n6; ++i) {
            for (int j = 0; j < n5; ++j) {
                int n9;
                this.transformInverse(n7 + j, n8 + i, fArray);
                int n10 = (int)fArray[0];
                int n11 = (int)fArray[1];
                if (fArray[0] < 0.0f || n10 >= n3 || fArray[1] < 0.0f || n11 >= n4) {
                    switch (this.edgeAction) {
                        default: {
                            n9 = 0;
                            break;
                        }
                        case 2: {
                            n9 = nArray[ImageMath.mod(n11, n4) * n3 + ImageMath.mod(n10, n3)];
                            break;
                        }
                        case 1: {
                            n9 = nArray[ImageMath.clamp(n11, 0, n4 - 1) * n3 + ImageMath.clamp(n10, 0, n3 - 1)];
                            break;
                        }
                        case 3: {
                            n9 = nArray[ImageMath.clamp(n11, 0, n4 - 1) * n3 + ImageMath.clamp(n10, 0, n3 - 1)] & 0xFFFFFF;
                        }
                    }
                    nArray2[j] = n9;
                    continue;
                }
                n9 = n3 * n11 + n10;
                nArray3[0] = nArray[n9];
                nArray2[j] = nArray[n9];
            }
            this.setRGB(bufferedImage, 0, i, rectangle.width, 1, nArray2);
        }
        return bufferedImage;
    }
}

