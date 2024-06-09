package com.client.glowclient;

import com.client.glowclient.utils.*;
import com.client.glowclient.modules.other.*;
import org.lwjgl.opengl.*;

public class ME extends gf
{
    private final zE G;
    private NumberValue d;
    private int L;
    private float B;
    private boolean b;
    
    public float A() {
        return (float)this.d.D();
    }
    
    @Override
    public void M(final int n, final int n2) {
        final int m = HUD.red.M();
        final int i = HUD.green.M();
        final int j = HUD.blue.M();
        if (this.b) {
            this.L = n - this.D();
            ME me;
            if (this.L < 0) {
                me = this;
                this.L = 0;
            }
            else {
                if (this.L > 92) {
                    this.L = 92;
                }
                me = this;
            }
            me.D();
        }
        final float n3 = 0.0f;
        this.d = this.M.B();
        this.B = this.M.D() + 4;
        this.D(n, n2);
        ba.M(this.D(), this.e(), -1, this.b, ga.M(m, i, j, 255));
        final int d = this.D();
        final int e = this.e();
        final int k = this.M();
        final int b = this.b;
        final int n4 = 0;
        ba.M(d, e, k, b, ga.M(n4, n4, n4, n4));
        ba.M(this.D(), this.e(), this.L, this.b, this.k());
        final float n5 = 0.0f;
        GL11.glColor3f(n3, n5, n5);
        final ca ca = new ca();
        Ia.M(HUD.F, new StringBuilder().insert(0, this.d.A()).append(": ").append(DD.M(this.B)).toString(), this.D() + 2, this.e() + 2, true, ga.G, 1.0);
        if (this.A() && HUD.descriptions.M()) {
            final int n6 = n2 + 3;
            final int n7 = (int)Ia.M(HUD.F, this.d.D(), 1.0) + 2;
            final int n8 = -12;
            final int n9 = 10;
            ba.M(n, n6, n7, n8, ga.M(n9, n9, n9, 255), 175, 1.0f);
            ca.K().M(HUD.F).D(ga.G).M(this.d.D(), n + 1, n2 - 7);
        }
    }
    
    public ME(final zE g, final NumberValue d) {
        final boolean b = false;
        super(g.M().D() + 4, g.e() + 4, g.M().e() - 8, 14);
        this.b = b;
        this.G = g;
        this.M = g.M();
        if (d != null) {
            this.B = (float)d.k();
        }
        this.d = d;
    }
    
    public float D() {
        return (float)this.d.A();
    }
    
    public void D() {
        this.d.M(DD.M((double)(this.B = DD.M(DD.M(this.L / 92.0f * (this.D() - this.M()) + this.M(), this.A()), this.M(), this.D()))));
    }
    
    @Override
    public boolean D() {
        return this.G.M() && this.G.D();
    }
    
    @Override
    public void D(final int n, final int n2, final int n3) {
        this.D(n, n2);
        if (this.A() && n3 == 0) {
            this.b = true;
        }
    }
    
    @Override
    public String M() {
        return this.d.A();
    }
    
    public int E() {
        return this.L = (int)DD.M((this.B - this.M()) / (this.D() - this.M()) * 92.0f, 0.0f, 92.0f);
    }
    
    public float M() {
        return (float)this.d.M();
    }
    
    @Override
    public void M(final int n, final int n2, final int n3) {
        this.D(n, n2);
        if (this.b && n3 == 0) {
            this.b = false;
            this.E();
        }
    }
    
    @Override
    public void M() {
        if (this.d != null) {
            this.B = (float)this.d.k();
        }
        this.E();
    }
    
    @Override
    public int k() {
        return Ze.M(2, this.A(), false);
    }
    
    public zE M() {
        return this.G;
    }
}
