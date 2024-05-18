package baritone;

final class cb
{
    static final int[] a;
    
    static {
        a = new int[cc.a().length];
        try {
            final int[] a2 = cb.a;
            final int b = cc.b;
            a2[1] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            final int[] a3 = cb.a;
            final int c = cc.c;
            a3[2] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            final int[] a4 = cb.a;
            final int d = cc.d;
            a4[3] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
    }
}
