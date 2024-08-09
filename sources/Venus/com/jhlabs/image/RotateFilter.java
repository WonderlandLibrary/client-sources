/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.image;

import com.jhlabs.image.TransformFilter;
import java.awt.Point;
import java.awt.Rectangle;

public class RotateFilter
extends TransformFilter {
    private float angle;
    private float cos;
    private float sin;
    private boolean resize = true;

    public RotateFilter() {
        this((float)Math.PI);
    }

    public RotateFilter(float f) {
        this(f, true);
    }

    public RotateFilter(float f, boolean bl) {
        this.setAngle(f);
        this.resize = bl;
    }

    public void setAngle(float f) {
        this.angle = f;
        this.cos = (float)Math.cos(this.angle);
        this.sin = (float)Math.sin(this.angle);
    }

    public float getAngle() {
        return this.angle;
    }

    @Override
    protected void transformSpace(Rectangle rectangle) {
        if (this.resize) {
            Point point = new Point(0, 0);
            int n = Integer.MAX_VALUE;
            int n2 = Integer.MAX_VALUE;
            int n3 = Integer.MIN_VALUE;
            int n4 = Integer.MIN_VALUE;
            int n5 = rectangle.width;
            int n6 = rectangle.height;
            int n7 = rectangle.x;
            int n8 = rectangle.y;
            for (int i = 0; i < 4; ++i) {
                switch (i) {
                    case 0: {
                        this.transform(n7, n8, point);
                        break;
                    }
                    case 1: {
                        this.transform(n7 + n5, n8, point);
                        break;
                    }
                    case 2: {
                        this.transform(n7, n8 + n6, point);
                        break;
                    }
                    case 3: {
                        this.transform(n7 + n5, n8 + n6, point);
                    }
                }
                n = Math.min(n, point.x);
                n2 = Math.min(n2, point.y);
                n3 = Math.max(n3, point.x);
                n4 = Math.max(n4, point.y);
            }
            rectangle.x = n;
            rectangle.y = n2;
            rectangle.width = n3 - rectangle.x;
            rectangle.height = n4 - rectangle.y;
        }
    }

    private void transform(int n, int n2, Point point) {
        point.x = (int)((float)n * this.cos + (float)n2 * this.sin);
        point.y = (int)((float)n2 * this.cos - (float)n * this.sin);
    }

    @Override
    protected void transformInverse(int n, int n2, float[] fArray) {
        fArray[0] = (float)n * this.cos - (float)n2 * this.sin;
        fArray[1] = (float)n2 * this.cos + (float)n * this.sin;
    }

    public String toString() {
        return "Rotate " + (int)((double)(this.angle * 180.0f) / Math.PI);
    }
}

