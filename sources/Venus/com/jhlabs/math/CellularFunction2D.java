/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.jhlabs.math;

import com.jhlabs.math.Function2D;
import java.util.Random;

public class CellularFunction2D
implements Function2D {
    public float distancePower = 2.0f;
    public boolean cells = false;
    public boolean angular = false;
    private float[] coefficients = new float[]{1.0f, 0.0f, 0.0f, 0.0f};
    private Random random = new Random();
    private Point[] results = new Point[2];

    public CellularFunction2D() {
        for (int i = 0; i < this.results.length; ++i) {
            this.results[i] = new Point(this);
        }
    }

    public void setCoefficient(int n, float f) {
        this.coefficients[n] = f;
    }

    public float getCoefficient(int n) {
        return this.coefficients[n];
    }

    private float checkCube(float f, float f2, int n, int n2, Point[] pointArray) {
        this.random.setSeed(571 * n + 23 * n2);
        int n3 = 3 + this.random.nextInt() % 4;
        n3 = 4;
        block0: for (int i = 0; i < n3; ++i) {
            float f3 = this.random.nextFloat();
            float f4 = this.random.nextFloat();
            float f5 = Math.abs(f - f3);
            float f6 = Math.abs(f2 - f4);
            float f7 = this.distancePower == 1.0f ? f5 + f6 : (this.distancePower == 2.0f ? (float)Math.sqrt(f5 * f5 + f6 * f6) : (float)Math.pow(Math.pow(f5, this.distancePower) + Math.pow(f6, this.distancePower), 1.0f / this.distancePower));
            for (int j = 0; j < pointArray.length; ++j) {
                Point point;
                if ((double)pointArray[j].distance == Double.POSITIVE_INFINITY) {
                    point = pointArray[j];
                    point.distance = f7;
                    point.x = f3;
                    point.y = f4;
                    pointArray[j] = point;
                    continue block0;
                }
                if (!(f7 < pointArray[j].distance)) continue;
                point = pointArray[pointArray.length - 1];
                for (int k = pointArray.length - 1; k > j; --k) {
                    pointArray[k] = pointArray[k - 1];
                }
                point.distance = f7;
                point.x = f3;
                point.y = f4;
                pointArray[j] = point;
                continue block0;
            }
        }
        return pointArray[0].distance;
    }

    @Override
    public float evaluate(float f, float f2) {
        int n;
        for (n = 0; n < this.results.length; ++n) {
            this.results[n].distance = Float.POSITIVE_INFINITY;
        }
        n = (int)f;
        float f3 = f - (float)n;
        int n2 = (int)f2;
        float f4 = f2 - (float)n2;
        float f5 = this.checkCube(f3, f4, n, n2, this.results);
        if (f5 > f4) {
            f5 = this.checkCube(f3, f4 + 1.0f, n, n2 - 1, this.results);
        }
        if (f5 > 1.0f - f4) {
            f5 = this.checkCube(f3, f4 - 1.0f, n, n2 + 1, this.results);
        }
        if (f5 > f3) {
            this.checkCube(f3 + 1.0f, f4, n - 1, n2, this.results);
            if (f5 > f4) {
                f5 = this.checkCube(f3 + 1.0f, f4 + 1.0f, n - 1, n2 - 1, this.results);
            }
            if (f5 > 1.0f - f4) {
                f5 = this.checkCube(f3 + 1.0f, f4 - 1.0f, n - 1, n2 + 1, this.results);
            }
        }
        if (f5 > 1.0f - f3) {
            f5 = this.checkCube(f3 - 1.0f, f4, n + 1, n2, this.results);
            if (f5 > f4) {
                f5 = this.checkCube(f3 - 1.0f, f4 + 1.0f, n + 1, n2 - 1, this.results);
            }
            if (f5 > 1.0f - f4) {
                f5 = this.checkCube(f3 - 1.0f, f4 - 1.0f, n + 1, n2 + 1, this.results);
            }
        }
        float f6 = 0.0f;
        for (int i = 0; i < 2; ++i) {
            f6 += this.coefficients[i] * this.results[i].distance;
        }
        if (this.angular) {
            f6 = (float)((double)f6 + (Math.atan2(f4 - this.results[0].y, f3 - this.results[0].x) / (Math.PI * 2) + 0.5));
        }
        return f6;
    }

    class Point {
        int index;
        float x;
        float y;
        float distance;
        final CellularFunction2D this$0;

        Point(CellularFunction2D cellularFunction2D) {
            this.this$0 = cellularFunction2D;
        }
    }
}

