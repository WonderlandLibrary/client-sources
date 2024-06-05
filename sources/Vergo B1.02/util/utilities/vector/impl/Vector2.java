package xyz.vergoclient.util.utilities.vector.impl;

import xyz.vergoclient.util.utilities.vector.Vector;

public class Vector2<T extends Number> extends Vector<Number> {

    public Vector2(T x, T y) {
        super(x, y, 0);
    }

    public Vector3<T> toVector3() {
        return new Vector3<>((T) getX(), ((T) getY()), ((T) getZ()));
    }
}
