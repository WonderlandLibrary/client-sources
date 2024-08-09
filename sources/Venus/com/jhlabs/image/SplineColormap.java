/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ArrayColormap;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;

public class SplineColormap
extends ArrayColormap {
    private int numKnots = 4;
    private int[] xKnots = new int[]{0, 0, 255, 255};
    private int[] yKnots = new int[]{-16777216, -16777216, -1, -1};

    public SplineColormap() {
        this.rebuildGradient();
    }

    public SplineColormap(int[] nArray, int[] nArray2) {
        this.xKnots = nArray;
        this.yKnots = nArray2;
        this.numKnots = nArray.length;
        this.rebuildGradient();
    }

    public void setKnot(int n, int n2) {
        this.yKnots[n] = n2;
        this.rebuildGradient();
    }

    public int getKnot(int n) {
        return this.yKnots[n];
    }

    public void addKnot(int n, int n2) {
        int[] nArray = new int[this.numKnots + 1];
        int[] nArray2 = new int[this.numKnots + 1];
        System.arraycopy(this.xKnots, 0, nArray, 0, this.numKnots);
        System.arraycopy(this.yKnots, 0, nArray2, 0, this.numKnots);
        this.xKnots = nArray;
        this.yKnots = nArray2;
        this.xKnots[this.numKnots] = n;
        this.yKnots[this.numKnots] = n2;
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
        }
        --this.numKnots;
        this.rebuildGradient();
    }

    public void setKnotPosition(int n, int n2) {
        this.xKnots[n] = PixelUtils.clamp(n2);
        this.sortKnots();
        this.rebuildGradient();
    }

    private void rebuildGradient() {
        this.xKnots[0] = -1;
        this.xKnots[this.numKnots - 1] = 256;
        this.yKnots[0] = this.yKnots[1];
        this.yKnots[this.numKnots - 1] = this.yKnots[this.numKnots - 2];
        for (int i = 0; i < 256; ++i) {
            this.map[i] = ImageMath.colorSpline(i, this.numKnots, this.xKnots, this.yKnots);
        }
    }

    private void sortKnots() {
        for (int i = 1; i < this.numKnots; ++i) {
            for (int j = 1; j < i; ++j) {
                if (this.xKnots[i] >= this.xKnots[j]) continue;
                int n = this.xKnots[i];
                this.xKnots[i] = this.xKnots[j];
                this.xKnots[j] = n;
                n = this.yKnots[i];
                this.yKnots[i] = this.yKnots[j];
                this.yKnots[j] = n;
            }
        }
    }
}

