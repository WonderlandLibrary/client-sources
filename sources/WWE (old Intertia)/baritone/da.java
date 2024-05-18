package baritone;

import java.util.function.*;
import java.util.*;

public final class da
{
    private final Map<aow, Double> a;
    private final Function<aow, Double> a;
    private final bud a;
    
    public da(final bud a) {
        super();
        this.a = new HashMap<aow, Double>();
        this.a = a;
        if (baritone.a.a().considerPotionEffects.value) {
            double n = 1.0;
            if (this.a.a(vb.c)) {
                n = 1.0 * (1.0 + (this.a.b(vb.c).c() + 1) * 0.2);
            }
            if (this.a.a(vb.d)) {
                switch (this.a.b(vb.d).c()) {
                    case 0: {
                        n *= 0.3;
                        break;
                    }
                    case 1: {
                        n *= 0.09;
                        break;
                    }
                    case 2: {
                        n *= 0.0027;
                        break;
                    }
                    default: {
                        n *= 8.1E-4;
                        break;
                    }
                }
            }
            this.a = (Function<aow, Double>)((Function<T, Double>)da::a).compose((Function<? super Object, ? extends T>)this::a);
            return;
        }
        this.a = this::a;
    }
    
    public final double a(final awt awt) {
        return this.a.computeIfAbsent(awt.u(), this.a);
    }
    
    private static int a(final aip aip) {
        if (aip.c() instanceof ahq) {
            return ain$a.valueOf(((ahq)aip.c()).h()).ordinal();
        }
        return -1;
    }
    
    public final byte a(final aow aow) {
        byte b = 0;
        double n = Double.NEGATIVE_INFINITY;
        int a = Integer.MIN_VALUE;
        final awt t = aow.t();
        for (byte b2 = 0; b2 < 9; ++b2) {
            final aip a3;
            final double a2;
            if ((a2 = a(a3 = this.a.bv.a((int)b2), t)) > n) {
                n = a2;
                b = b2;
                a = a(a3);
            }
            else {
                final int a4;
                if (a2 == n && (a4 = a(a3)) < a) {
                    n = a2;
                    b = b2;
                    a = a4;
                }
            }
        }
        return b;
    }
    
    private double a(final aow aow) {
        return a(this.a.bv.a((int)this.a(aow)), aow.t());
    }
    
    public static double a(final aip aip, final awt awt) {
        final float b;
        if ((b = awt.b((amu)null, (et)null)) < 0.0f) {
            return -1.0;
        }
        float a;
        final int a2;
        if ((a = aip.a(awt)) > 1.0f && (a2 = alm.a(alo.s, aip)) > 0 && !aip.b()) {
            final float n = a;
            final int n2 = a2;
            a = n + (n2 * n2 + 1);
        }
        final float n3 = a / b;
        if (awt.a().l() || (!aip.b() && aip.b(awt))) {
            return n3 / 30.0f;
        }
        return n3 / 100.0f;
    }
    
    private static Double a(final double n, final Double n2) {
        return n * n2;
    }
}
