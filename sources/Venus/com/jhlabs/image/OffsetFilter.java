/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import java.awt.image.BufferedImage;

public class OffsetFilter
extends TransformFilter {
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;
    private boolean wrap;

    public OffsetFilter() {
        this(0, 0, true);
    }

    public OffsetFilter(int n, int n2, boolean bl) {
        this.xOffset = n;
        this.yOffset = n2;
        this.wrap = bl;
        this.setEdgeAction(0);
    }

    public void setXOffset(int n) {
        this.xOffset = n;
    }

    public int getXOffset() {
        return this.xOffset;
    }

    public void setYOffset(int n) {
        this.yOffset = n;
    }

    public int getYOffset() {
        return this.yOffset;
    }

    public void setWrap(boolean bl) {
        this.wrap = bl;
    }

    public boolean getWrap() {
        return this.wrap;
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        if (this.wrap) {
            fArray[0] = (n + this.width - this.xOffset) % this.width;
            fArray[1] = (n2 + this.height - this.yOffset) % this.height;
        } else {
            fArray[0] = n - this.xOffset;
            fArray[1] = n2 - this.yOffset;
        }
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        this.width = bufferedImage.getWidth();
        this.height = bufferedImage.getHeight();
        if (this.wrap) {
            while (this.xOffset < 0) {
                this.xOffset += this.width;
            }
            while (this.yOffset < 0) {
                this.yOffset += this.height;
            }
            this.xOffset %= this.width;
            this.yOffset %= this.height;
        }
        return super.filter(bufferedImage, bufferedImage2);
    }

    public String toString() {
        return "Distort/Offset...";
    }
}

