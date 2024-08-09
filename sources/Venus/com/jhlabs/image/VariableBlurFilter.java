/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

public class VariableBlurFilter
extends AbstractBufferedImageOp {
    private int hRadius = 1;
    private int vRadius = 1;
    private int iterations = 1;
    private BufferedImage blurMask;
    private boolean premultiplyAlpha = true;

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
            bufferedImage2 = new BufferedImage(n, n2, 2);
        }
        int[] nArray = new int[n * n2];
        int[] nArray2 = new int[n * n2];
        this.getRGB(bufferedImage, 0, 0, n, n2, nArray);
        if (this.premultiplyAlpha) {
            ImageMath.premultiply(nArray, 0, nArray.length);
        }
        for (int i = 0; i < this.iterations; ++i) {
            this.blur(nArray, nArray2, n, n2, this.hRadius, 1);
            this.blur(nArray2, nArray, n2, n, this.vRadius, 2);
        }
        if (this.premultiplyAlpha) {
            ImageMath.unpremultiply(nArray, 0, nArray.length);
        }
        this.setRGB(bufferedImage2, 0, 0, n, n2, nArray);
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

    public void blur(int[] nArray, int[] nArray2, int n, int n2, int n3, int n4) {
        int n5 = n - 1;
        int[] nArray3 = new int[n];
        int[] nArray4 = new int[n];
        int[] nArray5 = new int[n];
        int[] nArray6 = new int[n];
        int[] nArray7 = new int[n];
        int n6 = 0;
        for (int i = 0; i < n2; ++i) {
            int n7;
            int n8;
            int n9 = i;
            if (this.blurMask != null) {
                if (n4 == 1) {
                    this.getRGB(this.blurMask, 0, i, n, 1, nArray7);
                } else {
                    this.getRGB(this.blurMask, i, 0, 1, n, nArray7);
                }
            }
            for (n8 = 0; n8 < n; ++n8) {
                n7 = nArray[n6 + n8];
                nArray6[n8] = n7 >> 24 & 0xFF;
                nArray3[n8] = n7 >> 16 & 0xFF;
                nArray4[n8] = n7 >> 8 & 0xFF;
                nArray5[n8] = n7 & 0xFF;
                if (n8 == 0) continue;
                int n10 = n8;
                nArray6[n10] = nArray6[n10] + nArray6[n8 - 1];
                int n11 = n8;
                nArray3[n11] = nArray3[n11] + nArray3[n8 - 1];
                int n12 = n8;
                nArray4[n12] = nArray4[n12] + nArray4[n8 - 1];
                int n13 = n8;
                nArray5[n13] = nArray5[n13] + nArray5[n8 - 1];
            }
            for (n8 = 0; n8 < n; ++n8) {
                int n14;
                n7 = this.blurMask != null ? (n4 == 1 ? (int)((float)((nArray7[n8] & 0xFF) * this.hRadius) / 255.0f) : (int)((float)((nArray7[n8] & 0xFF) * this.vRadius) / 255.0f)) : (n4 == 1 ? (int)(this.blurRadiusAt(n8, i, n, n2) * (float)this.hRadius) : (int)(this.blurRadiusAt(i, n8, n2, n) * (float)this.vRadius));
                int n15 = 2 * n7 + 1;
                int n16 = 0;
                int n17 = 0;
                int n18 = 0;
                int n19 = 0;
                int n20 = n8 + n7;
                if (n20 > n5) {
                    n14 = n20 - n5;
                    int n21 = n5;
                    n16 += (nArray6[n21] - nArray6[n21 - 1]) * n14;
                    n17 += (nArray3[n21] - nArray3[n21 - 1]) * n14;
                    n18 += (nArray4[n21] - nArray4[n21 - 1]) * n14;
                    n19 += (nArray5[n21] - nArray5[n21 - 1]) * n14;
                    n20 = n5;
                }
                if ((n14 = n8 - n7 - 1) < 0) {
                    n16 -= nArray6[0] * n14;
                    n17 -= nArray3[0] * n14;
                    n18 -= nArray4[0] * n14;
                    n19 -= nArray5[0] * n14;
                    n14 = 0;
                }
                nArray2[n9] = (n16 += nArray6[n20] - nArray6[n14]) / n15 << 24 | (n17 += nArray3[n20] - nArray3[n14]) / n15 << 16 | (n18 += nArray4[n20] - nArray4[n14]) / n15 << 8 | (n19 += nArray5[n20] - nArray5[n14]) / n15;
                n9 += n2;
            }
            n6 += n;
        }
    }

    protected float blurRadiusAt(int n, int n2, int n3, int n4) {
        return (float)n / (float)n3;
    }

    public void setHRadius(int n) {
        this.hRadius = n;
    }

    public int getHRadius() {
        return this.hRadius;
    }

    public void setVRadius(int n) {
        this.vRadius = n;
    }

    public int getVRadius() {
        return this.vRadius;
    }

    public void setRadius(int n) {
        this.hRadius = this.vRadius = n;
    }

    public int getRadius() {
        return this.hRadius;
    }

    public void setIterations(int n) {
        this.iterations = n;
    }

    public int getIterations() {
        return this.iterations;
    }

    public void setBlurMask(BufferedImage bufferedImage) {
        this.blurMask = bufferedImage;
    }

    public BufferedImage getBlurMask() {
        return this.blurMask;
    }

    public String toString() {
        return "Blur/Variable Blur...";
    }
}

