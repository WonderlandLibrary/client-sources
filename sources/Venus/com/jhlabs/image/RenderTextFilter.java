/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.Composite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class RenderTextFilter
extends AbstractBufferedImageOp {
    private String text;
    private Font font;
    private Paint paint;
    private Composite composite;
    private AffineTransform transform;

    public RenderTextFilter() {
    }

    public RenderTextFilter(String string, Font font, Paint paint, Composite composite, AffineTransform affineTransform) {
        this.text = string;
        this.font = font;
        this.composite = composite;
        this.paint = paint;
        this.transform = affineTransform;
    }

    public void setText(String string) {
        this.text = string;
    }

    public String getText() {
        return this.text;
    }

    public void setComposite(Composite composite) {
        this.composite = composite;
    }

    public Composite getComposite() {
        return this.composite;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public Paint getPaint() {
        return this.paint;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public Font getFont() {
        return this.font;
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
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        if (this.font != null) {
            graphics2D.setFont(this.font);
        }
        if (this.transform != null) {
            graphics2D.setTransform(this.transform);
        }
        if (this.composite != null) {
            graphics2D.setComposite(this.composite);
        }
        if (this.paint != null) {
            graphics2D.setPaint(this.paint);
        }
        if (this.text != null) {
            graphics2D.drawString(this.text, 10, 100);
        }
        graphics2D.dispose();
        return bufferedImage2;
    }
}

