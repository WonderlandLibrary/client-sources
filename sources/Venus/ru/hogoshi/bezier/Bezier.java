/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.hogoshi.bezier;

import ru.hogoshi.bezier.Point;

public abstract class Bezier {
    private final Point start = new Point(0.0, 0.0);
    private final Point end = new Point(1.0, 1.0);
    private Point point2;
    private Point point3;

    public Bezier(Point point, Point point2) {
        this.setPoint2(point);
        this.setPoint3(point2);
    }

    public Bezier() {
    }

    public abstract double getValue(double var1);

    public Point getStart() {
        return this.start;
    }

    public Point getEnd() {
        return this.end;
    }

    public void setPoint2(Point point) {
        this.point2 = point;
    }

    public void setPoint3(Point point) {
        this.point3 = point;
    }

    public Point getPoint2() {
        return this.point2;
    }

    public Point getPoint3() {
        return this.point3;
    }
}

