/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.hogoshi.bezier.list;

import ru.hogoshi.bezier.Bezier;
import ru.hogoshi.bezier.Point;

public class CubicBezier
extends Bezier {
    @Override
    public double getValue(double d) {
        double d2 = 1.0 - d;
        double d3 = d2 * d2;
        double d4 = d * d;
        Point point = this.getPoint2().copy();
        return this.getStart().copy().scale(d3, d2).add(point.scale(3.0 * d3 * d)).add(point.set(this.getPoint3()).scale(3.0 * d2 * d4)).add(point.set(this.getEnd()).scale(d4 * d)).getY();
    }

    public static class Builder {
        private CubicBezier bezier = new CubicBezier();

        public Builder(CubicBezier cubicBezier) {
            this.bezier = cubicBezier;
        }

        public Builder() {
        }

        public Builder setPoint2(Point point) {
            this.bezier.setPoint2(point);
            return this;
        }

        public Builder setPoint3(Point point) {
            this.bezier.setPoint3(point);
            return this;
        }

        public CubicBezier build() {
            return this.bezier;
        }
    }
}

