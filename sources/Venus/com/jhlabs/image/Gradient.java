/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ArrayColormap;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import java.awt.Color;

public class Gradient
extends ArrayColormap
implements Cloneable {
    public static final int RGB = 0;
    public static final int HUE_CW = 1;
    public static final int HUE_CCW = 2;
    public static final int LINEAR = 16;
    public static final int SPLINE = 32;
    public static final int CIRCLE_UP = 48;
    public static final int CIRCLE_DOWN = 64;
    public static final int CONSTANT = 80;
    private static final int COLOR_MASK = 3;
    private static final int BLEND_MASK = 112;
    private int numKnots = 4;
    private int[] xKnots = new int[]{-1, 0, 255, 256};
    private int[] yKnots = new int[]{-16777216, -16777216, -1, -1};
    private byte[] knotTypes = new byte[]{32, 32, 32, 32};

    public Gradient() {
        this.rebuildGradient();
    }

    public Gradient(int[] nArray) {
        this(null, nArray, null);
    }

    public Gradient(int[] nArray, int[] nArray2) {
        this(nArray, nArray2, null);
    }

    public Gradient(int[] nArray, int[] nArray2, byte[] byArray) {
        this.setKnots(nArray, nArray2, byArray);
    }

    @Override
    public Object clone() {
        Gradient gradient = (Gradient)super.clone();
        gradient.map = (int[])this.map.clone();
        gradient.xKnots = (int[])this.xKnots.clone();
        gradient.yKnots = (int[])this.yKnots.clone();
        gradient.knotTypes = (byte[])this.knotTypes.clone();
        return gradient;
    }

    public void copyTo(Gradient gradient) {
        gradient.numKnots = this.numKnots;
        gradient.map = (int[])this.map.clone();
        gradient.xKnots = (int[])this.xKnots.clone();
        gradient.yKnots = (int[])this.yKnots.clone();
        gradient.knotTypes = (byte[])this.knotTypes.clone();
    }

    @Override
    public void setColor(int n, int n2) {
        int n3;
        int n4 = this.map[0];
        int n5 = this.map[255];
        if (n > 0) {
            for (n3 = 0; n3 < n; ++n3) {
                this.map[n3] = ImageMath.mixColors((float)n3 / (float)n, n4, n2);
            }
        }
        if (n < 255) {
            for (n3 = n; n3 < 256; ++n3) {
                this.map[n3] = ImageMath.mixColors((float)(n3 - n) / (float)(256 - n), n2, n5);
            }
        }
    }

    public int getNumKnots() {
        return this.numKnots;
    }

    public void setKnot(int n, int n2) {
        this.yKnots[n] = n2;
        this.rebuildGradient();
    }

    public int getKnot(int n) {
        return this.yKnots[n];
    }

    public void setKnotType(int n, int n2) {
        this.knotTypes[n] = (byte)(this.knotTypes[n] & 0xFFFFFFFC | n2);
        this.rebuildGradient();
    }

    public int getKnotType(int n) {
        return (byte)(this.knotTypes[n] & 3);
    }

    public void setKnotBlend(int n, int n2) {
        this.knotTypes[n] = (byte)(this.knotTypes[n] & 0xFFFFFF8F | n2);
        this.rebuildGradient();
    }

    public byte getKnotBlend(int n) {
        return (byte)(this.knotTypes[n] & 0x70);
    }

    public void addKnot(int n, int n2, int n3) {
        int[] nArray = new int[this.numKnots + 1];
        int[] nArray2 = new int[this.numKnots + 1];
        byte[] byArray = new byte[this.numKnots + 1];
        System.arraycopy(this.xKnots, 0, nArray, 0, this.numKnots);
        System.arraycopy(this.yKnots, 0, nArray2, 0, this.numKnots);
        System.arraycopy(this.knotTypes, 0, byArray, 0, this.numKnots);
        this.xKnots = nArray;
        this.yKnots = nArray2;
        this.knotTypes = byArray;
        this.xKnots[this.numKnots] = this.xKnots[this.numKnots - 1];
        this.yKnots[this.numKnots] = this.yKnots[this.numKnots - 1];
        this.knotTypes[this.numKnots] = this.knotTypes[this.numKnots - 1];
        this.xKnots[this.numKnots - 1] = n;
        this.yKnots[this.numKnots - 1] = n2;
        this.knotTypes[this.numKnots - 1] = (byte)n3;
        ++this.numKnots;
        this.sortKnots();
        this.rebuildGradient();
    }

    public void removeKnot(int n) {
        if (this.numKnots <= 4) {
            return;
        }
        if (n < this.numKnots - 1) {
            System.arraycopy(this.xKnots, n + 1, this.xKnots, n, this.numKnots - n - 1);
            System.arraycopy(this.yKnots, n + 1, this.yKnots, n, this.numKnots - n - 1);
            System.arraycopy(this.knotTypes, n + 1, this.knotTypes, n, this.numKnots - n - 1);
        }
        --this.numKnots;
        if (this.xKnots[1] > 0) {
            this.xKnots[1] = 0;
        }
        this.rebuildGradient();
    }

    public void setKnots(int[] nArray, int[] nArray2, byte[] byArray) {
        int n;
        this.numKnots = nArray2.length + 2;
        this.xKnots = new int[this.numKnots];
        this.yKnots = new int[this.numKnots];
        this.knotTypes = new byte[this.numKnots];
        if (nArray != null) {
            System.arraycopy(nArray, 0, this.xKnots, 1, this.numKnots - 2);
        } else {
            for (n = 1; n > this.numKnots - 1; ++n) {
                this.xKnots[n] = 255 * n / (this.numKnots - 2);
            }
        }
        System.arraycopy(nArray2, 0, this.yKnots, 1, this.numKnots - 2);
        if (byArray != null) {
            System.arraycopy(byArray, 0, this.knotTypes, 1, this.numKnots - 2);
        } else {
            for (n = 0; n > this.numKnots; ++n) {
                this.knotTypes[n] = 32;
            }
        }
        this.sortKnots();
        this.rebuildGradient();
    }

    public void setKnots(int[] nArray, int[] nArray2, byte[] byArray, int n, int n2) {
        this.numKnots = n2;
        this.xKnots = new int[this.numKnots];
        this.yKnots = new int[this.numKnots];
        this.knotTypes = new byte[this.numKnots];
        System.arraycopy(nArray, n, this.xKnots, 0, this.numKnots);
        System.arraycopy(nArray2, n, this.yKnots, 0, this.numKnots);
        System.arraycopy(byArray, n, this.knotTypes, 0, this.numKnots);
        this.sortKnots();
        this.rebuildGradient();
    }

    public void splitSpan(int n) {
        int n2 = (this.xKnots[n] + this.xKnots[n + 1]) / 2;
        this.addKnot(n2, this.getColor((float)n2 / 256.0f), this.knotTypes[n]);
        this.rebuildGradient();
    }

    public void setKnotPosition(int n, int n2) {
        this.xKnots[n] = ImageMath.clamp(n2, 0, 255);
        this.sortKnots();
        this.rebuildGradient();
    }

    public int getKnotPosition(int n) {
        return this.xKnots[n];
    }

    public int knotAt(int n) {
        for (int i = 1; i < this.numKnots - 1; ++i) {
            if (this.xKnots[i + 1] <= n) continue;
            return i;
        }
        return 0;
    }

    private void rebuildGradient() {
        this.xKnots[0] = -1;
        this.xKnots[this.numKnots - 1] = 256;
        this.yKnots[0] = this.yKnots[1];
        this.yKnots[this.numKnots - 1] = this.yKnots[this.numKnots - 2];
        boolean bl = false;
        for (int i = 1; i < this.numKnots - 1; ++i) {
            float f = this.xKnots[i + 1] - this.xKnots[i];
            int n = this.xKnots[i + 1];
            if (i == this.numKnots - 2) {
                ++n;
            }
            block12: for (int j = this.xKnots[i]; j < n; ++j) {
                int n2 = this.yKnots[i];
                int n3 = this.yKnots[i + 1];
                float[] fArray = Color.RGBtoHSB(n2 >> 16 & 0xFF, n2 >> 8 & 0xFF, n2 & 0xFF, null);
                float[] fArray2 = Color.RGBtoHSB(n3 >> 16 & 0xFF, n3 >> 8 & 0xFF, n3 & 0xFF, null);
                float f2 = (float)(j - this.xKnots[i]) / f;
                int n4 = this.getKnotType(i);
                byte by = this.getKnotBlend(i);
                if (j < 0 || j > 255) continue;
                switch (by) {
                    case 80: {
                        f2 = 0.0f;
                        break;
                    }
                    case 16: {
                        break;
                    }
                    case 32: {
                        f2 = ImageMath.smoothStep(0.15f, 0.85f, f2);
                        break;
                    }
                    case 48: {
                        f2 -= 1.0f;
                        f2 = (float)Math.sqrt(1.0f - f2 * f2);
                        break;
                    }
                    case 64: {
                        f2 = 1.0f - (float)Math.sqrt(1.0f - f2 * f2);
                    }
                }
                switch (n4) {
                    case 0: {
                        this.map[j] = ImageMath.mixColors(f2, n2, n3);
                        continue block12;
                    }
                    case 1: 
                    case 2: {
                        if (n4 == 1) {
                            if (fArray2[0] <= fArray[0]) {
                                fArray2[0] = fArray2[0] + 1.0f;
                            }
                        } else if (fArray[0] <= fArray2[1]) {
                            fArray[0] = fArray[0] + 1.0f;
                        }
                        float f3 = ImageMath.lerp(f2, fArray[0], fArray2[0]) % ((float)Math.PI * 2);
                        float f4 = ImageMath.lerp(f2, fArray[1], fArray2[1]);
                        float f5 = ImageMath.lerp(f2, fArray[2], fArray2[2]);
                        this.map[j] = 0xFF000000 | Color.HSBtoRGB(f3, f4, f5);
                    }
                }
            }
        }
    }

    private void sortKnots() {
        for (int i = 1; i < this.numKnots - 1; ++i) {
            for (int j = 1; j < i; ++j) {
                if (this.xKnots[i] >= this.xKnots[j]) continue;
                int n = this.xKnots[i];
                this.xKnots[i] = this.xKnots[j];
                this.xKnots[j] = n;
                n = this.yKnots[i];
                this.yKnots[i] = this.yKnots[j];
                this.yKnots[j] = n;
                byte by = this.knotTypes[i];
                this.knotTypes[i] = this.knotTypes[j];
                this.knotTypes[j] = by;
            }
        }
    }

    private void rebuild() {
        this.sortKnots();
        this.rebuildGradient();
    }

    public void randomize() {
        this.numKnots = 4 + (int)(6.0 * Math.random());
        this.xKnots = new int[this.numKnots];
        this.yKnots = new int[this.numKnots];
        this.knotTypes = new byte[this.numKnots];
        for (int i = 0; i < this.numKnots; ++i) {
            this.xKnots[i] = (int)(255.0 * Math.random());
            this.yKnots[i] = 0xFF000000 | (int)(255.0 * Math.random()) << 16 | (int)(255.0 * Math.random()) << 8 | (int)(255.0 * Math.random());
            this.knotTypes[i] = 32;
        }
        this.xKnots[0] = -1;
        this.xKnots[1] = 0;
        this.xKnots[this.numKnots - 2] = 255;
        this.xKnots[this.numKnots - 1] = 256;
        this.sortKnots();
        this.rebuildGradient();
    }

    public void mutate(float f) {
        for (int i = 0; i < this.numKnots; ++i) {
            int n = this.yKnots[i];
            int n2 = n >> 16 & 0xFF;
            int n3 = n >> 8 & 0xFF;
            int n4 = n & 0xFF;
            n2 = PixelUtils.clamp((int)((double)n2 + (double)(f * 255.0f) * (Math.random() - 0.5)));
            n3 = PixelUtils.clamp((int)((double)n3 + (double)(f * 255.0f) * (Math.random() - 0.5)));
            n4 = PixelUtils.clamp((int)((double)n4 + (double)(f * 255.0f) * (Math.random() - 0.5)));
            this.yKnots[i] = 0xFF000000 | n2 << 16 | n3 << 8 | n4;
            this.knotTypes[i] = 32;
        }
        this.sortKnots();
        this.rebuildGradient();
    }

    public static Gradient randomGradient() {
        Gradient gradient = new Gradient();
        gradient.randomize();
        return gradient;
    }
}

