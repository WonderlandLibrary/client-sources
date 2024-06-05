package ClassSub;

public class Class143<T extends Number> extends Class222<Number>
{
    
    
    public Class143(final T t, final T t2, final T t3) {
        super(t, t2, t3);
    }
    
    public Class62<T> toVector2() {
        return new Class62<T>((T)this.getX(), (T)this.getY());
    }
}
