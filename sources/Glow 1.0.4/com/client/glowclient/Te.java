package com.client.glowclient;

import com.google.common.collect.*;
import net.minecraft.util.math.*;
import java.util.*;

public class te
{
    private final Ge A;
    private final Queue<Double> B;
    private final List<Ge> b;
    
    private void A() {
        this.B.clear();
        this.D();
    }
    
    public static void M(final te te, final long n) {
        te.M(n);
    }
    
    public te(final int n, final vF vf) {
        this(n);
    }
    
    public int M() {
        return this.B.size();
    }
    
    public static void M(final te te) {
        te.A();
    }
    
    private te(final int n) {
        final int i = 0;
        super();
        this.A = new Ge(this);
        this.b = (List<Ge>)Lists.newArrayList();
        this.B = (Queue<Double>)EvictingQueue.create(n);
        int n2 = i;
        while (i < n) {
            final List<Ge> b = this.b;
            ++n2;
            b.add(new Ge(this));
        }
    }
    
    public Ge M() {
        return this.M(this.M() - 1);
    }
    
    private void D() {
        final Iterator<Ge> iterator2;
        Iterator<Ge> iterator = iterator2 = this.b.iterator();
        while (iterator.hasNext()) {
            iterator2.next().M();
            iterator = iterator2;
        }
    }
    
    private void M() {
        final int n = 0;
        this.D();
        int n2 = n;
        double n3 = 0.0;
        final ArrayList<Object> arrayList = Lists.newArrayList((Iterable<?>)this.B);
        Collections.reverse(arrayList);
        final Iterator<Double> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            final Iterator<Double> iterator2 = iterator;
            ++n2;
            n3 += iterator2.next();
            final Ge ge;
            if ((ge = this.b.get(n2 - 1)) != null) {
                final int n4 = n2;
                Ge.M(ge, MathHelper.clamp(n3 / n2, 0.0, 20.0));
                if (n4 < 40) {
                    continue;
                }
                this.B.clear();
            }
        }
    }
    
    public Ge M(final int n) {
        final Ge ge;
        if ((ge = this.b.get(Math.max(Math.min(this.M() - 1, n - 1), 0))) != null) {
            return ge;
        }
        return this.A;
    }
    
    private void M(final long n) {
        this.B.offer(MathHelper.clamp(20.0 / (n / 1000.0), 0.0, 20.0));
        this.M();
    }
}
