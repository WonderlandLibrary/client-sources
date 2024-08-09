/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import java.awt.image.BufferedImage;

public class DiffuseFilter
extends TransformFilter {
    private float[] sinTable;
    private float[] cosTable;
    private float scale = 4.0f;

    public DiffuseFilter() {
        this.setEdgeAction(1);
    }

    public void setScale(float f) {
        this.scale = f;
    }

    public float getScale() {
        return this.scale;
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        int n3 = (int)(Math.random() * 255.0);
        float f = (float)Math.random();
        fArray[0] = (float)n + f * this.sinTable[n3];
        fArray[1] = (float)n2 + f * this.cosTable[n3];
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        this.sinTable = new float[256];
        this.cosTable = new float[256];
        for (int i = 0; i < 256; ++i) {
            float f = (float)Math.PI * 2 * (float)i / 256.0f;
            this.sinTable[i] = (float)((double)this.scale * Math.sin(f));
            this.cosTable[i] = (float)((double)this.scale * Math.cos(f));
        }
        return super.filter(bufferedImage, bufferedImage2);
    }

    public String toString() {
        return "Distort/Diffuse...";
    }
}

