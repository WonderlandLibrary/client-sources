/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.CellularFilter;
import com.jhlabs.image.ImageMath;

public class CrystallizeFilter
extends CellularFilter {
    private float edgeThickness = 0.4f;
    private boolean fadeEdges = false;
    private int edgeColor = -16777216;

    public CrystallizeFilter() {
        this.setScale(16.0f);
        this.setRandomness(0.0f);
    }

    public void setEdgeThickness(float f) {
        this.edgeThickness = f;
    }

    public float getEdgeThickness() {
        return this.edgeThickness;
    }

    public void setFadeEdges(boolean bl) {
        this.fadeEdges = bl;
    }

    public boolean getFadeEdges() {
        return this.fadeEdges;
    }

    public void setEdgeColor(int n) {
        this.edgeColor = n;
    }

    public int getEdgeColor() {
        return this.edgeColor;
    }

    @Override
    public int getPixel(int n, int n2, int[] nArray, int n3, int n4) {
        float f = this.m00 * (float)n + this.m01 * (float)n2;
        float f2 = this.m10 * (float)n + this.m11 * (float)n2;
        f /= this.scale;
        f2 /= this.scale * this.stretch;
        float f3 = this.evaluate(f += 1000.0f, f2 += 1000.0f);
        float f4 = this.results[0].distance;
        float f5 = this.results[1].distance;
        int n5 = ImageMath.clamp((int)((this.results[0].x - 1000.0f) * this.scale), 0, n3 - 1);
        int n6 = ImageMath.clamp((int)((this.results[0].y - 1000.0f) * this.scale), 0, n4 - 1);
        int n7 = nArray[n6 * n3 + n5];
        f3 = (f5 - f4) / this.edgeThickness;
        f3 = ImageMath.smoothStep(0.0f, this.edgeThickness, f3);
        if (this.fadeEdges) {
            n5 = ImageMath.clamp((int)((this.results[1].x - 1000.0f) * this.scale), 0, n3 - 1);
            n6 = ImageMath.clamp((int)((this.results[1].y - 1000.0f) * this.scale), 0, n4 - 1);
            int n8 = nArray[n6 * n3 + n5];
            n8 = ImageMath.mixColors(0.5f, n8, n7);
            n7 = ImageMath.mixColors(f3, n8, n7);
        } else {
            n7 = ImageMath.mixColors(f3, this.edgeColor, n7);
        }
        return n7;
    }

    @Override
    public String toString() {
        return "Pixellate/Crystallize...";
    }
}

