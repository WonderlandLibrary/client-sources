package ClassSub;

public class Class222<T extends Number>
{
    private T x;
    private T y;
    private T z;
    
    
    public Class222(final T x, final T y, final T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Class222 setX(final T x) {
        this.x = x;
        return this;
    }
    
    public Class222 setY(final T y) {
        this.y = y;
        return this;
    }
    
    public Class222 setZ(final T z) {
        this.z = z;
        return this;
    }
    
    public T getX() {
        return this.x;
    }
    
    public T getY() {
        return this.y;
    }
    
    public T getZ() {
        return this.z;
    }
}
