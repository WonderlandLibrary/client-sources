package me.tojatta.api.utilities.utilities.vector.impl;

import me.tojatta.api.utilities.utilities.vector.*;

public class Vector3<T extends Number> extends Vector<Number>
{
    public Vector3(final T x, final T y, final T z) {
        super(x, y, z);
    }
    
    public Vector2<T> toVector2() {
        return new Vector2<T>((T)this.getX(), (T)this.getY());
    }
}
