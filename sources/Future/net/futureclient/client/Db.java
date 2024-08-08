package net.futureclient.client;

private enum Db
{
    public static final Db a;
    public static final Db D;
    private static final Db[] k;
    
    static {
        Db.D = new Db("File", 0);
        Db.a = new Db("Ban", 1);
        Db.k = new Db[] { Db.D, Db.a };
    }
    
    public static Db[] values() {
        return Db.k.clone();
    }
    
    public static Db valueOf(final String s) {
        return Enum.<Db>valueOf(Db.class, s);
    }
}