package com.client.glowclient;

import com.client.glowclient.utils.*;
import com.client.glowclient.modules.other.*;
import org.lwjgl.opengl.*;

public class ED extends gf
{
    private qe M;
    private nB f;
    public Timer M;
    private boolean G;
    private final zE d;
    private static int L;
    
    public zE M() {
        return this.d;
    }
    
    @Override
    public boolean D() {
        return this.d.M() && this.d.D();
    }
    
    @Override
    public void D(final int n, final int n2, final int n3) {
        this.D(n, n2);
        if (!this.G) {
            final boolean g = true;
            this.M.reset();
            this.G = g;
        }
        if (this.A() && this.d.M()) {
            int n4 = 0;
            Label_0140: {
                if (n3 == 0) {
                    ED.L = this.f.M().indexOf(this.f.e());
                    ++ED.L;
                    if (ED.L != this.f.M().size()) {
                        n4 = n3;
                        this.f.M(this.f.M().get(ED.L));
                        break Label_0140;
                    }
                    this.f.M(this.f.M().get(0));
                    ED.L = 0;
                }
                n4 = n3;
            }
            if (n4 == 1 && this.M.delay(50.0)) {
                ED.L = this.f.M().indexOf(this.f.e());
                --ED.L;
                if (ED.L != -1) {
                    this.f.M(this.f.M().get(ED.L));
                    return;
                }
                this.f.M(this.f.M().get(this.f.M().size() - 1));
                ED.L = this.f.M().size() - 1;
            }
        }
    }
    
    @Override
    public String M() {
        return this.f.A();
    }
    
    @Override
    public int k() {
        return Ze.M(2, this.A(), false);
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
        if (new StringBuilder().insert(0, this.f.A()).append(": ").append(this.f.e()).toString().length() <= 15) {
            ED ed;
            if (HUD.F != null) {
                ca.K().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(HUD.F).D(ga.G).M(new StringBuilder().insert(0, this.f.A()).append(": ").append(this.f.e()).toString(), this.D() + 2 + 1, this.e() + 2 + 1, true).D(ga.G).M(new StringBuilder().insert(0, this.f.A()).append(": ").append(this.f.e()).toString(), this.D() + 2, this.e() + 2);
                ed = this;
            }
            else {
                ca.K().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(HUD.F).D(ga.G).M(new StringBuilder().insert(0, this.f.A()).append(": ").append(this.f.e()).toString(), this.D() + 2, this.e() + 2, true);
                ed = this;
            }
            if (ed.A() && HUD.descriptions.M()) {
                final int n5 = n2 + 3;
                final int n6 = (int)Ia.M(HUD.F, this.f.D(), 1.0) + 2;
                final int n7 = -12;
                final int n8 = 10;
                ba.M(n, n5, n6, n7, ga.M(n8, n8, n8, 255), 175, 1.0f);
                ca.K().M(HUD.F).D(ga.G).M(this.f.D(), n + 1, n2 - 7);
            }
        }
        else {
            final ea f = HUD.F;
            final ca ca2 = ca;
            ED ed2;
            if (f != null) {
                ca2.K().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(HUD.F).D(ga.G).M(new StringBuilder().insert(0, this.f.A()).append(": ").append(this.f.e()).toString(), this.D() + 2 + 1, this.e() + 2 + 1, true).D(ga.G).M(new StringBuilder().insert(0, this.f.A()).append(": ").append(this.f.e()).toString(), this.D() + 2, this.e() + 2);
                ed2 = this;
            }
            else {
                ca2.K().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(HUD.F).D(ga.G).M(new StringBuilder().insert(0, this.f.A()).append(": ").append(this.f.e()).toString(), this.D() + 2, this.e() + 2, true);
                ed2 = this;
            }
            if (ed2.A() && HUD.descriptions.M()) {
                final int n9 = n2 + 3;
                final int n10 = (int)Ia.M(HUD.F, this.f.D(), 1.0) + 2;
                final int n11 = -12;
                final int n12 = 10;
                ba.M(n, n9, n10, n11, ga.M(n12, n12, n12, 255), 175, 1.0f);
                ca.K().M(HUD.F).D(ga.G).M(this.f.D(), n + 1, n2 - 7);
            }
        }
    }
    
    public ED(final zE d, final nB f) {
        final boolean g = false;
        super(d.M().D() + 4, d.e() + 4, d.M().e() - 8, 14);
        this.G = g;
        this.M = new Timer();
        this.d = d;
        this.M = d.M();
        this.f = f;
    }
}
