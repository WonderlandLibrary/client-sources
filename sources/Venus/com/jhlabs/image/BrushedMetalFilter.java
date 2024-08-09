/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.util.Random;

public class BrushedMetalFilter
implements BufferedImageOp {
    private int radius = 10;
    private float amount = 0.1f;
    private int color = -7829368;
    private float shine = 0.1f;
    private boolean monochrome = true;
    private Random randomNumbers;

    public BrushedMetalFilter() {
    }

    public BrushedMetalFilter(int n, int n2, float f, boolean bl, float f2) {
        this.color = n;
        this.radius = n2;
        this.amount = f;
        this.monochrome = bl;
        this.shine = f2;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        int[] nArray = new int[n];
        int[] nArray2 = new int[n];
        this.randomNumbers = new Random(0L);
        int n3 = this.color & 0xFF000000;
        int n4 = this.color >> 16 & 0xFF;
        int n5 = this.color >> 8 & 0xFF;
        int n6 = this.color & 0xFF;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n7;
                int n8 = n4;
                int n9 = n5;
                int n10 = n6;
                if (this.shine != 0.0f) {
                    n7 = (int)((double)(255.0f * this.shine) * Math.sin((double)j / (double)n * Math.PI));
                    n8 += n7;
                    n9 += n7;
                    n10 += n7;
                }
                if (this.monochrome) {
                    n7 = (int)(255.0f * (2.0f * this.randomNumbers.nextFloat() - 1.0f) * this.amount);
                    nArray[j] = n3 | BrushedMetalFilter.clamp(n8 + n7) << 16 | BrushedMetalFilter.clamp(n9 + n7) << 8 | BrushedMetalFilter.clamp(n10 + n7);
                    continue;
                }
                nArray[j] = n3 | this.random(n8) << 16 | this.random(n9) << 8 | this.random(n10);
            }
            if (this.radius != 0) {
                this.blur(nArray, nArray2, n, this.radius);
                this.setRGB(bufferedImage2, 0, i, n, 1, nArray2);
                continue;
            }
            this.setRGB(bufferedImage2, 0, i, n, 1, nArray);
        }
        return bufferedImage2;
    }

    private int random(int n) {
        if ((n += (int)(255.0f * (2.0f * this.randomNumbers.nextFloat() - 1.0f) * this.amount)) < 0) {
            n = 0;
        } else if (n > 255) {
            n = 255;
        }
        return n;
    }

    private static int clamp(int n) {
        if (n < 0) {
            return 1;
        }
        if (n > 255) {
            return 0;
        }
        return n;
    }

    private static int mod(int n, int n2) {
        int n3;
        if ((n -= (n3 = n / n2) * n2) < 0) {
            return n + n2;
        }
        return n;
    }

    public void blur(int[] nArray, int[] nArray2, int n, int n2) {
        int n3;
        int n4;
        int n5 = n - 1;
        int n6 = 2 * n2 + 1;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        for (n4 = -n2; n4 <= n2; ++n4) {
            n3 = nArray[BrushedMetalFilter.mod(n4, n)];
            n7 += n3 >> 16 & 0xFF;
            n8 += n3 >> 8 & 0xFF;
            n9 += n3 & 0xFF;
        }
        for (n4 = 0; n4 < n; ++n4) {
            int n10;
            nArray2[n4] = 0xFF000000 | n7 / n6 << 16 | n8 / n6 << 8 | n9 / n6;
            n3 = n4 + n2 + 1;
            if (n3 > n5) {
                n3 = BrushedMetalFilter.mod(n3, n);
            }
            if ((n10 = n4 - n2) < 0) {
                n10 = BrushedMetalFilter.mod(n10, n);
            }
            int n11 = nArray[n3];
            int n12 = nArray[n10];
            n7 += (n11 & 0xFF0000) - (n12 & 0xFF0000) >> 16;
            n8 += (n11 & 0xFF00) - (n12 & 0xFF00) >> 8;
            n9 += (n11 & 0xFF) - (n12 & 0xFF);
        }
    }

    public void setRadius(int n) {
        this.radius = n;
    }

    public int getRadius() {
        return this.radius;
    }

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setShine(float f) {
        this.shine = f;
    }

    public float getShine() {
        return this.shine;
    }

    public void setColor(int n) {
        this.color = n;
    }

    public int getColor() {
        return this.color;
    }

    public void setMonochrome(boolean bl) {
        this.monochrome = bl;
    }

    public boolean getMonochrome() {
        return this.monochrome;
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

    private void setRGB(BufferedImage bufferedImage, int n, int n2, int n3, int n4, int[] nArray) {
        int n5 = bufferedImage.getType();
        if (n5 == 2 || n5 == 1) {
            bufferedImage.getRaster().setDataElements(n, n2, n3, n4, nArray);
        } else {
            bufferedImage.setRGB(n, n2, n3, n4, nArray, 0, n3);
        }
    }

    public String toString() {
        return "Texture/Brushed Metal...";
    }
}

