package baritone;

public final class cc extends Enum<cc>
{
    public static final int a;
    public static final int b;
    public static final int c;
    public static final int d;
    private static final int[] a;
    
    public static int[] a() {
        return cc.a.clone();
    }
    
    static {
        a = 1;
        b = 2;
        c = 3;
        d = 4;
        a = new int[] { 1, 2, 3, 4 };
    }
}
