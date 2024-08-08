package net.futureclient.client;

private enum SD
{
    private static final SD[] d;
    public static final SD a;
    public static final SD D;
    public static final SD k;
    
    static {
        SD.k = new SD("Yaw", 0);
        SD.a = new SD("Pitch", 1);
        SD.D = new SD("Both", 2);
        SD.d = new SD[] { SD.k, SD.a, SD.D };
    }
    
    public static SD[] values() {
        return SD.d.clone();
    }
    
    public static SD valueOf(final String s) {
        return Enum.<SD>valueOf(SD.class, s);
    }
}