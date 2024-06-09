package com.client.glowclient;

import java.util.*;

public enum qB
{
    private static final qB[] f;
    
    M("amount", "\u2193", qB::D), 
    G("name", "\u2191", qB::k);
    
    private final Comparator<Bb> d;
    public final String L;
    
    A("name", "\u2193", qB::M);
    
    public final String B;
    
    b("amount", "\u2191", qB::A);
    
    private static int k(final Bb bb, final Bb bb2) {
        return bb.D().compareTo(bb2.D());
    }
    
    public static qB M(final String s) {
        try {
            return valueOf(s);
        }
        catch (Exception ex) {
            return qB.G;
        }
    }
    
    public void M(final List<Bb> list) {
        try {
            Collections.sort((List<Object>)list, (Comparator<? super Object>)this.d);
        }
        catch (Exception ex) {
            ld.H.error("Could not sort the block list!", (Throwable)ex);
        }
    }
    
    private static int A(final Bb bb, final Bb bb2) {
        return bb.A - bb2.A;
    }
    
    static {
        f = new qB[] { qB.G, qB.A, qB.b, qB.M };
    }
    
    private static int D(final Bb bb, final Bb bb2) {
        return bb2.A - bb.A;
    }
    
    public static qB[] values() {
        return qB.f.clone();
    }
    
    public static qB valueOf(final String s) {
        return Enum.valueOf(qB.class, s);
    }
    
    private static int M(final Bb bb, final Bb bb2) {
        return bb2.D().compareTo(bb.D());
    }
    
    public qB M() {
        final qB[] values = values();
        final int n = this.ordinal() + 1;
        final qB[] array = values;
        return array[n % array.length];
    }
    
    private qB(final String b, final String l, final Comparator<Bb> d) {
        this.B = b;
        this.L = l;
        this.d = d;
    }
}
