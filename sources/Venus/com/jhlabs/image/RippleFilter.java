/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.TransformFilter;
import com.jhlabs.math.Noise;
import java.awt.Rectangle;

public class RippleFilter
extends TransformFilter {
    public static final int SINE = 0;
    public static final int SAWTOOTH = 1;
    public static final int TRIANGLE = 2;
    public static final int NOISE = 3;
    private float xAmplitude = 5.0f;
    private float yAmplitude = 0.0f;
    private float xWavelength = 16.0f;
    private float yWavelength = 16.0f;
    private int waveType;

    public void setXAmplitude(float f) {
        this.xAmplitude = f;
    }

    public float getXAmplitude() {
        return this.xAmplitude;
    }

    public void setXWavelength(float f) {
        this.xWavelength = f;
    }

    public float getXWavelength() {
        return this.xWavelength;
    }

    public void setYAmplitude(float f) {
        this.yAmplitude = f;
    }

    public float getYAmplitude() {
        return this.yAmplitude;
    }

    public void setYWavelength(float f) {
        this.yWavelength = f;
    }

    public float getYWavelength() {
        return this.yWavelength;
    }

    public void setWaveType(int n) {
        this.waveType = n;
    }

    public int getWaveType() {
        return this.waveType;
    }

    @Override
    protected void transformSpace(Rectangle rectangle) {
        if (this.edgeAction == 0) {
            rectangle.x -= (int)this.xAmplitude;
            rectangle.width += (int)(2.0f * this.xAmplitude);
            rectangle.y -= (int)this.yAmplitude;
            rectangle.height += (int)(2.0f * this.yAmplitude);
        }
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        float f;
        float f2 = (float)n2 / this.xWavelength;
        float f3 = (float)n / this.yWavelength;
        float f4 = switch (this.waveType) {
            default -> {
                f = (float)Math.sin(f2);
                yield (float)Math.sin(f3);
            }
            case 1 -> {
                f = ImageMath.mod(f2, 1.0f);
                yield ImageMath.mod(f3, 1.0f);
            }
            case 2 -> {
                f = ImageMath.triangle(f2);
                yield ImageMath.triangle(f3);
            }
            case 3 -> {
                f = Noise.noise1(f2);
                yield Noise.noise1(f3);
            }
        };
        fArray[0] = (float)n + this.xAmplitude * f;
        fArray[1] = (float)n2 + this.yAmplitude * f4;
    }

    public String toString() {
        return "Distort/Ripple...";
    }
}

