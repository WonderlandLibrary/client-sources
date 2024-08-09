package dev.luvbeeq.animation.bezier.list;

import dev.luvbeeq.animation.bezier.Bezier;
import dev.luvbeeq.animation.bezier.Point;

@SuppressWarnings("unused")
public class CubicBezier extends Bezier {
    public double getValue(double t) {
        double dt = 1.0D - t;
        double dt2 = dt * dt;
        double t2 = t * t;
        Point temp = this.getPoint2().copy();
        return this.getStart().copy().scale(dt2, dt).add(temp.scale(3.0D * dt2 * t)).add(temp.set(this.getPoint3()).scale(3.0D * dt * t2)).add(temp.set(this.getEnd()).scale(t2 * t)).getY();
    }

    public static class Builder {
        private CubicBezier bezier = new CubicBezier();

        public Builder(CubicBezier bezier) {
            this.bezier = bezier;
        }

        public Builder() {
        }

        public CubicBezier.Builder setPoint2(Point point) {
            this.bezier.setPoint2(point);
            return this;
        }

        public CubicBezier.Builder setPoint3(Point point) {
            this.bezier.setPoint3(point);
            return this;
        }

        public CubicBezier build() {
            return this.bezier;
        }
    }
}
 