package dev.luvbeeq.animation.bezier;

import lombok.Getter;

@Getter
public abstract class Bezier {
    private final Point start = new Point(0.0D, 0.0D);
    private final Point end = new Point(1.0D, 1.0D);
    private Point point2;
    private Point point3;

    public Bezier(Point point2, Point point3) {
        this.setPoint2(point2);
        this.setPoint3(point3);
    }

    public Bezier() {
    }

    public abstract double getValue(double var1);

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public void setPoint3(Point point3) {
        this.point3 = point3;
    }

}
 