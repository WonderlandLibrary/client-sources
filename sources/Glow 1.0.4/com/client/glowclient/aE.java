package com.client.glowclient;

public class aE
{
    public static final int[] b;
    
    static {
        b = new int[pf.values().length];
        try {
            aE.b[pf.B.ordinal()] = 1;
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            aE.b[pf.A.ordinal()] = 2;
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
    }
}
