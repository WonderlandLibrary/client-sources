package net.futureclient.client;

private enum Ka
{
    public static final Ka a;
    private static final Ka[] D;
    public static final Ka k;
    
    static {
        Ka.a = new Ka("Normal", 0);
        Ka.k = new Ka("Hypixel", 1);
        Ka.D = new Ka[] { Ka.a, Ka.k };
    }
    
    public static Ka[] values() {
        return Ka.D.clone();
    }
    
    public static Ka valueOf(final String s) {
        return Enum.<Ka>valueOf(Ka.class, s);
    }
}