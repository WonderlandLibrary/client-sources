package baritone;

public final class ag extends Enum<ag>
{
    public static final int a;
    public static final int b;
    public static final int c;
    private static final int[] a;
    
    public static int[] a() {
        return ag.a.clone();
    }
    
    static {
        a = 1;
        b = 2;
        c = 3;
        a = new int[] { 1, 2, 3 };
    }
}
