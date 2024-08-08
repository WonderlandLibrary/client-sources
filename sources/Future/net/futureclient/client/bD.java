package net.futureclient.client;

public enum bD
{
    private static final bD[] M;
    public static final bD d;
    public static final bD a;
    public static final bD D;
    public static final bD k;
    
    static {
        bD.a = new bD("Sand", 0);
        bD.d = new bD("Climb", 1);
        bD.D = new bD("Door", 2);
        bD.k = new bD("Normal", 3);
        bD.M = new bD[] { bD.a, bD.d, bD.D, bD.k };
    }
    
    public static bD[] values() {
        return bD.M.clone();
    }
    
    public static bD valueOf(final String s) {
        return Enum.<bD>valueOf(bD.class, s);
    }
}