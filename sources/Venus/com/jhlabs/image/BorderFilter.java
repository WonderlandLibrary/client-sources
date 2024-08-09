/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class BorderFilter
extends AbstractBufferedImageOp {
    private int leftBorder;
    private int rightBorder;
    private int topBorder;
    private int bottomBorder;
    private Paint borderPaint;

    public BorderFilter() {
    }

    public BorderFilter(int n, int n2, int n3, int n4, Paint paint) {
        this.leftBorder = n;
        this.topBorder = n2;
        this.rightBorder = n3;
        this.bottomBorder = n4;
        this.borderPaint = paint;
    }

    public void setLeftBorder(int n) {
        this.leftBorder = n;
    }

    public int getLeftBorder() {
        return this.leftBorder;
    }

    public void setRightBorder(int n) {
        this.rightBorder = n;
    }

    public int getRightBorder() {
        return this.rightBorder;
    }

    public void setTopBorder(int n) {
        this.topBorder = n;
    }

    public int getTopBorder() {
        return this.topBorder;
    }

    public void setBottomBorder(int n) {
        this.bottomBorder = n;
    }

    public int getBottomBorder() {
        return this.bottomBorder;
    }

    public void setBorderPaint(Paint paint) {
        this.borderPaint = paint;
    }

    public Paint getBorderPaint() {
        return this.borderPaint;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            bufferedImage2 = new BufferedImage(n + this.leftBorder + this.rightBorder, n2 + this.topBorder + this.bottomBorder, bufferedImage.getType());
        }
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        if (this.borderPaint != null) {
            graphics2D.setPaint(this.borderPaint);
            if (this.leftBorder > 0) {
                graphics2D.fillRect(0, 0, this.leftBorder, n2);
            }
            if (this.rightBorder > 0) {
                graphics2D.fillRect(n - this.rightBorder, 0, this.rightBorder, n2);
            }
            if (this.topBorder > 0) {
                graphics2D.fillRect(this.leftBorder, 0, n - this.leftBorder - this.rightBorder, this.topBorder);
            }
            if (this.bottomBorder > 0) {
                graphics2D.fillRect(this.leftBorder, n2 - this.bottomBorder, n - this.leftBorder - this.rightBorder, this.bottomBorder);
            }
        }
        graphics2D.drawRenderedImage(bufferedImage, AffineTransform.getTranslateInstance(this.leftBorder, this.rightBorder));
        graphics2D.dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Distort/Border...";
    }
}

