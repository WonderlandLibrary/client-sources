package com.client.glowclient;

public enum FA
{
    private boolean f;
    private static final FA[] M;
    
    G(true), 
    d, 
    L, 
    A(true), 
    B(true), 
    b;
    
    static {
        M = new FA[] { FA.B, FA.d, FA.G, FA.L, FA.A, FA.b };
    }
    
    public boolean M() {
        return this.f;
    }
    
    private FA(final boolean f) {
        this.f = f;
    }
    
    public static FA valueOf(final String s) {
        return Enum.valueOf(FA.class, s);
    }
    
    public static FA[] values() {
        return FA.M.clone();
    }
}
