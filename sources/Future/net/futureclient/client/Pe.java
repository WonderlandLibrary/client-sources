package net.futureclient.client;

private enum Pe
{
    public static final Pe a;
    public static final Pe D;
    private static final Pe[] k;
    
    static {
        Pe.a = new Pe("Right", 0);
        Pe.D = new Pe("Left", 1);
        Pe.k = new Pe[] { Pe.a, Pe.D };
    }
    
    public static Pe[] values() {
        return Pe.k.clone();
    }
    
    public static Pe valueOf(final String s) {
        return Enum.<Pe>valueOf(Pe.class, s);
    }
}