package net.futureclient.client;

private enum oc
{
    public static final oc j;
    public static final oc K;
    private static final oc[] M;
    public static final oc d;
    public static final oc a;
    public static final oc D;
    public static final oc k;
    
    static {
        oc.a = new oc("Off", 0);
        oc.j = new oc("Static", 1);
        oc.K = new oc("Spin", 2);
        oc.d = new oc("Jitter", 3);
        oc.D = new oc("Overflow", 4);
        oc.k = new oc("Zero", 5);
        oc.M = new oc[] { oc.a, oc.j, oc.K, oc.d, oc.D, oc.k };
    }
    
    public static oc[] values() {
        return oc.M.clone();
    }
    
    public static oc valueOf(final String s) {
        return Enum.<oc>valueOf(oc.class, s);
    }
}