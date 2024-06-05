package zamorozka.ui;

public class Vector<T extends Number> {

    private T x, y, z;

    public Vector(T x, T y, T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public T getX() {
        return x;
    }

    public Vector setX(T x) {
        this.x = x;
        return this;
    }

    public T getY() {
        return y;
    }

    public Vector setY(T y) {
        this.y = y;
        return this;
    }

    public T getZ() {
        return z;
    }

    public Vector setZ(T z) {
        this.z = z;
        return this;
    }
}