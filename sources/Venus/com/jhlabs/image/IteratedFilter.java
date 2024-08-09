/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public class IteratedFilter
extends AbstractBufferedImageOp {
    private BufferedImageOp filter;
    private int iterations;

    public IteratedFilter(BufferedImageOp bufferedImageOp, int n) {
        this.filter = bufferedImageOp;
        this.iterations = n;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        BufferedImage bufferedImage3 = bufferedImage;
        for (int i = 0; i < this.iterations; ++i) {
            bufferedImage3 = this.filter.filter(bufferedImage3, bufferedImage2);
        }
        return bufferedImage3;
    }
}

