/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;

public class Curve {
    public float[] x;
    public float[] y;

    public Curve() {
        this.x = new float[]{0.0f, 1.0f};
        this.y = new float[]{0.0f, 1.0f};
    }

    public Curve(Curve curve) {
        this.x = (float[])curve.x.clone();
        this.y = (float[])curve.y.clone();
    }

    public int addKnot(float f, float f2) {
        int n = -1;
        int n2 = this.x.length;
        float[] fArray = new float[n2 + 1];
        float[] fArray2 = new float[n2 + 1];
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            if (n == -1 && this.x[i] > f) {
                n = n3;
                fArray[n3] = f;
                fArray2[n3] = f2;
                ++n3;
            }
            fArray[n3] = this.x[i];
            fArray2[n3] = this.y[i];
            ++n3;
        }
        if (n == -1) {
            n = n3;
            fArray[n3] = f;
            fArray2[n3] = f2;
        }
        this.x = fArray;
        this.y = fArray2;
        return n;
    }

    public void removeKnot(int n) {
        int n2 = this.x.length;
        if (n2 <= 2) {
            return;
        }
        float[] fArray = new float[n2 - 1];
        float[] fArray2 = new float[n2 - 1];
        int n3 = 0;
        for (int i = 0; i < n2 - 1; ++i) {
            if (i == n) {
                ++n3;
            }
            fArray[i] = this.x[n3];
            fArray2[i] = this.y[n3];
            ++n3;
        }
        this.x = fArray;
        this.y = fArray2;
    }

    private void sortKnots() {
        int n = this.x.length;
        for (int i = 1; i < n - 1; ++i) {
            for (int j = 1; j < i; ++j) {
                if (!(this.x[i] < this.x[j])) continue;
                float f = this.x[i];
                this.x[i] = this.x[j];
                this.x[j] = f;
                f = this.y[i];
                this.y[i] = this.y[j];
                this.y[j] = f;
            }
        }
    }

    protected int[] makeTable() {
        int n = this.x.length;
        float[] fArray = new float[n + 2];
        float[] fArray2 = new float[n + 2];
        System.arraycopy(this.x, 0, fArray, 1, n);
        System.arraycopy(this.y, 0, fArray2, 1, n);
        fArray[0] = fArray[1];
        fArray2[0] = fArray2[1];
        fArray[n + 1] = fArray[n];
        fArray2[n + 1] = fArray2[n];
        int[] nArray = new int[256];
        for (int i = 0; i < 1024; ++i) {
            float f = (float)i / 1024.0f;
            int n2 = (int)(255.0f * ImageMath.spline(f, fArray.length, fArray) + 0.5f);
            int n3 = (int)(255.0f * ImageMath.spline(f, fArray.length, fArray2) + 0.5f);
            n2 = ImageMath.clamp(n2, 0, 255);
            nArray[n2] = n3 = ImageMath.clamp(n3, 0, 255);
        }
        return nArray;
    }
}

