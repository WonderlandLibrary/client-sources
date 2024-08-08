package net.futureclient.client;

public enum bB
{
    public static final bB a;
    private static final bB[] D;
    public static final bB k;
    
    static {
        bB.a = new bB("Blink", 0);
        bB.k = new bB("Pulse", 1);
        bB.D = new bB[] { bB.a, bB.k };
    }
    
    public static bB[] values() {
        return bB.D.clone();
    }
    
    public static bB valueOf(final String s) {
        return Enum.<bB>valueOf(bB.class, s);
    }
}