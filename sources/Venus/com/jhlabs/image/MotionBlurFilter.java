/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import com.jhlabs.image.ImageMath;
import com.jhlabs.image.PixelUtils;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class MotionBlurFilter
extends AbstractBufferedImageOp {
    private float angle = 0.0f;
    private float falloff = 1.0f;
    private float distance = 1.0f;
    private float zoom = 0.0f;
    private float rotation = 0.0f;
    private boolean wrapEdges = false;
    private boolean premultiplyAlpha = true;

    public MotionBlurFilter() {
    }

    public MotionBlurFilter(float f, float f2, float f3, float f4) {
        this.distance = f;
        this.angle = f2;
        this.rotation = f3;
        this.zoom = f4;
    }

    public void setAngle(float f) {
        this.angle = f;
    }

    public float getAngle() {
        return this.angle;
    }

    public void setDistance(float f) {
        this.distance = f;
    }

    public float getDistance() {
        return this.distance;
    }

    public void setRotation(float f) {
        this.rotation = f;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setZoom(float f) {
        this.zoom = f;
    }

    public float getZoom() {
        return this.zoom;
    }

    public void setWrapEdges(boolean bl) {
        this.wrapEdges = bl;
    }

    public boolean getWrapEdges() {
        return this.wrapEdges;
    }

    public void setPremultiplyAlpha(boolean bl) {
        this.premultiplyAlpha = bl;
    }

    public boolean getPremultiplyAlpha() {
        return this.premultiplyAlpha;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        int n = bufferedImage.getWidth();
        int n2 = bufferedImage.getHeight();
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        int[] nArray = new int[n * n2];
        int[] nArray2 = new int[n * n2];
        this.getRGB(bufferedImage, 0, 0, n, n2, nArray);
        float f = (float)Math.sin(this.angle);
        float f2 = (float)Math.cos(this.angle);
        int n3 = n / 2;
        int n4 = n2 / 2;
        int n5 = 0;
        float f3 = (float)Math.sqrt(n3 * n3 + n4 * n4);
        float f4 = (float)((double)this.distance * Math.cos(this.angle));
        float f5 = (float)((double)this.distance * -Math.sin(this.angle));
        float f6 = this.distance + Math.abs(this.rotation * f3) + this.zoom * f3;
        int n6 = (int)f6;
        AffineTransform affineTransform = new AffineTransform();
        Point2D.Float float_ = new Point2D.Float();
        if (this.premultiplyAlpha) {
            ImageMath.premultiply(nArray, 0, nArray.length);
        }
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                int n7 = 0;
                int n8 = 0;
                int n9 = 0;
                int n10 = 0;
                int n11 = 0;
                for (int k = 0; k < n6; ++k) {
                    int n12 = j;
                    int n13 = i;
                    float f7 = (float)k / (float)n6;
                    float_.x = j;
                    float_.y = i;
                    affineTransform.setToIdentity();
                    affineTransform.translate((float)n3 + f7 * f4, (float)n4 + f7 * f5);
                    float f8 = 1.0f - this.zoom * f7;
                    affineTransform.scale(f8, f8);
                    if (this.rotation != 0.0f) {
                        affineTransform.rotate(-this.rotation * f7);
                    }
                    affineTransform.translate(-n3, -n4);
                    affineTransform.transform(float_, float_);
                    n12 = (int)float_.x;
                    n13 = (int)float_.y;
                    if (n12 < 0 || n12 >= n) {
                        if (!this.wrapEdges) break;
                        n12 = ImageMath.mod(n12, n);
                    }
                    if (n13 < 0 || n13 >= n2) {
                        if (!this.wrapEdges) break;
                        n13 = ImageMath.mod(n13, n2);
                    }
                    ++n11;
                    int n14 = nArray[n13 * n + n12];
                    n7 += n14 >> 24 & 0xFF;
                    n8 += n14 >> 16 & 0xFF;
                    n9 += n14 >> 8 & 0xFF;
                    n10 += n14 & 0xFF;
                }
                if (n11 == 0) {
                    nArray2[n5] = nArray[n5];
                } else {
                    n7 = PixelUtils.clamp(n7 / n11);
                    n8 = PixelUtils.clamp(n8 / n11);
                    n9 = PixelUtils.clamp(n9 / n11);
                    n10 = PixelUtils.clamp(n10 / n11);
                    nArray2[n5] = n7 << 24 | n8 << 16 | n9 << 8 | n10;
                }
                ++n5;
            }
        }
        if (this.premultiplyAlpha) {
            ImageMath.unpremultiply(nArray2, 0, nArray.length);
        }
        this.setRGB(bufferedImage2, 0, 0, n, n2, nArray2);
        return bufferedImage2;
    }

    public String toString() {
        return "Blur/Motion Blur...";
    }
}

