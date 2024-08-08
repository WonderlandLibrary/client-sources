package net.futureclient.client;

private enum bC
{
    public static final bC d;
    private static final bC[] a;
    public static final bC D;
    public static final bC k;
    
    static {
        bC.d = new bC("Shader", 0);
        bC.k = new bC("Outline", 1);
        bC.D = new bC("CsGo", 2);
        bC.a = new bC[] { bC.d, bC.k, bC.D };
    }
    
    public static bC[] values() {
        return bC.a.clone();
    }
    
    public static bC valueOf(final String s) {
        return Enum.<bC>valueOf(bC.class, s);
    }
}