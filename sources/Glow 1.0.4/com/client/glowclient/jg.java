package com.client.glowclient;

import org.lwjgl.input.*;
import com.client.glowclient.modules.other.*;
import org.lwjgl.opengl.*;

public class jg extends gf
{
    private final zE B;
    private boolean b;
    private qe M;
    
    @Override
    public void M(final char c, final int n) {
        if (this.b) {
            if (n == 211 || n == 14 || n == 1) {
                final boolean b = false;
                this.B.M().M(-1);
                this.b = b;
                return;
            }
            final boolean b2 = false;
            this.B.M().M(n);
            this.b = b2;
        }
    }
    
    @Override
    public int k() {
        if (this.A()) {
            final int n = 150;
            return ga.M(n, n, n, 50);
        }
        final int n2 = 0;
        return ga.M(n2, n2, n2, 50);
    }
    
    public jg(final zE b) {
        final boolean b2 = false;
        super(b.M().D() + 4, b.e() + 4, b.M().e() - 8, 14);
        this.b = b2;
        this.B = b;
        this.M = b.M();
    }
    
    @Override
    public boolean D() {
        return this.B.M() && this.B.D();
    }
    
    @Override
    public String M() {
        return new StringBuilder().insert(0, "Bind: ").append(this.B.M().M()).toString();
    }
    
    @Override
    public void M(final int n, final int n2) {
        String string;
        if (!this.b) {
            if (this.B.M().M() == -1) {
                string = "Bind: NONE";
            }
            else {
                string = new StringBuilder().insert(0, "Bind: ").append(Keyboard.getKeyName(this.B.M().M())).toString();
            }
        }
        else {
            string = "Press a key...";
        }
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
        final ea f = HUD.F;
        final ca ca2 = ca;
        jg jg;
        if (f != null) {
            ca2.K().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(HUD.F).D(ga.G).M(string, this.D() + 2 + 1, this.e() + 2 + 1, true).D(ga.G).M(string, this.D() + 2, this.e() + 2);
            jg = this;
        }
        else {
            ca2.K().M(com.client.glowclient.ca::D).M(com.client.glowclient.ca::d).M(HUD.F).D(ga.G).M(string, this.D() + 2, this.e() + 2, true);
            jg = this;
        }
        if (jg.A() && HUD.descriptions.M()) {
            final int n5 = n2 + 3;
            final int n6 = (int)Ia.M(HUD.F, "Keybind of mod", 1.0) + 2;
            final int n7 = -12;
            final int n8 = 10;
            ba.M(n, n5, n6, n7, ga.M(n8, n8, n8, 255), 175, 1.0f);
            ca.K().M(HUD.F).D(ga.G).M("Keybind of mod", n + 1, n2 - 7);
        }
    }
    
    public zE M() {
        return this.B;
    }
    
    @Override
    public void D(final int n, final int n2, final int n3) {
        this.D(n, n2);
        if (this.A() && n3 == 0) {
            this.b = true;
        }
    }
}
