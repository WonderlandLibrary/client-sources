/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import java.awt.Rectangle;

public class ShearFilter
extends TransformFilter {
    private float xangle = 0.0f;
    private float yangle = 0.0f;
    private float shx = 0.0f;
    private float shy = 0.0f;
    private float xoffset = 0.0f;
    private float yoffset = 0.0f;
    private boolean resize = true;

    public void setResize(boolean bl) {
        this.resize = bl;
    }

    public boolean isResize() {
        return this.resize;
    }

    public void setXAngle(float f) {
        this.xangle = f;
        this.initialize();
    }

    public float getXAngle() {
        return this.xangle;
    }

    public void setYAngle(float f) {
        this.yangle = f;
        this.initialize();
    }

    public float getYAngle() {
        return this.yangle;
    }

    private void initialize() {
        this.shx = (float)Math.sin(this.xangle);
        this.shy = (float)Math.sin(this.yangle);
    }

    @Override
    protected void transformSpace(Rectangle rectangle) {
        float f = (float)Math.tan(this.xangle);
        this.xoffset = (float)(-rectangle.height) * f;
        if ((double)f < 0.0) {
            f = -f;
        }
        rectangle.width = (int)((float)rectangle.height * f + (float)rectangle.width + 0.999999f);
        f = (float)Math.tan(this.yangle);
        this.yoffset = (float)(-rectangle.width) * f;
        if ((double)f < 0.0) {
            f = -f;
        }
        rectangle.height = (int)((float)rectangle.width * f + (float)rectangle.height + 0.999999f);
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        fArray[0] = (float)n + this.xoffset + (float)n2 * this.shx;
        fArray[1] = (float)n2 + this.yoffset + (float)n * this.shy;
    }

    public String toString() {
        return "Distort/Shear...";
    }
}

