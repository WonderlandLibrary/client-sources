package com.client.glowclient;

public class Kb
{
    public static final int[] b;
    
    static {
        b = new int[Fd.values().length];
        try {
            Kb.b[Fd.b.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            Kb.b[Fd.G.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            Kb.b[Fd.A.ordinal()] = 3;
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
    }
}
