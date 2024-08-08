package net.futureclient.client;

private enum zc
{
    public static final zc M;
    public static final zc d;
    public static final zc a;
    private static final zc[] D;
    public static final zc k;
    
    static {
        zc.k = new zc("Packet", 0);
        zc.M = new zc("Anti", 1);
        zc.d = new zc("AAC", 2);
        zc.a = new zc("Bucket", 3);
        zc.D = new zc[] { zc.k, zc.M, zc.d, zc.a };
    }
    
    public static zc[] values() {
        return zc.D.clone();
    }
    
    public static zc valueOf(final String s) {
        return Enum.<zc>valueOf(zc.class, s);
    }
}