package ClassSub;

public class Class62<T extends Number> extends Class222<Number>
{
    
    
    public Class62(final T t, final T t2) {
        super(t, t2, 0);
    }
    
    public Class143<T> toVector3() {
        return new Class143<T>((T)this.getX(), (T)this.getY(), (T)this.getZ());
    }
}
