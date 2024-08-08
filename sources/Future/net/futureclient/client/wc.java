package net.futureclient.client;

private enum wc
{
    private static final wc[] d;
    public static final wc a;
    public static final wc D;
    public static final wc k;
    
    static {
        wc.a = new wc("Normal", 0);
        wc.D = new wc("Cowabunga", 1);
        wc.k = new wc("Hypixel", 2);
        wc.d = new wc[] { wc.a, wc.D, wc.k };
    }
    
    public static wc[] values() {
        return wc.d.clone();
    }
    
    public static wc valueOf(final String s) {
        return Enum.<wc>valueOf(wc.class, s);
    }
}