package net.futureclient.client;

private enum XC
{
    public static final XC a;
    private static final XC[] D;
    public static final XC k;
    
    static {
        XC.a = new XC("Once", 0);
        XC.k = new XC("Looping", 1);
        XC.D = new XC[] { XC.a, XC.k };
    }
    
    public static XC[] values() {
        return XC.D.clone();
    }
    
    public static XC valueOf(final String s) {
        return Enum.<XC>valueOf(XC.class, s);
    }
}