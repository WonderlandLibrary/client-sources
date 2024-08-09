/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;

public abstract class AbstractBufferedImageOp
implements BufferedImageOp,
Cloneable {
    @Override
    public BufferedImage createCompatibleDestImage(BufferedImage bufferedImage, ColorModel colorModel) {
        if (colorModel == null) {
            colorModel = bufferedImage.getColorModel();
        }
        return new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(bufferedImage.getWidth(), bufferedImage.getHeight()), colorModel.isAlphaPremultiplied(), null);
    }

    @Override
    public Rectangle2D getBounds2D(BufferedImage bufferedImage) {
        return new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

    @Override
    public Point2D getPoint2D(Point2D point2D, Point2D point2D2) {
        if (point2D2 == null) {
            point2D2 = new Point2D.Double();
        }
        point2D2.setLocation(point2D.getX(), point2D.getY());
        return point2D2;
    }

    @Override
    public RenderingHints getRenderingHints() {
        return null;
    }

    public int[] getRGB(BufferedImage bufferedImage, int n, int n2, int n3, int n4, int[] nArray) {
        int n5 = bufferedImage.getType();
        if (n5 == 2 || n5 == 1) {
            return (int[])bufferedImage.getRaster().getDataElements(n, n2, n3, n4, nArray);
        }
        return bufferedImage.getRGB(n, n2, n3, n4, nArray, 0, n3);
    }

    public void setRGB(BufferedImage bufferedImage, int n, int n2, int n3, int n4, int[] nArray) {
        int n5 = bufferedImage.getType();
        if (n5 == 2 || n5 == 1) {
            bufferedImage.getRaster().setDataElements(n, n2, n3, n4, nArray);
        } else {
            bufferedImage.setRGB(n, n2, n3, n4, nArray, 0, n3);
        }
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }
}

