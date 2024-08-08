package net.futureclient.client;

public class Jb
{
    public static final int[] k;
    
    static {
        k = new int[RC.sc.values().length];
        try {
            Jb.k[RC.sc.a.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            Jb.k[RC.sc.D.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
