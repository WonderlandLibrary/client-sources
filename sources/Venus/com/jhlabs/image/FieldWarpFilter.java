/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.ImageMath;
import com.jhlabs.image.TransformFilter;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class FieldWarpFilter
extends TransformFilter {
    private float amount = 1.0f;
    private float power = 1.0f;
    private float strength = 2.0f;
    private Line[] inLines;
    private Line[] outLines;
    private Line[] intermediateLines;
    private float width;
    private float height;

    public void setAmount(float f) {
        this.amount = f;
    }

    public float getAmount() {
        return this.amount;
    }

    public void setPower(float f) {
        this.power = f;
    }

    public float getPower() {
        return this.power;
    }

    public void setStrength(float f) {
        this.strength = f;
    }

    public float getStrength() {
        return this.strength;
    }

    public void setInLines(Line[] lineArray) {
        this.inLines = lineArray;
    }

    public Line[] getInLines() {
        return this.inLines;
    }

    public void setOutLines(Line[] lineArray) {
        this.outLines = lineArray;
    }

    public Line[] getOutLines() {
        return this.outLines;
    }

    protected void transform(int n, int n2, Point point) {
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.001f;
        float f5 = 1.5f * this.strength + 0.5f;
        float f6 = this.power;
        float f7 = 0.0f;
        float f8 = 0.0f;
        float f9 = 0.0f;
        for (int i = 0; i < this.inLines.length; ++i) {
            float f10;
            Line line = this.inLines[i];
            Line line2 = this.intermediateLines[i];
            float f11 = n - line2.x1;
            float f12 = n2 - line2.y1;
            f3 = (f11 * (float)line2.dx + f12 * (float)line2.dy) / line2.lengthSquared;
            float f13 = (f12 * (float)line2.dx - f11 * (float)line2.dy) / line2.length;
            if (f3 <= 0.0f) {
                f10 = (float)Math.sqrt(f11 * f11 + f12 * f12);
            } else if (f3 >= 1.0f) {
                f11 = n - line2.x2;
                f12 = n2 - line2.y2;
                f10 = (float)Math.sqrt(f11 * f11 + f12 * f12);
            } else {
                f10 = f13 >= 0.0f ? f13 : -f13;
            }
            f = (float)line.x1 + f3 * (float)line.dx - f13 * (float)line.dy / line.length;
            f2 = (float)line.y1 + f3 * (float)line.dy + f13 * (float)line.dx / line.length;
            float f14 = (float)Math.pow(Math.pow(line2.length, f6) / (double)(f4 + f10), f5);
            f8 += (f - (float)n) * f14;
            f9 += (f2 - (float)n2) * f14;
            f7 += f14;
        }
        fArray[0] = (float)n + f8 / f7 + 0.5f;
        fArray[1] = (float)n2 + f9 / f7 + 0.5f;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        this.width = this.width;
        this.height = this.height;
        if (this.inLines != null && this.outLines != null) {
            this.intermediateLines = new Line[this.inLines.length];
            for (int i = 0; i < this.inLines.length; ++i) {
                Line line = this.intermediateLines[i] = new Line(ImageMath.lerp(this.amount, this.inLines[i].x1, this.outLines[i].x1), ImageMath.lerp(this.amount, this.inLines[i].y1, this.outLines[i].y1), ImageMath.lerp(this.amount, this.inLines[i].x2, this.outLines[i].x2), ImageMath.lerp(this.amount, this.inLines[i].y2, this.outLines[i].y2));
                line.setup();
                this.inLines[i].setup();
            }
            bufferedImage2 = super.filter(bufferedImage, bufferedImage2);
            this.intermediateLines = null;
            return bufferedImage2;
        }
        return bufferedImage;
    }

    public String toString() {
        return "Distort/Field Warp...";
    }

    public static class Line {
        public int x1;
        public int y1;
        public int x2;
        public int y2;
        public int dx;
        public int dy;
        public float length;
        public float lengthSquared;

        public Line(int n, int n2, int n3, int n4) {
            this.x1 = n;
            this.y1 = n2;
            this.x2 = n3;
            this.y2 = n4;
        }

        public void setup() {
            this.dx = this.x2 - this.x1;
            this.dy = this.y2 - this.y1;
            this.lengthSquared = this.dx * this.dx + this.dy * this.dy;
            this.length = (float)Math.sqrt(this.lengthSquared);
        }
    }
}

