package jaco.mp3.a;

public final class g implements Cloneable
{
    private f a;
    
    public g() {
        this.a = new f();
    }
    
    public final Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException ex) {
            throw new InternalError(this + ": " + ex);
        }
    }
    
    public final f a() {
        return this.a;
    }
}
