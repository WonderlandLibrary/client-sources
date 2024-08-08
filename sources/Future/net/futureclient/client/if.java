package net.futureclient.client;

public enum if
{
    public static final if a;
    private static final if[] D;
    public static final if k;
    
    static {
        if.k = new if("Up", 0);
        if.a = new if("Down", 1);
        if.D = new if[] { if.k, if.a };
    }
    
    public static if[] values() {
        return if.D.clone();
    }
    
    public static if valueOf(final String s) {
        return Enum.<if>valueOf(if.class, s);
    }
}