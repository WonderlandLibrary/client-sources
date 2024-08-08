package net.futureclient.client;

public enum CC
{
    public static final CC M;
    public static final CC d;
    public static final CC a;
    public static final CC D;
    private static final CC[] k;
    
    static {
        CC.a = new CC(mh.M("\u0013*%3"), 0);
        CC.d = new CC("Outline", 1);
        CC.M = new CC(mh.M("\u00067,2"), 2);
        CC.D = new CC("Off", 3);
        CC.k = new CC[] { CC.a, CC.d, CC.M, CC.D };
    }
    
    public static CC[] values() {
        return CC.k.clone();
    }
    
    public static CC valueOf(final String s) {
        return Enum.<CC>valueOf(CC.class, s);
    }
}