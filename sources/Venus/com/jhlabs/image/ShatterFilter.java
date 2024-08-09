/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.AbstractBufferedImageOp;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ShatterFilter
extends AbstractBufferedImageOp {
    private float centreX = 0.5f;
    private float centreY = 0.5f;
    private float distance;
    private float transition;
    private float rotation;
    private float zoom;
    private float startAlpha = 1.0f;
    private float endAlpha = 1.0f;
    private int iterations = 5;
    private int tile;

    public void setTransition(float f) {
        this.transition = f;
    }

    public float getTransition() {
        return this.transition;
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

    public void setStartAlpha(float f) {
        this.startAlpha = f;
    }

    public float getStartAlpha() {
        return this.startAlpha;
    }

    public void setEndAlpha(float f) {
        this.endAlpha = f;
    }

    public float getEndAlpha() {
        return this.endAlpha;
    }

    public void setCentreX(float f) {
        this.centreX = f;
    }

    public float getCentreX() {
        return this.centreX;
    }

    public void setCentreY(float f) {
        this.centreY = f;
    }

    public float getCentreY() {
        return this.centreY;
    }

    public void setCentre(Point2D point2D) {
        this.centreX = (float)point2D.getX();
        this.centreY = (float)point2D.getY();
    }

    public Point2D getCentre() {
        return new Point2D.Float(this.centreX, this.centreY);
    }

    public void setIterations(int n) {
        this.iterations = n;
    }

    public int getIterations() {
        return this.iterations;
    }

    public void setTile(int n) {
        this.tile = n;
    }

    public int getTile() {
        return this.tile;
    }

    @Override
    public BufferedImage filter(BufferedImage bufferedImage, BufferedImage bufferedImage2) {
        Rectangle rectangle;
        int n;
        if (bufferedImage2 == null) {
            bufferedImage2 = this.createCompatibleDestImage(bufferedImage, null);
        }
        float f = bufferedImage.getWidth();
        float f2 = bufferedImage.getHeight();
        float f3 = (float)bufferedImage.getWidth() * this.centreX;
        float f4 = (float)bufferedImage.getHeight() * this.centreY;
        float f5 = (float)Math.sqrt(f3 * f3 + f4 * f4);
        int n2 = this.iterations * this.iterations;
        Tile[] tileArray = new Tile[n2];
        float[] fArray = new float[n2];
        float[] fArray2 = new float[n2];
        float[] fArray3 = new float[n2];
        Graphics2D graphics2D = bufferedImage2.createGraphics();
        Random random2 = new Random(0L);
        float f6 = 0.0f;
        float f7 = 0.0f;
        for (n = 0; n < this.iterations; ++n) {
            int n3 = (int)f2 * n / this.iterations;
            int n4 = (int)f2 * (n + 1) / this.iterations;
            for (int i = 0; i < this.iterations; ++i) {
                int n5 = n * this.iterations + i;
                int n6 = (int)f * i / this.iterations;
                int n7 = (int)f * (i + 1) / this.iterations;
                fArray[n5] = (float)this.tile * random2.nextFloat();
                fArray2[n5] = (float)this.tile * random2.nextFloat();
                fArray[n5] = 0.0f;
                fArray2[n5] = 0.0f;
                fArray3[n5] = (float)this.tile * (2.0f * random2.nextFloat() - 1.0f);
                rectangle = new Rectangle(n6, n3, n7 - n6, n4 - n3);
                tileArray[n5] = new Tile();
                tileArray[n5].shape = rectangle;
                tileArray[n5].x = (float)(n6 + n7) * 0.5f;
                tileArray[n5].y = (float)(n3 + n4) * 0.5f;
                tileArray[n5].vx = f - (f3 - (float)i);
                tileArray[n5].vy = f2 - (f4 - (float)n);
                tileArray[n5].w = n7 - n6;
                tileArray[n5].h = n4 - n3;
            }
        }
        for (n = 0; n < n2; ++n) {
            float f8 = (float)n / (float)n2;
            double d = (double)(f8 * 2.0f) * Math.PI;
            float f9 = this.transition * f * (float)Math.cos(d);
            float f10 = this.transition * f2 * (float)Math.sin(d);
            Tile tile = tileArray[n];
            rectangle = tile.shape.getBounds();
            AffineTransform affineTransform = graphics2D.getTransform();
            f9 = tile.x + this.transition * tile.vx;
            f10 = tile.y + this.transition * tile.vy;
            graphics2D.translate(f9, f10);
            graphics2D.rotate(this.transition * fArray3[n]);
            graphics2D.setColor(Color.getHSBColor(f8, 1.0f, 1.0f));
            Shape shape = graphics2D.getClip();
            graphics2D.clip(tile.shape);
            graphics2D.drawImage((Image)bufferedImage, 0, 0, null);
            graphics2D.setClip(shape);
            graphics2D.setTransform(affineTransform);
        }
        graphics2D.dispose();
        return bufferedImage2;
    }

    public String toString() {
        return "Transition/Shatter...";
    }

    static class Tile {
        float x;
        float y;
        float vx;
        float vy;
        float w;
        float h;
        float rotation;
        Shape shape;

        Tile() {
        }
    }
}

