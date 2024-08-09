/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PointFilter;

public class WeaveFilter
extends PointFilter {
    private float xWidth = 16.0f;
    private float yWidth = 16.0f;
    private float xGap = 6.0f;
    private float yGap = 6.0f;
    private int rows = 4;
    private int cols = 4;
    private int rgbX = -32640;
    private int rgbY = -8355585;
    private boolean useImageColors = true;
    private boolean roundThreads = false;
    private boolean shadeCrossings = true;
    public int[][] matrix = new int[][]{{0, 1, 0, 1}, {1, 0, 1, 0}, {0, 1, 0, 1}, {1, 0, 1, 0}};

    public void setXGap(float f) {
        this.xGap = f;
    }

    public void setXWidth(float f) {
        this.xWidth = f;
    }

    public float getXWidth() {
        return this.xWidth;
    }

    public void setYWidth(float f) {
        this.yWidth = f;
    }

    public float getYWidth() {
        return this.yWidth;
    }

    public float getXGap() {
        return this.xGap;
    }

    public void setYGap(float f) {
        this.yGap = f;
    }

    public float getYGap() {
        return this.yGap;
    }

    public void setCrossings(int[][] nArray) {
        this.matrix = nArray;
    }

    public int[][] getCrossings() {
        return this.matrix;
    }

    public void setUseImageColors(boolean bl) {
        this.useImageColors = bl;
    }

    public boolean getUseImageColors() {
        return this.useImageColors;
    }

    public void setRoundThreads(boolean bl) {
        this.roundThreads = bl;
    }

    public boolean getRoundThreads() {
        return this.roundThreads;
    }

    public void setShadeCrossings(boolean bl) {
        this.shadeCrossings = bl;
    }

    public boolean getShadeCrossings() {
        return this.shadeCrossings;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        float f;
        float f2;
        float f3;
        float f4;
        boolean bl;
        n = (int)((float)n + (this.xWidth + this.xGap / 2.0f));
        n2 = (int)((float)n2 + (this.yWidth + this.yGap / 2.0f));
        float f5 = ImageMath.mod((float)n, this.xWidth + this.xGap);
        float f6 = ImageMath.mod((float)n2, this.yWidth + this.yGap);
        int n7 = (int)((float)n / (this.xWidth + this.xGap));
        int n8 = (int)((float)n2 / (this.yWidth + this.yGap));
        boolean bl2 = f5 < this.xWidth;
        boolean bl3 = bl = f6 < this.yWidth;
        if (this.roundThreads) {
            f4 = Math.abs(this.xWidth / 2.0f - f5) / this.xWidth / 2.0f;
            f3 = Math.abs(this.yWidth / 2.0f - f6) / this.yWidth / 2.0f;
        } else {
            f3 = 0.0f;
            f4 = 0.0f;
        }
        if (this.shadeCrossings) {
            f2 = ImageMath.smoothStep(this.xWidth / 2.0f, this.xWidth / 2.0f + this.xGap, Math.abs(this.xWidth / 2.0f - f5));
            f = ImageMath.smoothStep(this.yWidth / 2.0f, this.yWidth / 2.0f + this.yGap, Math.abs(this.yWidth / 2.0f - f6));
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        if (this.useImageColors) {
            n5 = n6 = n3;
        } else {
            n5 = this.rgbX;
            n6 = this.rgbY;
        }
        int n9 = n7 % this.cols;
        int n10 = n8 % this.rows;
        int n11 = this.matrix[n10][n9];
        if (bl2) {
            if (bl) {
                n4 = n11 == 1 ? n5 : n6;
                n4 = ImageMath.mixColors(2.0f * (n11 == 1 ? f4 : f3), n4, -16777216);
            } else {
                if (this.shadeCrossings) {
                    if (n11 != this.matrix[(n8 + 1) % this.rows][n9]) {
                        if (n11 == 0) {
                            f = 1.0f - f;
                        }
                        n5 = ImageMath.mixColors(f *= 0.5f, n5, -16777216);
                    } else if (n11 == 0) {
                        n5 = ImageMath.mixColors(0.5f, n5, -16777216);
                    }
                }
                n4 = ImageMath.mixColors(2.0f * f4, n5, -16777216);
            }
        } else if (bl) {
            if (this.shadeCrossings) {
                if (n11 != this.matrix[n10][(n7 + 1) % this.cols]) {
                    if (n11 == 1) {
                        f2 = 1.0f - f2;
                    }
                    n6 = ImageMath.mixColors(f2 *= 0.5f, n6, -16777216);
                } else if (n11 == 1) {
                    n6 = ImageMath.mixColors(0.5f, n6, -16777216);
                }
            }
            n4 = ImageMath.mixColors(2.0f * f3, n6, -16777216);
        } else {
            n4 = 0;
        }
        return n4;
    }

    public String toString() {
        return "Texture/Weave...";
    }
}

