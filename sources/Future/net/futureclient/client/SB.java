package net.futureclient.client;

public enum SB
{
    public static final SB j;
    public static final SB K;
    private static final SB[] M;
    public static final SB d;
    public static final SB a;
    public static final SB D;
    public static final SB k;
    
    static {
        SB.a = new SB("Normal", 0);
        SB.K = new SB("Creative", 1);
        SB.k = new SB("Hypixel", 2);
        SB.D = new SB("Gomme", 3);
        SB.j = new SB("Riga", 4);
        SB.d = new SB("AAC", 5);
        SB.M = new SB[] { SB.a, SB.K, SB.k, SB.D, SB.j, SB.d };
    }
    
    public static SB[] values() {
        return SB.M.clone();
    }
    
    public static SB valueOf(final String s) {
        return Enum.<SB>valueOf(SB.class, s);
    }
}