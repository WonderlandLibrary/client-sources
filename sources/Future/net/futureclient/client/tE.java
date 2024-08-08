package net.futureclient.client;

public enum tE
{
    public static final tE M;
    public static final tE d;
    private static final tE[] a;
    public static final tE D;
    public static final tE k;
    
    static {
        tE.M = new tE("Jump", 0);
        tE.d = new tE("Mini", 1);
        tE.D = new tE("Packet", 2);
        tE.k = new tE("Hypixel", 3);
        tE.a = new tE[] { tE.M, tE.d, tE.D, tE.k };
    }
    
    public static tE[] values() {
        return tE.a.clone();
    }
    
    public static tE valueOf(final String s) {
        return Enum.<tE>valueOf(tE.class, s);
    }
}