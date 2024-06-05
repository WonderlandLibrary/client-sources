package jaco.mp3.a;

public final class o
{
    private static short a;
    private short b;
    
    static {
        o.a = -32763;
    }
    
    public o() {
        this.b = -1;
    }
    
    public final void a(final int n, int n2) {
        n2 = 1 << n2 - 1;
        do {
            if ((this.b & 0x8000) == 0x0 ^ (n & n2) == 0x0) {
                this.b <<= 1;
                this.b ^= o.a;
            }
            else {
                this.b <<= 1;
            }
        } while ((n2 >>>= 1) != 0);
    }
    
    public final short a() {
        final short b = this.b;
        this.b = -1;
        return b;
    }
}
