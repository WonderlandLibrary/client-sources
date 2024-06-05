package me.tojatta.api.utilities.utilities.vector;

public class Vector<T extends Number>
{
    private T x;
    private T y;
    private T z;
    
    public Vector(final T x, final T y, final T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public T getX() {
        return this.x;
    }
    
    public Vector setX(final T x) {
        this.x = x;
        return this;
    }
    
    public T getY() {
        return this.y;
    }
    
    public Vector setY(final T y) {
        this.y = y;
        return this;
    }
    
    public T getZ() {
        return this.z;
    }
    
    public Vector setZ(final T z) {
        this.z = z;
        return this;
    }
}
