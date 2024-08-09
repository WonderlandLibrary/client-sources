/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PointFilter;

public class FadeFilter
extends PointFilter {
    private int width;
    private int height;
    private float angle = 0.0f;
    private float fadeStart = 1.0f;
    private float fadeWidth = 10.0f;
    private int sides;
    private boolean invert;
    private float m00 = 1.0f;
    private float m01 = 0.0f;
    private float m10 = 0.0f;
    private float m11 = 1.0f;

    public void setAngle(float f) {
        this.angle = f;
        float f2 = (float)Math.cos(f);
        float f3 = (float)Math.sin(f);
        this.m00 = f2;
        this.m01 = f3;
        this.m10 = -f3;
        this.m11 = f2;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setSides(int n) {
        this.sides = n;
    }

    public int getSides() {
        return this.sides;
    }

    public void setFadeStart(float f) {
        this.fadeStart = f;
    }

    public float getFadeStart() {
        return this.fadeStart;
    }

    public void setFadeWidth(float f) {
        this.fadeWidth = f;
    }

    public float getFadeWidth() {
        return this.fadeWidth;
    }

    public void setInvert(boolean bl) {
        this.invert = bl;
    }

    public boolean getInvert() {
        return this.invert;
    }

    @Override
    public void setDimensions(int n, int n2) {
        this.width = n;
        this.height = n2;
        super.setDimensions(n, n2);
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        float f = this.m00 * (float)n + this.m01 * (float)n2;
        float f2 = this.m10 * (float)n + this.m11 * (float)n2;
        if (this.sides == 2) {
            f = (float)Math.sqrt(f * f + f2 * f2);
        } else if (this.sides == 3) {
            f = ImageMath.mod(f, 16.0f);
        } else if (this.sides == 4) {
            f = this.symmetry(f, 16.0f);
        }
        int n4 = (int)(ImageMath.smoothStep(this.fadeStart, this.fadeStart + this.fadeWidth, f) * 255.0f);
        if (this.invert) {
            n4 = 255 - n4;
        }
        return n4 << 24 | n3 & 0xFFFFFF;
    }

    public float symmetry(float f, float f2) {
        if ((f = ImageMath.mod(f, 2.0f * f2)) > f2) {
            return 2.0f * f2 - f;
        }
        return f;
    }

    public String toString() {
        return "Fade...";
    }
}

