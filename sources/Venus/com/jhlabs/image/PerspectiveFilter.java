/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class PerspectiveFilter
extends TransformFilter {
    private float x0;
    private float y0;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float x3;
    private float y3;
    private float dx1;
    private float dy1;
    private float dx2;
    private float dy2;
    private float dx3;
    private float dy3;
    private float A;
    private float B;
    private float C;
    private float D;
    private float E;
    private float F;
    private float G;
    private float H;
    private float I;
    private float a11;
    private float a12;
    private float a13;
    private float a21;
    private float a22;
    private float a23;
    private float a31;
    private float a32;
    private float a33;
    private boolean scaled;
    private boolean clip = false;

    public PerspectiveFilter() {
        this(0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f);
    }

    public PerspectiveFilter(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.unitSquareToQuad(f, f2, f3, f4, f5, f6, f7, f8);
    }

    public void setClip(boolean bl) {
        this.clip = bl;
    }

    public boolean getClip() {
        return this.clip;
    }

    public void setCorners(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.unitSquareToQuad(f, f2, f3, f4, f5, f6, f7, f8);
        this.scaled = true;
    }

    public void unitSquareToQuad(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.x0 = f;
        this.y0 = f2;
        this.x1 = f3;
        this.y1 = f4;
        this.x2 = f5;
        this.y2 = f6;
        this.x3 = f7;
        this.y3 = f8;
        this.dx1 = f3 - f5;
        this.dy1 = f4 - f6;
        this.dx2 = f7 - f5;
        this.dy2 = f8 - f6;
        this.dx3 = f - f3 + f5 - f7;
        this.dy3 = f2 - f4 + f6 - f8;
        if (this.dx3 == 0.0f && this.dy3 == 0.0f) {
            this.a11 = f3 - f;
            this.a21 = f5 - f3;
            this.a31 = f;
            this.a12 = f4 - f2;
            this.a22 = f6 - f4;
            this.a32 = f2;
            this.a23 = 0.0f;
            this.a13 = 0.0f;
        } else {
            this.a13 = (this.dx3 * this.dy2 - this.dx2 * this.dy3) / (this.dx1 * this.dy2 - this.dy1 * this.dx2);
            this.a23 = (this.dx1 * this.dy3 - this.dy1 * this.dx3) / (this.dx1 * this.dy2 - this.dy1 * this.dx2);
            this.a11 = f3 - f + this.a13 * f3;
            this.a21 = f7 - f + this.a23 * f7;
            this.a31 = f;
            this.a12 = f4 - f2 + this.a13 * f4;
            this.a22 = f8 - f2 + this.a23 * f8;
            this.a32 = f2;
        }
        this.a33 = 1.0f;
        this.scaled = false;
    }

    public void quadToUnitSquare(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.unitSquareToQuad(f, f2, f3, f4, f5, f6, f7, f8);
        float f9 = this.a22 * this.a33 - this.a32 * this.a23;
        float f10 = this.a32 * this.a13 - this.a12 * this.a33;
        float f11 = this.a12 * this.a23 - this.a22 * this.a13;
        float f12 = this.a31 * this.a23 - this.a21 * this.a33;
        float f13 = this.a11 * this.a33 - this.a31 * this.a13;
        float f14 = this.a21 * this.a13 - this.a11 * this.a23;
        float f15 = this.a21 * this.a32 - this.a31 * this.a22;
        float f16 = this.a31 * this.a12 - this.a11 * this.a32;
        float f17 = this.a11 * this.a22 - this.a21 * this.a12;
        float f18 = 1.0f / f17;
        this.a11 = f9 * f18;
        this.a21 = f12 * f18;
        this.a31 = f15 * f18;
        this.a12 = f10 * f18;
        this.a22 = f13 * f18;
        this.a32 = f16 * f18;
        this.a13 = f11 * f18;
        this.a23 = f14 * f18;
        this.a33 = 1.0f;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        this.A = this.a22 * this.a33 - this.a32 * this.a23;
        this.B = this.a31 * this.a23 - this.a21 * this.a33;
        this.C = this.a21 * this.a32 - this.a31 * this.a22;
        this.D = this.a32 * this.a13 - this.a12 * this.a33;
        this.E = this.a11 * this.a33 - this.a31 * this.a13;
        this.F = this.a31 * this.a12 - this.a11 * this.a32;
        this.G = this.a12 * this.a23 - this.a22 * this.a13;
        this.H = this.a21 * this.a13 - this.a11 * this.a23;
        this.I = this.a11 * this.a22 - this.a21 * this.a12;
        if (!this.scaled) {
            int n = bufferedImage.getWidth();
            int n2 = bufferedImage.getHeight();
            float f = 1.0f / (float)n;
            float f2 = 1.0f / (float)n2;
            this.A *= f;
            this.D *= f;
            this.G *= f;
            this.B *= f2;
            this.E *= f2;
            this.H *= f2;
        }
        return super.filter(bufferedImage, bufferedImage2);
    }

    @Override
    protected void transformSpace(Rectangle rectangle) {
        if (this.scaled) {
            rectangle.x = (int)Math.min(Math.min(this.x0, this.x1), Math.min(this.x2, this.x3));
            rectangle.y = (int)Math.min(Math.min(this.y0, this.y1), Math.min(this.y2, this.y3));
            rectangle.width = (int)Math.max(Math.max(this.x0, this.x1), Math.max(this.x2, this.x3)) - rectangle.x;
            rectangle.height = (int)Math.max(Math.max(this.y0, this.y1), Math.max(this.y2, this.y3)) - rectangle.y;
            return;
        }
        if (!this.clip) {
            float f = (float)rectangle.getWidth();
            float f2 = (float)rectangle.getHeight();
            Rectangle rectangle2 = new Rectangle();
            rectangle2.add(this.getPoint2D(new Point2D.Float(0.0f, 0.0f), null));
            rectangle2.add(this.getPoint2D(new Point2D.Float(f, 0.0f), null));
            rectangle2.add(this.getPoint2D(new Point2D.Float(0.0f, f2), null));
            rectangle2.add(this.getPoint2D(new Point2D.Float(f, f2), null));
            rectangle.setRect(rectangle2);
        }
    }

    public float getOriginX() {
        return this.x0 - (float)((int)Math.min(Math.min(this.x0, this.x1), Math.min(this.x2, this.x3)));
    }

    public float getOriginY() {
        return this.y0 - (float)((int)Math.min(Math.min(this.y0, this.y1), Math.min(this.y2, this.y3)));
    }

    @Override
    public Rectangle2D getBounds2D(BufferedImage bufferedImage) {
        if (this.clip) {
            return new Rectangle(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        }
        float f = bufferedImage.getWidth();
        float f2 = bufferedImage.getHeight();
        Rectangle2D.Float float_ = new Rectangle2D.Float();
        float_.add(this.getPoint2D(new Point2D.Float(0.0f, 0.0f), null));
        float_.add(this.getPoint2D(new Point2D.Float(f, 0.0f), null));
        float_.add(this.getPoint2D(new Point2D.Float(0.0f, f2), null));
        float_.add(this.getPoint2D(new Point2D.Float(f, f2), null));
        return float_;
    }

    @Override
    public Point2D getPoint2D(Point2D point2D, Point2D point2D2) {
        if (point2D2 == null) {
            point2D2 = new Point2D.Float();
        }
        float f = (float)point2D.getX();
        float f2 = (float)point2D.getY();
        float f3 = 1.0f / (f * this.a13 + f2 * this.a23 + this.a33);
        point2D2.setLocation((f * this.a11 + f2 * this.a21 + this.a31) * f3, (f * this.a12 + f2 * this.a22 + this.a32) * f3);
        return point2D2;
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        fArray[0] = (float)this.originalSpace.width * (this.A * (float)n + this.B * (float)n2 + this.C) / (this.G * (float)n + this.H * (float)n2 + this.I);
        fArray[1] = (float)this.originalSpace.height * (this.D * (float)n + this.E * (float)n2 + this.F) / (this.G * (float)n + this.H * (float)n2 + this.I);
    }

    public String toString() {
        return "Distort/Perspective...";
    }
}

