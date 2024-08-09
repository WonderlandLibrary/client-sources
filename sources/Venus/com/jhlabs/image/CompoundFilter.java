/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

public class CompoundFilter
extends AbstractBufferedImageOp {
    private BufferedImageOp filter1;
    private BufferedImageOp filter2;

    public CompoundFilter(BufferedImageOp bufferedImageOp, BufferedImageOp bufferedImageOp2) {
        this.filter1 = bufferedImageOp;
        this.filter2 = bufferedImageOp2;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        BufferedImage bufferedImage3 = this.filter1.filter(bufferedImage, bufferedImage2);
        bufferedImage3 = this.filter2.filter(bufferedImage3, bufferedImage2);
        return bufferedImage3;
    }
}

