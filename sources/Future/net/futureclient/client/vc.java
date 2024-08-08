package net.futureclient.client;

public enum vc
{
    private static final vc[] d;
    public static final vc a;
    public static final vc D;
    public static final vc k;
    
    static {
        vc.D = new vc("None", 0);
        vc.a = new vc("Rider", 1);
        vc.k = new vc("Friend", 2);
        vc.d = new vc[] { vc.D, vc.a, vc.k };
    }
    
    public static vc[] values() {
        return vc.d.clone();
    }
    
    public static vc valueOf(final String s) {
        return Enum.<vc>valueOf(vc.class, s);
    }
}