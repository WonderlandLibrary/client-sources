/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.WarpGrid;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class WarpFilter
extends WholeImageFilter {
    private WarpGrid sourceGrid;
    private WarpGrid destGrid;
    private int frames = 1;
    private BufferedImage morphImage;
    private float time;

    public WarpFilter() {
    }

    public WarpFilter(WarpGrid warpGrid, WarpGrid warpGrid2) {
        this.sourceGrid = warpGrid;
        this.destGrid = warpGrid2;
    }

    public void setSourceGrid(WarpGrid warpGrid) {
        this.sourceGrid = warpGrid;
    }

    public WarpGrid getSourceGrid() {
        return this.sourceGrid;
    }

    public void setDestGrid(WarpGrid warpGrid) {
        this.destGrid = warpGrid;
    }

    public WarpGrid getDestGrid() {
        return this.destGrid;
    }

    public void setFrames(int n) {
        this.frames = n;
    }

    public int getFrames() {
        return this.frames;
    }

    public void setMorphImage(BufferedImage bufferedImage) {
        this.morphImage = bufferedImage;
    }

    public BufferedImage getMorphImage() {
        return this.morphImage;
    }

    public void setTime(float f) {
        this.time = f;
    }

    public float getTime() {
        return this.time;
    }

    @Override
    protected void transformSpace(Rectangle rectangle) {
        rectangle.width *= this.frames;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        int[] nArray2 = new int[n * n2];
        if (this.morphImage != null) {
            int[] nArray3 = this.getRGB(this.morphImage, 0, 0, n, n2, null);
            this.morph(nArray, nArray3, nArray2, this.sourceGrid, this.destGrid, n, n2, this.time);
        } else if (this.frames <= 1) {
            this.sourceGrid.warp(nArray, n, n2, this.sourceGrid, this.destGrid, nArray2);
        } else {
            WarpGrid warpGrid = new WarpGrid(this.sourceGrid.rows, this.sourceGrid.cols, n, n2);
            for (int i = 0; i < this.frames; ++i) {
                float f = (float)i / (float)(this.frames - 1);
                this.sourceGrid.lerp(f, this.destGrid, warpGrid);
                this.sourceGrid.warp(nArray, n, n2, this.sourceGrid, warpGrid, nArray2);
            }
        }
        return nArray2;
    }

    public void morph(int[] nArray, int[] nArray2, int[] nArray3, WarpGrid warpGrid, WarpGrid warpGrid2, int n, int n2, float f) {
        WarpGrid warpGrid3 = new WarpGrid(warpGrid.rows, warpGrid.cols, n, n2);
        warpGrid.lerp(f, warpGrid2, warpGrid3);
        warpGrid.warp(nArray, n, n2, warpGrid, warpGrid3, nArray3);
        int[] nArray4 = new int[n * n2];
        warpGrid2.warp(nArray2, n, n2, warpGrid2, warpGrid3, nArray4);
        this.crossDissolve(nArray3, nArray4, n, n2, f);
    }

    public void crossDissolve(int[] nArray, int[] nArray2, int n, int n2, float f) {
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                nArray[n3] = ImageMath.mixColors(f, nArray[n3], nArray2[n3]);
                ++n3;
            }
        }
    }

    public String toString() {
        return "Distort/Mesh Warp...";
    }
}

