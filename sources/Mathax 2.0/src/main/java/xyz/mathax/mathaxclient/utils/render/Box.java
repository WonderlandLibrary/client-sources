package xyz.mathax.mathaxclient.utils.render;

import java.util.Objects;

public class Box {
    public double x, y;
    public double width, height;

    public Box(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Box() {
        this(0, 0, 0, 0);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Box box = (Box) object;
        return Double.compare(box.x, x) == 0 && Double.compare(box.y, y) == 0 && Double.compare(box.width, width) == 0 && Double.compare(box.height, height) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, width, height);
    }
}
