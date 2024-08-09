/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.TransformFilter;
import com.jhlabs.math.Noise;
import java.awt.image.BufferedImage;

public class MarbleFilter
extends TransformFilter {
    private float[] sinTable;
    private float[] cosTable;
    private float xScale = 4.0f;
    private float yScale = 4.0f;
    private float amount = 1.0f;
    private float turbulence = 1.0f;

    public MarbleFilter() {
        this.setEdgeAction(1);
    }

    public void setXScale(float f) {
        this.xScale = f;
    }

    public float getXScale() {
        return this.xScale;
    }

    public void setYScale(float f) {
        this.yScale = f;
    }

    public float getYScale() {
        return this.yScale;
    }

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setTurbulence(float f) {
        this.turbulence = f;
    }

    public float getTurbulence() {
        return this.turbulence;
    }

    private void initialize() {
        this.sinTable = new float[256];
        this.cosTable = new float[256];
        for (int i = 0; i < 256; ++i) {
            float f = (float)Math.PI * 2 * (float)i / 256.0f * this.turbulence;
            this.sinTable[i] = (float)((double)(-this.yScale) * Math.sin(f));
            this.cosTable[i] = (float)((double)this.yScale * Math.cos(f));
        }
    }

    private int displacementMap(int n, int n2) {
        return PixelUtils.clamp((int)(127.0f * (1.0f + Noise.noise2((float)n / this.xScale, (float)n2 / this.xScale))));
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        int n3 = this.displacementMap(n, n2);
        fArray[0] = (float)n + this.sinTable[n3];
        fArray[1] = (float)n2 + this.cosTable[n3];
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        this.initialize();
        return super.filter(bufferedImage, bufferedImage2);
    }

    public String toString() {
        return "Distort/Marble...";
    }
}

