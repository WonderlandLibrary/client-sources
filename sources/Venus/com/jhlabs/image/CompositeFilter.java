/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class CompositeFilter
extends AbstractBufferedImageOp {
    private Composite composite;
    private AffineTransform transform;

    public CompositeFilter() {
    }

    public CompositeFilter(Composite composite) {
        this.composite = composite;
    }

    public CompositeFilter(Composite composite, AffineTransform affineTransform) {
        this.composite = composite;
        this.transform = affineTransform;
    }

    public void setComposite(Composite composite) {
        this.composite = composite;
    }

    public Composite getComposite() {
        return this.composite;
    }

    public void setTransform(AffineTransform affineTransform) {
        this.transform = affineTransform;
    }

    public AffineTransform getTransform() {
        return this.transform;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setComposite(this.composite);
        graphics2D.drawRenderedImage(bufferedImage, this.transform);
        graphics2D.dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Composite";
    }
}

