/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class FlipFilter
extends AbstractBufferedImageOp {
    public static final int FLIP_H = 1;
    public static final int FLIP_V = 2;
    public static final int FLIP_HV = 3;
    public static final int FLIP_90CW = 4;
    public static final int FLIP_90CCW = 5;
    public static final int FLIP_180 = 6;
    private int operation;
    private int width;
    private int height;
    private int newWidth;
    private int newHeight;

    public FlipFilter() {
        this(3);
    }

    public FlipFilter(int n) {
        this.operation = n;
    }

    public void setOperation(int n) {
        this.operation = n;
    }

    public int getOperation() {
        return this.operation;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        int n3 = bufferedImage.getType();
        WritableRaster writableRaster = bufferedImage.getRaster();
        int[] nArray = this.getRGB(bufferedImage, 0, 0, n, n2, null);
        int n4 = 0;
        int n5 = 0;
        int n6 = n;
        int n7 = n2;
        int n8 = 0;
        int n9 = 0;
        int n10 = n6;
        int n11 = n7;
        switch (this.operation) {
            case 1: {
                n8 = n - (n4 + n6);
                break;
            }
            case 2: {
                n9 = n2 - (n5 + n7);
                break;
            }
            case 3: {
                n10 = n7;
                n11 = n6;
                n8 = n5;
                n9 = n4;
                break;
            }
            case 4: {
                n10 = n7;
                n11 = n6;
                n8 = n2 - (n5 + n7);
                n9 = n4;
                break;
            }
            case 5: {
                n10 = n7;
                n11 = n6;
                n8 = n5;
                n9 = n - (n4 + n6);
                break;
            }
            case 6: {
                n8 = n - (n4 + n6);
                n9 = n2 - (n5 + n7);
            }
        }
        int[] nArray2 = new int[n10 * n11];
        for (int i = 0; i < n7; ++i) {
            for (int j = 0; j < n6; ++j) {
                int n12 = i * n + j;
                int n13 = i;
                int n14 = j;
                switch (this.operation) {
                    case 1: {
                        n14 = n6 - j - 1;
                        break;
                    }
                    case 2: {
                        n13 = n7 - i - 1;
                        break;
                    }
                    case 3: {
                        n13 = j;
                        n14 = i;
                        break;
                    }
                    case 4: {
                        n13 = j;
                        n14 = n7 - i - 1;
                        break;
                    }
                    case 5: {
                        n13 = n6 - j - 1;
                        n14 = i;
                        break;
                    }
                    case 6: {
                        n13 = n7 - i - 1;
                        n14 = n6 - j - 1;
                    }
                }
                int n15 = n13 * n10 + n14;
                nArray2[n15] = nArray[n12];
            }
        }
        if (bufferedImage2 == null) {
            ColorModel colorModel = bufferedImage.getColorModel();
            bufferedImage2 = new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(n10, n11), colorModel.isAlphaPremultiplied(), null);
        }
        WritableRaster writableRaster2 = bufferedImage2.getRaster();
        this.setRGB(bufferedImage2, 0, 0, n10, n11, nArray2);
        return bufferedImage2;
    }

    public String toString() {
        switch (this.operation) {
            case 1: {
                return "Flip Horizontal";
            }
            case 2: {
                return "Flip Vertical";
            }
            case 3: {
                return "Flip Diagonal";
            }
            case 4: {
                return "Rotate 90";
            }
            case 5: {
                return "Rotate -90";
            }
            case 6: {
                return "Rotate 180";
            }
        }
        return "Flip";
    }
}

