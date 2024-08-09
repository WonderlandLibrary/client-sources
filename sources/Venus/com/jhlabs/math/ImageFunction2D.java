/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import com.jhlabs.math.Function2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;

public class ImageFunction2D
implements Function2D {
    public static final int ZERO = 0;
    public static final int CLAMP = 1;
    public static final int WRAP = 2;
    protected int[] pixels;
    protected int width;
    protected int height;
    protected int edgeAction = 0;
    protected boolean alpha = false;

    public ImageFunction2D(BufferedImage bufferedImage) {
        this(bufferedImage, false);
    }

    public ImageFunction2D(BufferedImage bufferedImage, boolean bl) {
        this(bufferedImage, 0, bl);
    }

    public ImageFunction2D(BufferedImage bufferedImage, int n, boolean bl) {
        this.init(this.getRGB(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null), bufferedImage.getWidth(), bufferedImage.getHeight(), n, bl);
    }

    public ImageFunction2D(int[] nArray, int n, int n2, int n3, boolean bl) {
        this.init(nArray, n, n2, n3, bl);
    }

    public ImageFunction2D(Image image) {
        this(image, 0, false);
    }

    public ImageFunction2D(Image image, int n, boolean bl) {
        PixelGrabber pixelGrabber = new PixelGrabber(image, 0, 0, -1, -1, null, 0, -1);
        try {
            pixelGrabber.grabPixels();
        } catch (InterruptedException interruptedException) {
            throw new RuntimeException("interrupted waiting for pixels!");
        }
        if ((pixelGrabber.status() & 0x80) != 0) {
            throw new RuntimeException("image fetch aborted");
        }
        this.init((int[])pixelGrabber.getPixels(), pixelGrabber.getWidth(), pixelGrabber.getHeight(), n, bl);
    }

    public int[] getRGB(BufferedImage bufferedImage, int n, int n2, int n3, int n4, int[] nArray) {
        int n5 = bufferedImage.getType();
        if (n5 == 2 || n5 == 1) {
            return (int[])bufferedImage.getRaster().getDataElements(n, n2, n3, n4, nArray);
        }
        return bufferedImage.getRGB(n, n2, n3, n4, nArray, 0, n3);
    }

    public void init(int[] nArray, int n, int n2, int n3, boolean bl) {
        this.pixels = nArray;
        this.width = n;
        this.height = n2;
        this.edgeAction = n3;
        this.alpha = bl;
    }

    @Override
    public float evaluate(float f, float f2) {
        int n = (int)f;
        int n2 = (int)f2;
        if (this.edgeAction == 2) {
            n = ImageMath.mod(n, this.width);
            n2 = ImageMath.mod(n2, this.height);
        } else if (n < 0 || n2 < 0 || n >= this.width || n2 >= this.height) {
            if (this.edgeAction == 0) {
                return 0.0f;
            }
            if (n < 0) {
                n = 0;
            } else if (n >= this.width) {
                n = this.width - 1;
            }
            if (n2 < 0) {
                n2 = 0;
            } else if (n2 >= this.height) {
                n2 = this.height - 1;
            }
        }
        return this.alpha ? (float)(this.pixels[n2 * this.width + n] >> 24 & 0xFF) / 255.0f : (float)PixelUtils.brightness(this.pixels[n2 * this.width + n]) / 255.0f;
    }

    public void setEdgeAction(int n) {
        this.edgeAction = n;
    }

    public int getEdgeAction() {
        return this.edgeAction;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int[] getPixels() {
        return this.pixels;
    }
}

