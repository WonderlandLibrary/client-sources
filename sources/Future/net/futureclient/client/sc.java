package net.futureclient.client;

private enum sc
{
    public static final sc a;
    public static final sc D;
    private static final sc[] k;
    
    static {
        sc.a = new sc("Coords", 0);
        sc.D = new sc(AE.M("\",\u00151\u0007+\u0005 "), 1);
        sc.k = new sc[] { sc.a, sc.D };
    }
    
    public static sc[] values() {
        return sc.k.clone();
    }
    
    public static sc valueOf(final String s) {
        return Enum.<sc>valueOf(sc.class, s);
    }
}