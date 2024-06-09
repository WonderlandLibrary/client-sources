package com.client.glowclient;

import com.client.glowclient.modules.other.*;
import org.lwjgl.opengl.*;

public abstract class xf extends gf
{
    private String A;
    private String B;
    private final zE b;
    
    @Override
    public String M() {
        return this.B;
    }
    
    @Override
    public abstract void D(final int p0, final int p1, final int p2);
    
    @Override
    public boolean D() {
        return this.b.M() && this.b.D();
    }
    
    @Override
    public int k() {
        return Ze.M(3, this.A(), true);
    }
    
    @Override
    public void M(final int n, final int n2) {
        final int m = HUD.red.M();
        final int i = HUD.green.M();
        final int j = HUD.blue.M();
        final float n3 = 0.0f;
        this.d = this.M.B();
        this.B = this.M.D() + 4;
        this.D(n, n2);
        ba.M(this.D(), this.e(), this.M(), this.b, this.k());
        ba.M(this.D(), this.e(), -1, this.b, ga.M(m, i, j, 255));
        final float n4 = 0.0f;
        GL11.glColor3f(n3, n4, n4);
        final ca ca = new ca();
        xf xf;
        if (HUD.F != null) {
            ca.K().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(HUD.F).D(ga.G).M(this.B, this.D() + 2 + 1, this.e() + 2 + 1, true).D(ga.G).M(this.B, this.D() + 2, this.e() + 2);
            xf = this;
        }
        else {
            ca.K().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(HUD.F).D(ga.G).M(this.B, this.D() + 2, this.e() + 2, true);
            xf = this;
        }
        if (xf.A() && HUD.descriptions.M()) {
            final int n5 = n2 + 3;
            final int n6 = (int)Ia.M(HUD.F, this.A, 1.0) + 2;
            final int n7 = -12;
            final int n8 = 10;
            ba.M(n, n5, n6, n7, ga.M(n8, n8, n8, 255), 175, 1.0f);
            ca.K().M(HUD.F).D(ga.G).M(this.A, n + 1, n2 - 7);
        }
    }
    
    public xf(final zE b, final String b2, final String a) {
        super(b.M().D() + 4, b.e() + 4, b.M().e() - 8, 14);
        this.b = b;
        this.M = b.M();
        this.B = b2;
        this.A = a;
    }
    
    public zE M() {
        return this.b;
    }
}
