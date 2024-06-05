package jaco.mp3.a;

public final class s extends q
{
    private int a;
    
    public s(final String s, final Throwable t) {
        super(s, t);
        this.a = 256;
    }
    
    public s(final int a, final Throwable t) {
        this("Bitstream errorcode " + Integer.toHexString(a), t);
        this.a = a;
    }
    
    public final int a() {
        return this.a;
    }
}
