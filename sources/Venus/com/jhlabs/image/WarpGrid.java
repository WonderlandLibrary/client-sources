/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;

public class WarpGrid {
    public float[] xGrid = null;
    public float[] yGrid = null;
    public int rows;
    public int cols;
    private static final float m00 = -0.5f;
    private static final float m01 = 1.5f;
    private static final float m02 = -1.5f;
    private static final float m03 = 0.5f;
    private static final float m10 = 1.0f;
    private static final float m11 = -2.5f;
    private static final float m12 = 2.0f;
    private static final float m13 = -0.5f;
    private static final float m20 = -0.5f;
    private static final float m22 = 0.5f;
    private static final float m31 = 1.0f;

    public WarpGrid(int n, int n2, int n3, int n4) {
        this.rows = n;
        this.cols = n2;
        this.xGrid = new float[n * n2];
        this.yGrid = new float[n * n2];
        int n5 = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n2; ++j) {
                this.xGrid[n5] = (float)j * (float)(n3 - 1) / (float)(n2 - 1);
                this.yGrid[n5] = (float)i * (float)(n4 - 1) / (float)(n - 1);
                ++n5;
            }
        }
    }

    public void addRow(int n) {
        int n2 = (this.rows + 1) * this.cols;
        float[] fArray = new float[n2];
        float[] fArray2 = new float[n2];
        ++this.rows;
        int n3 = 0;
        int n4 = 0;
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                int n5 = n4 + j;
                int n6 = n3 + j;
                if (i == n) {
                    fArray[n5] = (this.xGrid[n6] + this.xGrid[n5]) / 2.0f;
                    fArray2[n5] = (this.yGrid[n6] + this.yGrid[n5]) / 2.0f;
                    continue;
                }
                fArray[n5] = this.xGrid[n6];
                fArray2[n5] = this.yGrid[n6];
            }
            if (i != n - 1) {
                n3 += this.cols;
            }
            n4 += this.cols;
        }
        this.xGrid = fArray;
        this.yGrid = fArray2;
    }

    public void addCol(int n) {
        int n2 = this.rows * (this.cols + 1);
        float[] fArray = new float[n2];
        float[] fArray2 = new float[n2];
        ++this.cols;
        int n3 = 0;
        int n4 = 0;
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                if (j == n) {
                    fArray[n4] = (this.xGrid[n3] + this.xGrid[n3 - 1]) / 2.0f;
                    fArray2[n4] = (this.yGrid[n3] + this.yGrid[n3 - 1]) / 2.0f;
                } else {
                    fArray[n4] = this.xGrid[n3];
                    fArray2[n4] = this.yGrid[n3];
                    ++n3;
                }
                ++n4;
            }
        }
        this.xGrid = fArray;
        this.yGrid = fArray2;
    }

    public void removeRow(int n) {
        int n2 = (this.rows - 1) * this.cols;
        float[] fArray = new float[n2];
        float[] fArray2 = new float[n2];
        --this.rows;
        int n3 = 0;
        int n4 = 0;
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                int n5 = n4 + j;
                int n6 = n3 + j;
                fArray[n5] = this.xGrid[n6];
                fArray2[n5] = this.yGrid[n6];
            }
            if (i == n - 1) {
                n3 += this.cols;
            }
            n3 += this.cols;
            n4 += this.cols;
        }
        this.xGrid = fArray;
        this.yGrid = fArray2;
    }

    public void removeCol(int n) {
        int n2 = this.rows * (this.cols + 1);
        float[] fArray = new float[n2];
        float[] fArray2 = new float[n2];
        --this.cols;
        for (int i = 0; i < this.rows; ++i) {
            int n3 = i * (this.cols + 1);
            int n4 = i * this.cols;
            for (int j = 0; j < this.cols; ++j) {
                fArray[n4] = this.xGrid[n3];
                fArray2[n4] = this.yGrid[n3];
                if (j == n - 1) {
                    ++n3;
                }
                ++n3;
                ++n4;
            }
        }
        this.xGrid = fArray;
        this.yGrid = fArray2;
    }

    public void lerp(float f, WarpGrid warpGrid, WarpGrid warpGrid2) {
        if (this.rows != warpGrid.rows || this.cols != warpGrid.cols) {
            throw new IllegalArgumentException("source and destination are different sizes");
        }
        if (this.rows != warpGrid2.rows || this.cols != warpGrid2.cols) {
            throw new IllegalArgumentException("source and intermediate are different sizes");
        }
        int n = 0;
        for (int i = 0; i < this.rows; ++i) {
            for (int j = 0; j < this.cols; ++j) {
                warpGrid2.xGrid[n] = ImageMath.lerp(f, this.xGrid[n], warpGrid.xGrid[n]);
                warpGrid2.yGrid[n] = ImageMath.lerp(f, this.yGrid[n], warpGrid.yGrid[n]);
                ++n;
            }
        }
    }

    public void warp(int[] nArray, int n, int n2, WarpGrid warpGrid, WarpGrid warpGrid2, int[] nArray2) {
        try {
            int n3;
            int n4;
            int n5;
            int n6;
            if (warpGrid.rows != warpGrid2.rows || warpGrid.cols != warpGrid2.cols) {
                throw new IllegalArgumentException("source and destination grids are different sizes");
            }
            int n7 = Math.max(n, n2);
            float[] fArray = new float[n7];
            float[] fArray2 = new float[n7];
            float[] fArray3 = new float[n7 + 1];
            float[] fArray4 = new float[n7 + 1];
            int n8 = warpGrid.cols;
            int n9 = warpGrid.rows;
            WarpGrid warpGrid3 = new WarpGrid(n2, n8, 1, 1);
            for (n6 = 0; n6 < n8; ++n6) {
                n5 = n6;
                for (n4 = 0; n4 < n9; ++n4) {
                    fArray[n4] = warpGrid.xGrid[n5];
                    fArray2[n4] = warpGrid.yGrid[n5];
                    n5 += n8;
                }
                this.interpolateSpline(fArray2, fArray, 0, n9, fArray4, 0, n2);
                n5 = n6;
                for (n3 = 0; n3 < n2; ++n3) {
                    warpGrid3.xGrid[n5] = fArray4[n3];
                    n5 += n8;
                }
            }
            for (n6 = 0; n6 < n8; ++n6) {
                n5 = n6;
                for (n4 = 0; n4 < n9; ++n4) {
                    fArray[n4] = warpGrid2.xGrid[n5];
                    fArray2[n4] = warpGrid2.yGrid[n5];
                    n5 += n8;
                }
                this.interpolateSpline(fArray2, fArray, 0, n9, fArray4, 0, n2);
                n5 = n6;
                for (n3 = 0; n3 < n2; ++n3) {
                    warpGrid3.yGrid[n5] = fArray4[n3];
                    n5 += n8;
                }
            }
            int[] nArray3 = new int[n2 * n];
            n5 = 0;
            for (n3 = 0; n3 < n2; ++n3) {
                this.interpolateSpline(warpGrid3.xGrid, warpGrid3.yGrid, n5, n8, fArray3, 0, n);
                fArray3[n] = n;
                ImageMath.resample(nArray, nArray3, n, n3 * n, 1, fArray3);
                n5 += n8;
            }
            warpGrid3 = new WarpGrid(n9, n, 1, 1);
            n5 = 0;
            int n10 = 0;
            for (n4 = 0; n4 < n9; ++n4) {
                this.interpolateSpline(warpGrid.xGrid, warpGrid.yGrid, n5, n8, warpGrid3.xGrid, n10, n);
                n5 += n8;
                n10 += n;
            }
            n5 = 0;
            n10 = 0;
            for (n4 = 0; n4 < n9; ++n4) {
                this.interpolateSpline(warpGrid2.xGrid, warpGrid2.yGrid, n5, n8, warpGrid3.yGrid, n10, n);
                n5 += n8;
                n10 += n;
            }
            for (int i = 0; i < n; ++i) {
                int n11 = i;
                for (n4 = 0; n4 < n9; ++n4) {
                    fArray[n4] = warpGrid3.xGrid[n11];
                    fArray2[n4] = warpGrid3.yGrid[n11];
                    n11 += n;
                }
                this.interpolateSpline(fArray, fArray2, 0, n9, fArray3, 0, n2);
                fArray3[n2] = n2;
                ImageMath.resample(nArray3, nArray2, n2, i, n, fArray3);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    protected void interpolateSpline(float[] fArray, float[] fArray2, int n, int n2, float[] fArray3, int n3, int n4) {
        float f;
        int n5 = n;
        int n6 = n + n2 - 1;
        float f2 = fArray[n5];
        float f3 = f = fArray2[n5];
        float f4 = f;
        float f5 = fArray[n5 + 1];
        float f6 = fArray2[n5 + 1];
        for (int i = 0; i < n4; ++i) {
            if (n5 <= n6 && (float)i > fArray[n5]) {
                f4 = f3;
                f3 = f;
                f = f6;
                f2 = fArray[n5];
                if (++n5 <= n6) {
                    f5 = fArray[n5];
                }
                f6 = n5 < n6 ? fArray2[n5 + 1] : f;
            }
            float f7 = ((float)i - f2) / (f5 - f2);
            float f8 = -0.5f * f4 + 1.5f * f3 + -1.5f * f + 0.5f * f6;
            float f9 = 1.0f * f4 + -2.5f * f3 + 2.0f * f + -0.5f * f6;
            float f10 = -0.5f * f4 + 0.5f * f;
            float f11 = 1.0f * f3;
            fArray3[n3 + i] = ((f8 * f7 + f9) * f7 + f10) * f7 + f11;
        }
    }

    protected void interpolateSpline2(float[] fArray, float[] fArray2, int n, float[] fArray3, int n2, int n3) {
        int n4 = n;
        float f = fArray[n4];
        float f2 = fArray2[n4];
        float f3 = fArray[n4 + 1];
        float f4 = fArray2[n4 + 1];
        for (int i = 0; i < n3; ++i) {
            if ((float)i > fArray[n4]) {
                f = fArray[n4];
                f2 = fArray2[n4];
                f3 = fArray[++n4];
                f4 = fArray2[n4];
            }
            float f5 = ((float)i - f) / (f3 - f);
            fArray3[n2 + i] = f2 + f5 * (f4 - f2);
        }
    }
}

