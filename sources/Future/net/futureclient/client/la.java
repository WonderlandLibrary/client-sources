package net.futureclient.client;

private enum la
{
    public static final la M;
    public static final la d;
    public static final la a;
    private static final la[] D;
    public static final la k;
    
    static {
        la.k = new la("Survival", 0);
        la.M = new la("Selective", 1);
        la.d = new la("Farm", 2);
        la.a = new la("Creative", 3);
        la.D = new la[] { la.k, la.M, la.d, la.a };
    }
    
    public static la[] values() {
        return la.D.clone();
    }
    
    public static la valueOf(final String s) {
        return Enum.<la>valueOf(la.class, s);
    }
}