/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.LinearColormap;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;
import java.util.Date;
import java.util.Random;

public class PlasmaFilter
extends WholeImageFilter {
    public float turbulence = 1.0f;
    private float scaling = 0.0f;
    private Colormap colormap = new LinearColormap();
    private Random randomGenerator = new Random();
    private long seed = 567L;
    private boolean useColormap = false;
    private boolean useImageColors = false;

    public void setTurbulence(float f) {
        this.turbulence = f;
    }

    public float getTurbulence() {
        return this.turbulence;
    }

    public void setScaling(float f) {
        this.scaling = f;
    }

    public float getScaling() {
        return this.scaling;
    }

    public void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    public Colormap getColormap() {
        return this.colormap;
    }

    public void setUseColormap(boolean bl) {
        this.useColormap = bl;
    }

    public boolean getUseColormap() {
        return this.useColormap;
    }

    public void setUseImageColors(boolean bl) {
        this.useImageColors = bl;
    }

    public boolean getUseImageColors() {
        return this.useImageColors;
    }

    public void setSeed(int n) {
        this.seed = n;
    }

    public int getSeed() {
        return (int)this.seed;
    }

    public void randomize() {
        this.seed = new Date().getTime();
    }

    private int randomRGB(int[] nArray, int n, int n2) {
        if (this.useImageColors) {
            return nArray[n2 * this.originalSpace.width + n];
        }
        int n3 = (int)(255.0f * this.randomGenerator.nextFloat());
        int n4 = (int)(255.0f * this.randomGenerator.nextFloat());
        int n5 = (int)(255.0f * this.randomGenerator.nextFloat());
        return 0xFF000000 | n3 << 16 | n4 << 8 | n5;
    }

    private int displace(int n, float f) {
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        n2 = PixelUtils.clamp(n2 + (int)((double)f * ((double)this.randomGenerator.nextFloat() - 0.5)));
        n3 = PixelUtils.clamp(n3 + (int)((double)f * ((double)this.randomGenerator.nextFloat() - 0.5)));
        n4 = PixelUtils.clamp(n4 + (int)((double)f * ((double)this.randomGenerator.nextFloat() - 0.5)));
        return 0xFF000000 | n2 << 16 | n3 << 8 | n4;
    }

    private int average(int n, int n2) {
        return PixelUtils.combinePixels(n, n2, 13);
    }

    private int getPixel(int n, int n2, int[] nArray, int n3) {
        return nArray[n2 * n3 + n];
    }

    private void putPixel(int n, int n2, int n3, int[] nArray, int n4) {
        nArray[n2 * n4 + n] = n3;
    }

    private boolean doPixel(int n, int n2, int n3, int n4, int[] nArray, int n5, int n6, int n7) {
        if (n6 == 0) {
            int n8 = this.getPixel(n, n2, nArray, n5);
            int n9 = this.getPixel(n, n4, nArray, n5);
            int n10 = this.getPixel(n3, n2, nArray, n5);
            int n11 = this.getPixel(n3, n4, nArray, n5);
            float f = 256.0f / (2.0f * (float)n7) * this.turbulence;
            int n12 = (n + n3) / 2;
            int n13 = (n2 + n4) / 2;
            if (n12 == n && n12 == n3 && n13 == n2 && n13 == n4) {
                return false;
            }
            if (n12 != n || n12 != n3) {
                int n14 = this.average(n8, n9);
                n14 = this.displace(n14, f);
                this.putPixel(n, n13, n14, nArray, n5);
                if (n != n3) {
                    int n15 = this.average(n10, n11);
                    n15 = this.displace(n15, f);
                    this.putPixel(n3, n13, n15, nArray, n5);
                }
            }
            if (n13 != n2 || n13 != n4) {
                if (n != n12 || n13 != n4) {
                    int n16 = this.average(n9, n11);
                    n16 = this.displace(n16, f);
                    this.putPixel(n12, n4, n16, nArray, n5);
                }
                if (n2 != n4) {
                    int n17 = this.average(n8, n10);
                    n17 = this.displace(n17, f);
                    this.putPixel(n12, n2, n17, nArray, n5);
                }
            }
            if (n2 != n4 || n != n3) {
                int n18 = this.average(n8, n11);
                int n19 = this.average(n9, n10);
                n18 = this.average(n18, n19);
                n18 = this.displace(n18, f);
                this.putPixel(n12, n13, n18, nArray, n5);
            }
            return n3 - n < 3 && n4 - n2 < 3;
        }
        int n20 = (n + n3) / 2;
        int n21 = (n2 + n4) / 2;
        this.doPixel(n, n2, n20, n21, nArray, n5, n6 - 1, n7 + 1);
        this.doPixel(n, n21, n20, n4, nArray, n5, n6 - 1, n7 + 1);
        this.doPixel(n20, n2, n3, n21, nArray, n5, n6 - 1, n7 + 1);
        return this.doPixel(n20, n21, n3, n4, nArray, n5, n6 - 1, n7 + 1);
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int[] nArray2 = new int[n * n2];
        this.randomGenerator.setSeed(this.seed);
        int n3 = n - 1;
        int n4 = n2 - 1;
        this.putPixel(0, 0, this.randomRGB(nArray, 0, 0), nArray2, n);
        this.putPixel(n3, 0, this.randomRGB(nArray, n3, 0), nArray2, n);
        this.putPixel(0, n4, this.randomRGB(nArray, 0, n4), nArray2, n);
        this.putPixel(n3, n4, this.randomRGB(nArray, n3, n4), nArray2, n);
        this.putPixel(n3 / 2, n4 / 2, this.randomRGB(nArray, n3 / 2, n4 / 2), nArray2, n);
        this.putPixel(0, n4 / 2, this.randomRGB(nArray, 0, n4 / 2), nArray2, n);
        this.putPixel(n3, n4 / 2, this.randomRGB(nArray, n3, n4 / 2), nArray2, n);
        this.putPixel(n3 / 2, 0, this.randomRGB(nArray, n3 / 2, 0), nArray2, n);
        this.putPixel(n3 / 2, n4, this.randomRGB(nArray, n3 / 2, n4), nArray2, n);
        int n5 = 1;
        while (this.doPixel(0, 0, n - 1, n2 - 1, nArray2, n, n5, 1)) {
            ++n5;
        }
        if (this.useColormap && this.colormap != null) {
            int n6 = 0;
            for (int i = 0; i < n2; ++i) {
                for (int j = 0; j < n; ++j) {
                    nArray2[n6] = this.colormap.getColor((float)(nArray2[n6] & 0xFF) / 255.0f);
                    ++n6;
                }
            }
        }
        return nArray2;
    }

    public String toString() {
        return "Texture/Plasma...";
    }
}

