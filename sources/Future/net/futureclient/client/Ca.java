package net.futureclient.client;

private enum Ca
{
    public static final Ca M;
    public static final Ca d;
    public static final Ca a;
    public static final Ca D;
    private static final Ca[] k;
    
    static {
        Ca.M = new Ca("Bed", 0);
        Ca.a = new Ca("Egg", 1);
        Ca.d = new Ca("Cake", 2);
        Ca.D = new Ca("Farm", 3);
        Ca.k = new Ca[] { Ca.M, Ca.a, Ca.d, Ca.D };
    }
    
    public static Ca[] values() {
        return Ca.k.clone();
    }
    
    public static Ca valueOf(final String s) {
        return Enum.<Ca>valueOf(Ca.class, s);
    }
}