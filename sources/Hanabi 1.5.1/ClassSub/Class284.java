package ClassSub;

public class Class284<T extends Number> extends Class310<Number>
{
    
    
    public Class284(final T t, final T t2) {
        super(t, t2, 0);
    }
    
    public Class266<T> toVector3() {
        return new Class266<T>((T)this.getX(), (T)this.getY(), (T)this.getZ());
    }
}
