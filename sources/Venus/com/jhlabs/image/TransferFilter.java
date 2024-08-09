/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.PixelUtils;
import com.jhlabs.image.PointFilter;
import java.awt.image.BufferedImage;

public abstract class TransferFilter
extends PointFilter {
    protected int[] rTable;
    protected int[] gTable;
    protected int[] bTable;
    protected boolean initialized = false;

    public TransferFilter() {
        this.canFilterIndexColorModel = true;
    }

    @Override
    public int filterRGB(int n, int n2, int n3) {
        int n4 = n3 & 0xFF000000;
        int n5 = n3 >> 16 & 0xFF;
        int n6 = n3 >> 8 & 0xFF;
        int n7 = n3 & 0xFF;
        n5 = this.rTable[n5];
        n6 = this.gTable[n6];
        n7 = this.bTable[n7];
        return n4 | n5 << 16 | n6 << 8 | n7;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        if (!this.initialized) {
            this.initialize();
        }
        return super.filter(bufferedImage, bufferedImage2);
    }

    protected void initialize() {
        this.initialized = true;
        this.bTable = this.makeTable();
        this.gTable = this.bTable;
        this.rTable = this.bTable;
    }

    protected int[] makeTable() {
        int[] nArray = new int[256];
        for (int i = 0; i < 256; ++i) {
            nArray[i] = PixelUtils.clamp((int)(255.0f * this.transferFunction((float)i / 255.0f)));
        }
        return nArray;
    }

    protected float transferFunction(float f) {
        return 0.0f;
    }

    public int[] getLUT() {
        if (!this.initialized) {
            this.initialize();
        }
        int[] nArray = new int[256];
        for (int i = 0; i < 256; ++i) {
            nArray[i] = this.filterRGB(0, 0, i << 24 | i << 16 | i << 8 | i);
        }
        return nArray;
    }
}

