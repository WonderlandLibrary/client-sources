package com.client.glowclient;

public enum pf
{
    A, 
    B;
    
    private static final pf[] b;
    
    static {
        b = new pf[] { pf.B, pf.A };
    }
    
    public static pf[] values() {
        return pf.b.clone();
    }
    
    public static pf valueOf(final String s) {
        return Enum.valueOf(pf.class, s);
    }
}
