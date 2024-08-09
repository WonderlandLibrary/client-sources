/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Kernel;

public class ConvolveFilter
extends AbstractBufferedImageOp {
    public static int ZERO_EDGES = 0;
    public static int CLAMP_EDGES = 1;
    public static int WRAP_EDGES = 2;
    protected Kernel kernel = null;
    protected boolean alpha = true;
    protected boolean premultiplyAlpha = true;
    private int edgeAction = CLAMP_EDGES;

    public ConvolveFilter() {
        this(new float[9]);
    }

    public ConvolveFilter(float[] fArray) {
        this(new Kernel(3, 3, fArray));
    }

    public ConvolveFilter(int n, int n2, float[] fArray) {
        this(new Kernel(n2, n, fArray));
    }

    public ConvolveFilter(Kernel kernel) {
        this.kernel = kernel;
    }

    public void setKernel(Kernel kernel) {
        this.kernel = kernel;
    }

    public Kernel getKernel() {
        return this.kernel;
    }

    public void setEdgeAction(int n) {
        this.edgeAction = n;
    }

    public int getEdgeAction() {
        return this.edgeAction;
    }

    public void setUseAlpha(boolean bl) {
        this.alpha = bl;
    }

    public boolean getUseAlpha() {
        return this.alpha;
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
        ConvolveFilter.convolve(this.kernel, nArray, nArray2, n, n2, this.alpha, this.edgeAction);
        if (this.premultiplyAlpha) {
            ImageMath.unpremultiply(nArray2, 0, nArray2.length);
        }
        this.setRGB(bufferedImage2, 0, 0, n, n2, nArray2);
        return bufferedImage2;
    }

    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage bufferedImage, ColorModel colorModel) {
        if (colorModel == null) {
            colorModel = bufferedImage.getColorModel();
        }
        return new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(bufferedImage.getWidth(), bufferedImage.getHeight()), colorModel.isAlphaPremultiplied(), null);
    }

    @Override
    public Rectangle2D getBounds2D(BufferedImage bufferedImage) {
        return new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    @Override
    public Point2D getPoint2D(Point2D point2D, Point2D point2D2) {
        if (point2D2 == null) {
            point2D2 = new Point2D.Double();
        }
        point2D2.setLocation(point2D.getX(), point2D.getY());
        return point2D2;
    }

    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }

    public static void convolve(Kernel kernel, int[] nArray, int[] nArray2, int n, int n2, int n3) {
        ConvolveFilter.convolve(kernel, nArray, nArray2, n, n2, true, n3);
    }

    public static void convolve(Kernel kernel, int[] nArray, int[] nArray2, int n, int n2, boolean bl, int n3) {
        if (kernel.getHeight() == 1) {
            ConvolveFilter.convolveH(kernel, nArray, nArray2, n, n2, bl, n3);
        } else if (kernel.getWidth() == 1) {
            ConvolveFilter.convolveV(kernel, nArray, nArray2, n, n2, bl, n3);
        } else {
            ConvolveFilter.convolveHV(kernel, nArray, nArray2, n, n2, bl, n3);
        }
    }

    public static void convolveHV(Kernel kernel, int[] nArray, int[] nArray2, int n, int n2, boolean bl, int n3) {
        int n4 = 0;
        float[] fArray = kernel.getKernelData(null);
        int n5 = kernel.getHeight();
        int n6 = kernel.getWidth();
        int n7 = n5 / 2;
        int n8 = n6 / 2;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n9;
                int n10;
                int n11;
                int n12;
                float f = 0.0f;
                float f2 = 0.0f;
                float f3 = 0.0f;
                float f4 = 0.0f;
                for (n12 = -n7; n12 <= n7; ++n12) {
                    n11 = i + n12;
                    if (0 <= n11 && n11 < n2) {
                        n10 = n11 * n;
                    } else if (n3 == CLAMP_EDGES) {
                        n10 = i * n;
                    } else {
                        if (n3 != WRAP_EDGES) continue;
                        n10 = (n11 + n2) % n2 * n;
                    }
                    n9 = n6 * (n12 + n7) + n8;
                    for (int k = -n8; k <= n8; ++k) {
                        float f5 = fArray[n9 + k];
                        if (f5 == 0.0f) continue;
                        int n13 = j + k;
                        if (0 > n13 || n13 >= n) {
                            if (n3 == CLAMP_EDGES) {
                                n13 = j;
                            } else {
                                if (n3 != WRAP_EDGES) continue;
                                n13 = (j + n) % n;
                            }
                        }
                        int n14 = nArray[n10 + n13];
                        f4 += f5 * (float)(n14 >> 24 & 0xFF);
                        f += f5 * (float)(n14 >> 16 & 0xFF);
                        f2 += f5 * (float)(n14 >> 8 & 0xFF);
                        f3 += f5 * (float)(n14 & 0xFF);
                    }
                }
                n12 = bl ? PixelUtils.clamp((int)((double)f4 + 0.5)) : 255;
                n11 = PixelUtils.clamp((int)((double)f + 0.5));
                n10 = PixelUtils.clamp((int)((double)f2 + 0.5));
                n9 = PixelUtils.clamp((int)((double)f3 + 0.5));
                nArray2[n4++] = n12 << 24 | n11 << 16 | n10 << 8 | n9;
            }
        }
    }

    public static void convolveH(Kernel kernel, int[] nArray, int[] nArray2, int n, int n2, boolean bl, int n3) {
        int n4 = 0;
        float[] fArray = kernel.getKernelData(null);
        int n5 = kernel.getWidth();
        int n6 = n5 / 2;
        for (int i = 0; i < n2; ++i) {
            int n7 = i * n;
            for (int j = 0; j < n; ++j) {
                int n8;
                int n9;
                int n10;
                float f = 0.0f;
                float f2 = 0.0f;
                float f3 = 0.0f;
                float f4 = 0.0f;
                int n11 = n6;
                for (n10 = -n6; n10 <= n6; ++n10) {
                    float f5 = fArray[n11 + n10];
                    if (f5 == 0.0f) continue;
                    n9 = j + n10;
                    if (n9 < 0) {
                        if (n3 == CLAMP_EDGES) {
                            n9 = 0;
                        } else if (n3 == WRAP_EDGES) {
                            n9 = (j + n) % n;
                        }
                    } else if (n9 >= n) {
                        if (n3 == CLAMP_EDGES) {
                            n9 = n - 1;
                        } else if (n3 == WRAP_EDGES) {
                            n9 = (j + n) % n;
                        }
                    }
                    n8 = nArray[n7 + n9];
                    f4 += f5 * (float)(n8 >> 24 & 0xFF);
                    f += f5 * (float)(n8 >> 16 & 0xFF);
                    f2 += f5 * (float)(n8 >> 8 & 0xFF);
                    f3 += f5 * (float)(n8 & 0xFF);
                }
                n10 = bl ? PixelUtils.clamp((int)((double)f4 + 0.5)) : 255;
                int n12 = PixelUtils.clamp((int)((double)f + 0.5));
                n9 = PixelUtils.clamp((int)((double)f2 + 0.5));
                n8 = PixelUtils.clamp((int)((double)f3 + 0.5));
                nArray2[n4++] = n10 << 24 | n12 << 16 | n9 << 8 | n8;
            }
        }
    }

    public static void convolveV(Kernel kernel, int[] nArray, int[] nArray2, int n, int n2, boolean bl, int n3) {
        int n4 = 0;
        float[] fArray = kernel.getKernelData(null);
        int n5 = kernel.getHeight();
        int n6 = n5 / 2;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n7;
                int n8;
                int n9;
                float f = 0.0f;
                float f2 = 0.0f;
                float f3 = 0.0f;
                float f4 = 0.0f;
                for (n9 = -n6; n9 <= n6; ++n9) {
                    n8 = i + n9;
                    n7 = n8 < 0 ? (n3 == CLAMP_EDGES ? 0 : (n3 == WRAP_EDGES ? (i + n2) % n2 * n : n8 * n)) : (n8 >= n2 ? (n3 == CLAMP_EDGES ? (n2 - 1) * n : (n3 == WRAP_EDGES ? (i + n2) % n2 * n : n8 * n)) : n8 * n);
                    float f5 = fArray[n9 + n6];
                    if (f5 == 0.0f) continue;
                    int n10 = nArray[n7 + j];
                    f4 += f5 * (float)(n10 >> 24 & 0xFF);
                    f += f5 * (float)(n10 >> 16 & 0xFF);
                    f2 += f5 * (float)(n10 >> 8 & 0xFF);
                    f3 += f5 * (float)(n10 & 0xFF);
                }
                n9 = bl ? PixelUtils.clamp((int)((double)f4 + 0.5)) : 255;
                n8 = PixelUtils.clamp((int)((double)f + 0.5));
                n7 = PixelUtils.clamp((int)((double)f2 + 0.5));
                int n11 = PixelUtils.clamp((int)((double)f3 + 0.5));
                nArray2[n4++] = n9 << 24 | n8 << 16 | n7 << 8 | n11;
            }
        }
    }

    public String toString() {
        return "Blur/Convolve...";
    }
}

