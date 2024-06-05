package zamorozka.ui;

public class Vector3<T extends Number> extends Vector<Number> {

    public Vector3(T x, T y, T z) {
        super(x, y, z);
    }

    public Vector2<T> toVector2() {
        return new Vector2<>(((T) getX()), ((T) getY()));
    }

}