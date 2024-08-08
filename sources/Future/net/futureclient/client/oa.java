package net.futureclient.client;

public enum oa
{
    public static final oa a;
    private static final oa[] D;
    public static final oa k;
    
    static {
        oa.a = new oa("Normal", 0);
        oa.k = new oa("Packet", 1);
        oa.D = new oa[] { oa.a, oa.k };
    }
    
    public static oa[] values() {
        return oa.D.clone();
    }
    
    public static oa valueOf(final String s) {
        return Enum.<oa>valueOf(oa.class, s);
    }
}