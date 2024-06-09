package com.client.glowclient;

public enum V
{
    G, 
    d, 
    L;
    
    private static final V[] A;
    
    B, 
    b;
    
    public boolean M() {
        return this.ordinal() > 0;
    }
    
    public static V[] values() {
        return V.A.clone();
    }
    
    public static V valueOf(final String s) {
        return Enum.valueOf(V.class, s);
    }
    
    static {
        A = new V[] { V.G, V.B, V.L, V.d, V.b };
    }
}
