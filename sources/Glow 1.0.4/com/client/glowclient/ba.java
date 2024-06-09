package com.client.glowclient;

public class Ba
{
    public final ga B;
    private final int b;
    
    public int k() {
        return this.b & 0xFF;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof Ba && this.b == ((Ba)o).b);
    }
    
    public int A() {
        return this.b >> 24 & 0xFF;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(this.b);
    }
    
    public int D() {
        return this.b >> 8 & 0xFF;
    }
    
    private Ba(final ga b, final int b2) {
        this.B = b;
        super();
        this.b = b2;
    }
    
    public int M() {
        return this.b >> 16 & 0xFF;
    }
}
