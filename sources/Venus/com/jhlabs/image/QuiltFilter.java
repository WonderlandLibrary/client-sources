/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.LinearColormap;
import com.jhlabs.image.WholeImageFilter;
import java.awt.Rectangle;
import java.util.Date;
import java.util.Random;

public class QuiltFilter
extends WholeImageFilter {
    private Random randomGenerator;
    private long seed = 567L;
    private int iterations = 25000;
    private float a = -0.59f;
    private float b = 0.2f;
    private float c = 0.1f;
    private float d = 0.0f;
    private int k = 0;
    private Colormap colormap = new LinearColormap();

    public QuiltFilter() {
        this.randomGenerator = new Random();
    }

    public void randomize() {
        this.seed = new Date().getTime();
        this.randomGenerator.setSeed(this.seed);
        this.a = this.randomGenerator.nextFloat();
        this.b = this.randomGenerator.nextFloat();
        this.c = this.randomGenerator.nextFloat();
        this.d = this.randomGenerator.nextFloat();
        this.k = this.randomGenerator.nextInt() % 20 - 10;
    }

    public void setIterations(int n) {
        this.iterations = n;
    }

    public int getIterations() {
        return this.iterations;
    }

    public void setA(float f) {
        this.a = f;
    }

    public float getA() {
        return this.a;
    }

    public void setB(float f) {
        this.b = f;
    }

    public float getB() {
        return this.b;
    }

    public void setC(float f) {
        this.c = f;
    }

    public float getC() {
        return this.c;
    }

    public void setD(float f) {
        this.d = f;
    }

    public float getD() {
        return this.d;
    }

    public void setK(int n) {
        this.k = n;
    }

    public int getK() {
        return this.k;
    }

    public void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    public Colormap getColormap() {
        return this.colormap;
    }

    @Override
    protected int[] filterPixels(int n, int n2, int[] nArray, Rectangle rectangle) {
        float f;
        float f2;
        float f3;
        float f4;
        int n3;
        int[] nArray2 = new int[n * n2];
        boolean bl = false;
        int n4 = 0;
        float f5 = 0.1f;
        float f6 = 0.3f;
        for (n3 = 0; n3 < 20; ++n3) {
            f4 = (float)Math.PI * f5;
            f3 = (float)Math.PI * f6;
            f2 = (float)Math.sin(2.0f * f4);
            f = (float)Math.sin(2.0f * f3);
            float f7 = (float)((double)(this.a * f2) + (double)(this.b * f2) * Math.cos(2.0f * f3) + (double)this.c * Math.sin(4.0f * f4) + (double)this.d * Math.sin(6.0f * f4) * Math.cos(4.0f * f3) + (double)((float)this.k * f5));
            f7 = f7 >= 0.0f ? f7 - (float)((int)f7) : f7 - (float)((int)f7) + 1.0f;
            float f8 = (float)((double)(this.a * f) + (double)(this.b * f) * Math.cos(2.0f * f4) + (double)this.c * Math.sin(4.0f * f3) + (double)this.d * Math.sin(6.0f * f3) * Math.cos(4.0f * f4) + (double)((float)this.k * f6));
            f8 = f8 >= 0.0f ? f8 - (float)((int)f8) : f8 - (float)((int)f8) + 1.0f;
            f5 = f7;
            f6 = f8;
        }
        for (n3 = 0; n3 < this.iterations; ++n3) {
            int n5;
            f4 = (float)Math.PI * f5;
            f3 = (float)Math.PI * f6;
            f2 = (float)((double)this.a * Math.sin(2.0f * f4) + (double)this.b * Math.sin(2.0f * f4) * Math.cos(2.0f * f3) + (double)this.c * Math.sin(4.0f * f4) + (double)this.d * Math.sin(6.0f * f4) * Math.cos(4.0f * f3) + (double)((float)this.k * f5));
            f2 = f2 >= 0.0f ? f2 - (float)((int)f2) : f2 - (float)((int)f2) + 1.0f;
            f = (float)((double)this.a * Math.sin(2.0f * f3) + (double)this.b * Math.sin(2.0f * f3) * Math.cos(2.0f * f4) + (double)this.c * Math.sin(4.0f * f3) + (double)this.d * Math.sin(6.0f * f3) * Math.cos(4.0f * f4) + (double)((float)this.k * f6));
            f = f >= 0.0f ? f - (float)((int)f) : f - (float)((int)f) + 1.0f;
            f5 = f2;
            f6 = f;
            int n6 = (int)((float)n * f5);
            int n7 = (int)((float)n2 * f6);
            if (n6 < 0 || n6 >= n || n7 < 0 || n7 >= n2) continue;
            int n8 = n * n7 + n6;
            nArray2[n8] = nArray2[n8] + 1;
            if (n5 <= n4) continue;
            n4 = n5;
        }
        if (this.colormap != null) {
            n3 = 0;
            for (f6 = 0.0f; f6 < (float)n2; f6 += 1.0f) {
                for (f5 = 0.0f; f5 < (float)n; f5 += 1.0f) {
                    nArray2[n3] = this.colormap.getColor((float)nArray2[n3] / (float)n4);
                    ++n3;
                }
            }
        }
        return nArray2;
    }

    public String toString() {
        return "Texture/Chaotic Quilt...";
    }
}

