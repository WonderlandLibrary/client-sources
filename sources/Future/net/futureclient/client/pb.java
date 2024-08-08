package net.futureclient.client;

import net.futureclient.client.events.EventType2;

public class pb
{
    public static final int[] D;
    public static final int[] k;
    
    static {
        k = new int[EventType2.values().length];
        try {
            pb.k[EventType2.POST.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            pb.k[EventType2.PRE.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        D = new int[db.fC.values().length];
        try {
            pb.D[db.fC.d.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            pb.D[db.fC.E.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            pb.D[db.fC.k.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            pb.D[db.fC.j.ordinal()] = 4;
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        try {
            pb.D[db.fC.a.ordinal()] = 5;
        }
        catch (NoSuchFieldError noSuchFieldError7) {}
        try {
            pb.D[db.fC.M.ordinal()] = 6;
        }
        catch (NoSuchFieldError noSuchFieldError8) {}
        try {
            pb.D[db.fC.K.ordinal()] = 7;
        }
        catch (NoSuchFieldError noSuchFieldError9) {}
        try {
            pb.D[db.fC.A.ordinal()] = 8;
        }
        catch (NoSuchFieldError noSuchFieldError10) {}
    }
}
