package net.futureclient.client;

public enum Dc
{
    public static final Dc a;
    public static final Dc D;
    private static final Dc[] k;
    
    static {
        Dc.D = new Dc("Normal", 0);
        Dc.a = new Dc("AAC", 1);
        Dc.k = new Dc[] { Dc.D, Dc.a };
    }
    
    public static Dc[] values() {
        return Dc.k.clone();
    }
    
    public static Dc valueOf(final String s) {
        return Enum.<Dc>valueOf(Dc.class, s);
    }
}