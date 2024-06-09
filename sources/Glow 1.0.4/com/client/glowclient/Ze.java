package com.client.glowclient;

import com.client.glowclient.modules.*;
import com.client.glowclient.modules.other.*;
import java.util.*;
import com.client.glowclient.utils.*;

public class zE extends gf
{
    private Module G;
    private boolean d;
    private List<gf> A;
    private Timer B;
    public BooleanValue b;
    
    @Override
    public String M() {
        return this.G.k();
    }
    
    @Override
    public void M(final int n, final int n2) {
        final ca ca = new ca();
        this.d = this.M.B();
        this.B = this.M.D() + 2;
        this.D(n, n2);
        zE ze;
        if (HUD.F != null) {
            ca.K().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(HUD.F).D(this.k()).M(this.G.k(), this.D() + 2 + 1, this.e() + 3 + 1, true).D(this.k()).M(this.G.k(), this.D() + 2, this.e() + 3);
            ze = this;
        }
        else {
            ca.K().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(HUD.F).D(this.k()).M(this.G.k(), this.D() + 2, this.e() + 3, true);
            ze = this;
        }
        if (!ze.A()) {
            final int d = this.D();
            final int e = this.e();
            final int m = this.M();
            final int b = this.b;
            final int n3 = 0;
            ba.M(d, e, m, b, ga.M(n3, n3, n3, n3));
            return;
        }
        final int d2 = this.D();
        final int e2 = this.e();
        final int i = this.M();
        final int b2 = this.b;
        final int n4 = 100;
        ba.M(d2, e2, i, b2, ga.M(n4, n4, n4, 50));
        if (HUD.descriptions.M()) {
            final int n5 = n2 + 3;
            final int n6 = (int)Ia.M(HUD.F, this.G.A(), 1.0) + 2;
            final int n7 = -12;
            final int n8 = 10;
            ba.M(n, n5, n6, n7, ga.M(n8, n8, n8, 255), 175, 1.0f);
            ca.K().M(HUD.F).D(ga.G).M(this.G.A(), n + 1, n2 - 7);
        }
    }
    
    @Override
    public int k() {
        return Ze.M(0, this.A(), this.G.k());
    }
    
    @Override
    public int A() {
        int b = this.b;
        if (this.b.M()) {
            b += 15 * this.A.size();
        }
        return b;
    }
    
    @Override
    public void M(final boolean b) {
        this.b.M(b);
    }
    
    public zE(final qe m, final Module g) {
        final boolean d = false;
        super(m.D() + 2, m.E() + 2, m.e() - 6, 14);
        this.A = Collections.synchronizedList(new ArrayList<gf>());
        this.d = d;
        this.B = new Timer();
        this.M = m;
        this.G = g;
        this.b = ValueFactory.M(new StringBuilder().insert(0, "Button.").append(g.k()).toString(), "isOpen", "Is button for module open", false);
    }
    
    @Override
    public boolean M() {
        return this.b.M();
    }
    
    public Module M() {
        return this.G;
    }
    
    @Override
    public void D(final int n, final int n2, final int n3) {
        this.D(n, n2);
        if (!this.d) {
            final boolean d = true;
            this.B.reset();
            this.d = d;
        }
        if (this.M.I.M() && this.A()) {
            if (n3 == 0) {
                DD.M(this.G);
            }
            if (n3 == 1) {
                this.b.M(!this.b.M());
            }
        }
    }
    
    public List<gf> M() {
        return this.A;
    }
}
