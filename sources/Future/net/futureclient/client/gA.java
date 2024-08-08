package net.futureclient.client;

public enum gA
{
    public static final gA d;
    public static final gA a;
    public static final gA D;
    private static final gA[] k;
    
    static {
        gA.d = new gA("Solid", 0);
        gA.a = new gA("Trampoline", 1);
        gA.D = new gA("Dolphin", 2);
        gA.k = new gA[] { gA.d, gA.a, gA.D };
    }
    
    public static gA[] values() {
        return gA.k.clone();
    }
    
    public static gA valueOf(final String s) {
        return Enum.<gA>valueOf(gA.class, s);
    }
}