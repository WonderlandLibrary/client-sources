package net.futureclient.client;

public enum YA
{
    private static final YA[] d;
    public static final YA a;
    public static final YA D;
    public static final YA k;
    
    static {
        YA.D = new YA("Off", 0);
        YA.k = new YA(AE.M(".,\u0002 "), 1);
        YA.a = new YA("Remove", 2);
        YA.d = new YA[] { YA.D, YA.k, YA.a };
    }
    
    public static YA[] values() {
        return YA.d.clone();
    }
    
    public static YA valueOf(final String s) {
        return Enum.<YA>valueOf(YA.class, s);
    }
}