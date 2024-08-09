/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PointFilter;

public class CheckFilter
extends PointFilter {
    private int xScale = 8;
    private int yScale = 8;
    private int foreground = -1;
    private int background = -16777216;
    private int fuzziness = 0;
    private float angle = 0.0f;
    private float m00 = 1.0f;
    private float m01 = 0.0f;
    private float m10 = 0.0f;
    private float m11 = 1.0f;

    public void setForeground(int n) {
        this.foreground = n;
    }

    public int getForeground() {
        return this.foreground;
    }

    public void setBackground(int n) {
        this.background = n;
    }

    public int getBackground() {
        return this.background;
    }

    public void setXScale(int n) {
        this.xScale = n;
    }

    public int getXScale() {
        return this.xScale;
    }

    public void setYScale(int n) {
        this.yScale = n;
    }

    public int getYScale() {
        return this.yScale;
    }

    public void setFuzziness(int n) {
        this.fuzziness = n;
    }

    public int getFuzziness() {
        return this.fuzziness;
    }

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

    @Override
    public int filterRGB(int n, int n2, int n3) {
        float f;
        float f2 = (this.m00 * (float)n + this.m01 * (float)n2) / (float)this.xScale;
        float f3 = (this.m10 * (float)n + this.m11 * (float)n2) / (float)this.yScale;
        float f4 = f = (int)(f2 + 100000.0f) % 2 != (int)(f3 + 100000.0f) % 2 ? 1.0f : 0.0f;
        if (this.fuzziness != 0) {
            float f5 = (float)this.fuzziness / 100.0f;
            float f6 = ImageMath.smoothPulse(0.0f, f5, 1.0f - f5, 1.0f, ImageMath.mod(f2, 1.0f));
            float f7 = ImageMath.smoothPulse(0.0f, f5, 1.0f - f5, 1.0f, ImageMath.mod(f3, 1.0f));
            f *= f6 * f7;
        }
        return ImageMath.mixColors(f, this.foreground, this.background);
    }

    public String toString() {
        return "Texture/Checkerboard...";
    }
}

