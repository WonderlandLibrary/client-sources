package baritone;

public enum dj
{
    a(0), 
    b(1), 
    c(2), 
    d(3);
    
    public final boolean[] a;
    private static final dj[] a;
    
    public static dj[] values() {
        return dj.a.clone();
    }
    
    public static dj valueOf(final String s) {
        return Enum.valueOf(dj.class, s);
    }
    
    private dj(final int n2) {
        this.a = new boolean[] { (n2 & 0x2) != 0x0, (n2 & 0x1) != 0x0 };
    }
    
    public static dj a(final boolean b, final boolean b2) {
        if (b) {
            if (b2) {
                return dj.d;
            }
            return dj.c;
        }
        else {
            if (b2) {
                return dj.b;
            }
            return dj.a;
        }
    }
    
    static {
        a = new dj[] { dj.a, dj.b, dj.c, dj.d };
    }
}
