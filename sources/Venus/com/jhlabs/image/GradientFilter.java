/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.Colormap;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.LinearColormap;
import com.jhlabs.image.PixelUtils;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class GradientFilter
extends AbstractBufferedImageOp {
    public static final int LINEAR = 0;
    public static final int BILINEAR = 1;
    public static final int RADIAL = 2;
    public static final int CONICAL = 3;
    public static final int BICONICAL = 4;
    public static final int SQUARE = 5;
    public static final int INT_LINEAR = 0;
    public static final int INT_CIRCLE_UP = 1;
    public static final int INT_CIRCLE_DOWN = 2;
    public static final int INT_SMOOTH = 3;
    private float angle = 0.0f;
    private int color1 = -16777216;
    private int color2 = -1;
    private Point p1 = new Point(0, 0);
    private Point p2 = new Point(64, 64);
    private boolean repeat = false;
    private float x1;
    private float y1;
    private float dx;
    private float dy;
    private Colormap colormap = null;
    private int type;
    private int interpolation = 0;
    private int paintMode = 1;

    public GradientFilter() {
    }

    public GradientFilter(Point point, Point point2, int n, int n2, boolean bl, int n3, int n4) {
        this.p1 = point;
        this.p2 = point2;
        this.color1 = n;
        this.color2 = n2;
        this.repeat = bl;
        this.type = n3;
        this.interpolation = n4;
        this.colormap = new LinearColormap(n, n2);
    }

    public void setPoint1(Point point) {
        this.p1 = point;
    }

    public Point getPoint1() {
        return this.p1;
    }

    public void setPoint2(Point point) {
        this.p2 = point;
    }

    public Point getPoint2() {
        return this.p2;
    }

    public void setType(int n) {
        this.type = n;
    }

    public int getType() {
        return this.type;
    }

    public void setInterpolation(int n) {
        this.interpolation = n;
    }

    public int getInterpolation() {
        return this.interpolation;
    }

    public void setAngle(float f) {
        this.angle = f;
        this.p2 = new Point((int)(64.0 * Math.cos(f)), (int)(64.0 * Math.sin(f)));
    }

    public float getAngle() {
        return this.angle;
    }

    public void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    public Colormap getColormap() {
        return this.colormap;
    }

    public void setPaintMode(int n) {
        this.paintMode = n;
    }

    public int getPaintMode() {
        return this.paintMode;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        float f;
        float f2;
        float f3;
        float f4;
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        if ((f4 = (float)this.p1.x) > (f3 = (float)this.p2.x) && this.type != 2) {
            f2 = f4;
            f4 = f3;
            f3 = f2;
            f2 = this.p2.y;
            f = this.p1.y;
            var5_9 = this.color2;
            var6_10 = this.color1;
        } else {
            f2 = this.p1.y;
            f = this.p2.y;
            var5_9 = this.color1;
            var6_10 = this.color2;
        }
        float f5 = f3 - f4;
        float f6 = f - f2;
        float f7 = f5 * f5 + f6 * f6;
        this.x1 = f4;
        this.y1 = f2;
        if (f7 >= Float.MIN_VALUE) {
            f5 /= f7;
            f6 /= f7;
            if (this.repeat) {
                f5 %= 1.0f;
                f6 %= 1.0f;
            }
        }
        this.dx = f5;
        this.dy = f6;
        int[] nArray = new int[n];
        for (int i = 0; i < n2; ++i) {
            this.getRGB(bufferedImage, 0, i, n, 1, nArray);
            switch (this.type) {
                case 0: 
                case 1: {
                    this.linearGradient(nArray, i, n, 1);
                    break;
                }
                case 2: {
                    this.radialGradient(nArray, i, n, 1);
                    break;
                }
                case 3: 
                case 4: {
                    this.conicalGradient(nArray, i, n, 1);
                    break;
                }
                case 5: {
                    this.squareGradient(nArray, i, n, 1);
                }
            }
            this.setRGB(bufferedImage2, 0, i, n, 1, nArray);
        }
        return bufferedImage2;
    }

    private void repeatGradient(int[] nArray, int n, int n2, float f, float f2, float f3) {
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            float f4 = f;
            int n4 = n;
            while (--n4 >= 0) {
                int n5 = this.type == 1 ? this.colormap.getColor(this.map(ImageMath.triangle(f4))) : this.colormap.getColor(this.map(ImageMath.mod(f4, 1.0f)));
                nArray[n3] = PixelUtils.combinePixels(n5, nArray[n3], this.paintMode);
                ++n3;
                f4 += f2;
            }
            f += f3;
        }
    }

    private void singleGradient(int[] nArray, int n, int n2, float f, float f2, float f3) {
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            int n4;
            float f4 = f;
            int n5 = n;
            if ((double)f4 <= 0.0) {
                n4 = this.colormap.getColor(0.0f);
                do {
                    nArray[n3] = PixelUtils.combinePixels(n4, nArray[n3], this.paintMode);
                    ++n3;
                } while (--n5 > 0 && (double)(f4 += f2) <= 0.0);
            }
            while ((double)f4 < 1.0 && --n5 >= 0) {
                n4 = this.type == 1 ? this.colormap.getColor(this.map(ImageMath.triangle(f4))) : this.colormap.getColor(this.map(f4));
                nArray[n3] = PixelUtils.combinePixels(n4, nArray[n3], this.paintMode);
                ++n3;
                f4 += f2;
            }
            if (n5 > 0) {
                n4 = this.type == 1 ? this.colormap.getColor(0.0f) : this.colormap.getColor(1.0f);
                do {
                    nArray[n3] = PixelUtils.combinePixels(n4, nArray[n3], this.paintMode);
                    ++n3;
                } while (--n5 > 0);
            }
            f += f3;
        }
    }

    private void linearGradient(int[] nArray, int n, int n2, int n3) {
        boolean bl = false;
        float f = ((float)bl - this.x1) * this.dx + ((float)n - this.y1) * this.dy;
        if (this.repeat) {
            this.repeatGradient(nArray, n2, n3, f, this.dx, this.dy);
        } else {
            this.singleGradient(nArray, n2, n3, f, this.dx, this.dy);
        }
    }

    private void radialGradient(int[] nArray, int n, int n2, int n3) {
        int n4 = 0;
        float f = this.distance(this.p2.x - this.p1.x, this.p2.y - this.p1.y);
        for (int i = 0; i < n2; ++i) {
            float f2 = this.distance(i - this.p1.x, n - this.p1.y);
            float f3 = f2 / f;
            if (this.repeat) {
                f3 %= 2.0f;
            } else if ((double)f3 > 1.0) {
                f3 = 1.0f;
            }
            int n5 = this.colormap.getColor(this.map(f3));
            nArray[n4] = PixelUtils.combinePixels(n5, nArray[n4], this.paintMode);
            ++n4;
        }
    }

    private void squareGradient(int[] nArray, int n, int n2, int n3) {
        int n4 = 0;
        float f = Math.max(Math.abs(this.p2.x - this.p1.x), Math.abs(this.p2.y - this.p1.y));
        for (int i = 0; i < n2; ++i) {
            float f2 = Math.max(Math.abs(i - this.p1.x), Math.abs(n - this.p1.y));
            float f3 = f2 / f;
            if (this.repeat) {
                f3 %= 2.0f;
            } else if ((double)f3 > 1.0) {
                f3 = 1.0f;
            }
            int n5 = this.colormap.getColor(this.map(f3));
            nArray[n4] = PixelUtils.combinePixels(n5, nArray[n4], this.paintMode);
            ++n4;
        }
    }

    private void conicalGradient(int[] nArray, int n, int n2, int n3) {
        int n4 = 0;
        float f = (float)Math.atan2(this.p2.x - this.p1.x, this.p2.y - this.p1.y);
        for (int i = 0; i < n2; ++i) {
            float f2 = (float)(Math.atan2(i - this.p1.x, n - this.p1.y) - (double)f) / ((float)Math.PI * 2);
            f2 += 1.0f;
            f2 %= 1.0f;
            if (this.type == 4) {
                f2 = ImageMath.triangle(f2);
            }
            int n5 = this.colormap.getColor(this.map(f2));
            nArray[n4] = PixelUtils.combinePixels(n5, nArray[n4], this.paintMode);
            ++n4;
        }
    }

    private float map(float f) {
        if (this.repeat) {
            f = (double)f > 1.0 ? 2.0f - f : f;
        }
        switch (this.interpolation) {
            case 1: {
                f = ImageMath.circleUp(ImageMath.clamp(f, 0.0f, 1.0f));
                break;
            }
            case 2: {
                f = ImageMath.circleDown(ImageMath.clamp(f, 0.0f, 1.0f));
                break;
            }
            case 3: {
                f = ImageMath.smoothStep(0.0f, 1.0f, f);
            }
        }
        return f;
    }

    private float distance(float f, float f2) {
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    public String toString() {
        return "Other/Gradient Fill...";
    }
}

