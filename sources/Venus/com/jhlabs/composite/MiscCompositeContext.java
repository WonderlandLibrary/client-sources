/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.composite;

import java.awt.Color;
import java.awt.CompositeContext;
import java.awt.color.ColorSpace;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;

public class MiscCompositeContext
implements CompositeContext {
    private int rule;
    private float alpha;
    private ColorModel srcColorModel;
    private ColorModel dstColorModel;
    private ColorSpace srcColorSpace;
    private ColorSpace dstColorSpace;
    private boolean srcNeedsConverting;
    private boolean dstNeedsConverting;

    public MiscCompositeContext(int n, float f, ColorModel colorModel, ColorModel colorModel2) {
        this.rule = n;
        this.alpha = f;
        this.srcColorModel = colorModel;
        this.dstColorModel = colorModel2;
        this.srcColorSpace = colorModel.getColorSpace();
        this.dstColorSpace = colorModel2.getColorSpace();
        ColorModel colorModel3 = ColorModel.getRGBdefault();
    }

    @Override
    public void dispose() {
    }

    static int multiply255(int n, int n2) {
        int n3 = n * n2 + 128;
        return (n3 >> 8) + n3 >> 8;
    }

    static int clamp(int n) {
        return n < 0 ? 0 : (n > 255 ? 255 : n);
    }

    @Override
    public void compose(Raster raster, Raster raster2, WritableRaster writableRaster) {
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = this.alpha;
        float[] fArray = null;
        float[] fArray2 = null;
        float[] fArray3 = null;
        switch (this.rule) {
            case 12: 
            case 13: 
            case 14: 
            case 15: {
                fArray = new float[3];
                fArray2 = new float[3];
                fArray3 = new float[3];
            }
        }
        int[] nArray = null;
        int[] nArray2 = null;
        int n = writableRaster.getMinX();
        int n2 = writableRaster.getWidth();
        int n3 = writableRaster.getMinY();
        int n4 = n3 + writableRaster.getHeight();
        for (int i = n3; i < n4; ++i) {
            nArray = raster.getPixels(n, i, n2, 1, nArray);
            nArray2 = raster2.getPixels(n, i, n2, 1, nArray2);
            int n5 = n2 * 4;
            for (int j = 0; j < n5; j += 4) {
                int n6;
                int n7;
                int n8;
                int n9 = nArray[j];
                int n10 = nArray2[j];
                int n11 = nArray[j + 1];
                int n12 = nArray2[j + 1];
                int n13 = nArray[j + 2];
                int n14 = nArray2[j + 2];
                int n15 = nArray[j + 3];
                int n16 = nArray2[j + 3];
                switch (this.rule) {
                    default: {
                        n8 = n10 + n9;
                        if (n8 > 255) {
                            n8 = 255;
                        }
                        if ((n7 = n12 + n11) > 255) {
                            n7 = 255;
                        }
                        if ((n6 = n14 + n13) <= 255) break;
                        n6 = 255;
                        break;
                    }
                    case 2: {
                        n8 = n10 - n9;
                        if (n8 < 0) {
                            n8 = 0;
                        }
                        if ((n7 = n12 - n11) < 0) {
                            n7 = 0;
                        }
                        if ((n6 = n14 - n13) >= 0) break;
                        n6 = 0;
                        break;
                    }
                    case 3: {
                        n8 = n10 - n9;
                        if (n8 < 0) {
                            n8 = -n8;
                        }
                        if ((n7 = n12 - n11) < 0) {
                            n7 = -n7;
                        }
                        if ((n6 = n14 - n13) >= 0) break;
                        n6 = -n6;
                        break;
                    }
                    case 4: {
                        int n17 = n10 * n9 + 128;
                        n8 = (n17 >> 8) + n17 >> 8;
                        n17 = n12 * n11 + 128;
                        n7 = (n17 >> 8) + n17 >> 8;
                        n17 = n14 * n13 + 128;
                        n6 = (n17 >> 8) + n17 >> 8;
                        break;
                    }
                    case 8: {
                        int n17 = (255 - n10) * (255 - n9) + 128;
                        n8 = 255 - ((n17 >> 8) + n17 >> 8);
                        n17 = (255 - n12) * (255 - n11) + 128;
                        n7 = 255 - ((n17 >> 8) + n17 >> 8);
                        n17 = (255 - n14) * (255 - n13) + 128;
                        n6 = 255 - ((n17 >> 8) + n17 >> 8);
                        break;
                    }
                    case 16: {
                        int n17;
                        if (n10 < 128) {
                            n17 = n10 * n9 + 128;
                            n8 = 2 * ((n17 >> 8) + n17 >> 8);
                        } else {
                            n17 = (255 - n10) * (255 - n9) + 128;
                            n8 = 2 * (255 - ((n17 >> 8) + n17 >> 8));
                        }
                        if (n12 < 128) {
                            n17 = n12 * n11 + 128;
                            n7 = 2 * ((n17 >> 8) + n17 >> 8);
                        } else {
                            n17 = (255 - n12) * (255 - n11) + 128;
                            n7 = 2 * (255 - ((n17 >> 8) + n17 >> 8));
                        }
                        if (n14 < 128) {
                            n17 = n14 * n13 + 128;
                            n6 = 2 * ((n17 >> 8) + n17 >> 8);
                            break;
                        }
                        n17 = (255 - n14) * (255 - n13) + 128;
                        n6 = 2 * (255 - ((n17 >> 8) + n17 >> 8));
                        break;
                    }
                    case 5: {
                        n8 = n10 < n9 ? n10 : n9;
                        n7 = n12 < n11 ? n12 : n11;
                        n6 = n14 < n13 ? n14 : n13;
                        break;
                    }
                    case 9: {
                        n8 = n10 > n9 ? n10 : n9;
                        n7 = n12 > n11 ? n12 : n11;
                        n6 = n14 > n13 ? n14 : n13;
                        break;
                    }
                    case 22: {
                        n8 = (n10 + n9) / 2;
                        n7 = (n12 + n11) / 2;
                        n6 = (n14 + n13) / 2;
                        break;
                    }
                    case 12: 
                    case 13: 
                    case 14: 
                    case 15: {
                        Color.RGBtoHSB(n9, n11, n13, fArray);
                        Color.RGBtoHSB(n10, n12, n14, fArray2);
                        switch (this.rule) {
                            case 12: {
                                fArray3[0] = fArray[0];
                                fArray3[1] = fArray2[1];
                                fArray3[2] = fArray2[2];
                                break;
                            }
                            case 13: {
                                fArray3[0] = fArray2[0];
                                fArray3[1] = fArray[1];
                                fArray3[2] = fArray2[2];
                                break;
                            }
                            case 14: {
                                fArray3[0] = fArray2[0];
                                fArray3[1] = fArray2[1];
                                fArray3[2] = fArray[2];
                                break;
                            }
                            case 15: {
                                fArray3[0] = fArray[0];
                                fArray3[1] = fArray[1];
                                fArray3[2] = fArray2[2];
                            }
                        }
                        int n18 = Color.HSBtoRGB(fArray3[0], fArray3[1], fArray3[2]);
                        n8 = (n18 & 0xFF0000) >> 16;
                        n7 = (n18 & 0xFF00) >> 8;
                        n6 = n18 & 0xFF;
                        break;
                    }
                    case 6: {
                        n8 = n10 != 255 ? MiscCompositeContext.clamp(255 - (255 - n9 << 8) / (n10 + 1)) : n9;
                        n7 = n12 != 255 ? MiscCompositeContext.clamp(255 - (255 - n11 << 8) / (n12 + 1)) : n11;
                        if (n14 != 255) {
                            n6 = MiscCompositeContext.clamp(255 - (255 - n13 << 8) / (n14 + 1));
                            break;
                        }
                        n6 = n13;
                        break;
                    }
                    case 7: {
                        n8 = n9 != 0 ? Math.max(255 - (255 - n10 << 8) / n9, 0) : n9;
                        n7 = n11 != 0 ? Math.max(255 - (255 - n12 << 8) / n11, 0) : n11;
                        if (n13 != 0) {
                            n6 = Math.max(255 - (255 - n14 << 8) / n13, 0);
                            break;
                        }
                        n6 = n13;
                        break;
                    }
                    case 10: {
                        n8 = MiscCompositeContext.clamp((n9 << 8) / (256 - n10));
                        n7 = MiscCompositeContext.clamp((n11 << 8) / (256 - n12));
                        n6 = MiscCompositeContext.clamp((n13 << 8) / (256 - n14));
                        break;
                    }
                    case 11: {
                        n8 = n9 != 255 ? Math.min((n10 << 8) / (255 - n9), 255) : n9;
                        n7 = n11 != 255 ? Math.min((n12 << 8) / (255 - n11), 255) : n11;
                        if (n13 != 255) {
                            n6 = Math.min((n14 << 8) / (255 - n13), 255);
                            break;
                        }
                        n6 = n13;
                        break;
                    }
                    case 17: {
                        int n19 = MiscCompositeContext.multiply255(n9, n10);
                        n8 = n19 + MiscCompositeContext.multiply255(n10, 255 - MiscCompositeContext.multiply255(255 - n10, 255 - n9) - n19);
                        n19 = MiscCompositeContext.multiply255(n11, n12);
                        n7 = n19 + MiscCompositeContext.multiply255(n12, 255 - MiscCompositeContext.multiply255(255 - n12, 255 - n11) - n19);
                        n19 = MiscCompositeContext.multiply255(n13, n14);
                        n6 = n19 + MiscCompositeContext.multiply255(n14, 255 - MiscCompositeContext.multiply255(255 - n14, 255 - n13) - n19);
                        break;
                    }
                    case 18: {
                        n8 = n9 > 127 ? 255 - 2 * MiscCompositeContext.multiply255(255 - n9, 255 - n10) : 2 * MiscCompositeContext.multiply255(n9, n10);
                        n7 = n11 > 127 ? 255 - 2 * MiscCompositeContext.multiply255(255 - n11, 255 - n12) : 2 * MiscCompositeContext.multiply255(n11, n12);
                        if (n13 > 127) {
                            n6 = 255 - 2 * MiscCompositeContext.multiply255(255 - n13, 255 - n14);
                            break;
                        }
                        n6 = 2 * MiscCompositeContext.multiply255(n13, n14);
                        break;
                    }
                    case 19: {
                        n8 = n9 > 127 ? Math.max(n9, n10) : Math.min(n9, n10);
                        n7 = n11 > 127 ? Math.max(n11, n12) : Math.min(n11, n12);
                        n6 = n13 > 127 ? Math.max(n13, n14) : Math.min(n13, n14);
                        break;
                    }
                    case 20: {
                        n8 = n10 + MiscCompositeContext.multiply255(n9, 255 - n10 - n10);
                        n7 = n12 + MiscCompositeContext.multiply255(n11, 255 - n12 - n12);
                        n6 = n14 + MiscCompositeContext.multiply255(n13, 255 - n14 - n14);
                        break;
                    }
                    case 21: {
                        n8 = 255 - Math.abs(255 - n9 - n10);
                        n7 = 255 - Math.abs(255 - n11 - n12);
                        n6 = 255 - Math.abs(255 - n13 - n14);
                    }
                }
                f = f3 * (float)n15 / 255.0f;
                f2 = 1.0f - f;
                nArray2[j] = (int)(f * (float)n8 + f2 * (float)n10);
                nArray2[j + 1] = (int)(f * (float)n7 + f2 * (float)n12);
                nArray2[j + 2] = (int)(f * (float)n6 + f2 * (float)n14);
                nArray2[j + 3] = (int)((float)n15 * f3 + (float)n16 * f2);
            }
            writableRaster.setPixels(n, i, n2, 1, nArray2);
        }
    }
}

