package net.futureclient.client;

private enum yE
{
    private static final yE[] a;
    public static final yE D;
    public static final yE k;
    
    static {
        yE.D = new yE("Tunnel", 0);
        yE.k = new yE(mh.M("\u000e;!,\"'"), 1);
        yE.a = new yE[] { yE.D, yE.k };
    }
    
    public static yE[] values() {
        return yE.a.clone();
    }
    
    public static yE valueOf(final String s) {
        return Enum.<yE>valueOf(yE.class, s);
    }
}