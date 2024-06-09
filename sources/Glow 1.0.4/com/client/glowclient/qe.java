package com.client.glowclient;

import com.client.glowclient.modules.*;
import com.client.glowclient.modules.server.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import com.client.glowclient.modules.other.*;
import java.util.*;
import com.client.glowclient.utils.*;

public class qe
{
    public int F;
    public BooleanValue I;
    public boolean e;
    private int a;
    public boolean i;
    private int g;
    private int l;
    public BooleanValue K;
    public List<gf> c;
    public NumberValue k;
    public int H;
    public NumberValue f;
    private final String M;
    private Category G;
    public int d;
    private int L;
    public int A;
    public boolean B;
    public int b;
    
    public String M() {
        return this.M;
    }
    
    public void M() {
        final Iterator<gf> iterator2;
        Iterator<gf> iterator = iterator2 = this.c.iterator();
        while (iterator.hasNext()) {
            iterator2.next().M();
            iterator = iterator2;
        }
    }
    
    public Category M() {
        return this.G;
    }
    
    public List<gf> M() {
        return this.c;
    }
    
    public void M(final Category category) {
        final Iterator<NF> iterator = ModuleManager.M().iterator();
    Label_0009:
        while (true) {
            Iterator<NF> iterator2 = iterator;
            while (iterator2.hasNext()) {
                final Module module;
                if ((module = (Module)iterator.next()).M() != category) {
                    iterator2 = iterator;
                }
                else {
                    final zE m = this.M(new zE(this, module));
                    this.M(new jg(m));
                    final Iterator<Gc> iterator3 = xc.D().iterator();
                    while (iterator3.hasNext()) {
                        final NumberValue numberValue;
                        if (module == ModuleManager.M((numberValue = (NumberValue)iterator3.next()).M())) {
                            this.M(new ME(m, numberValue));
                        }
                    }
                    final Iterator<tc> iterator4 = xc.e().iterator();
                    while (iterator4.hasNext()) {
                        final BooleanValue booleanValue;
                        if (module == ModuleManager.M((booleanValue = (BooleanValue)iterator4.next()).M()) && !booleanValue.A().equals("Toggled") && !booleanValue.M().equals("AntiPackets")) {
                            this.M(new ie(m, booleanValue));
                        }
                    }
                    final Iterator<nB> iterator5 = xc.A().iterator();
                    while (iterator5.hasNext()) {
                        final nB nb;
                        if (module == ModuleManager.M((nb = iterator5.next()).M())) {
                            this.M(new ED(m, nb));
                        }
                    }
                    if (module instanceof AntiPackets) {
                        this.M(new Kg(m, "Server Packets", "Modify packets received from the server"));
                        this.M(new LF(m, "Client Packets", "Modify packets sent to the server"));
                        continue Label_0009;
                    }
                    continue Label_0009;
                }
            }
            break;
        }
    }
    
    public void D(final int n) {
        this.f.M((double)n);
    }
    
    public int B() {
        return this.d += 15;
    }
    
    public void A(final int n, final int n2, final int n3) {
        this.M(n2, n3);
        this.b += n;
    }
    
    public int E() {
        return this.k.M();
    }
    
    public int e() {
        return this.L;
    }
    
    private jg M(final jg jg) {
        this.c.add(this.c.indexOf(jg.M()) + 1, jg);
        jg.M().M().add(jg);
        return jg;
    }
    
    public int k() {
        int g = this.g;
        final Iterator<gf> iterator = this.c.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().D()) {
                g += 15;
            }
        }
        return g;
    }
    
    public void M(final int n, final int n2, final boolean b) {
        this.D(n, n2);
        final ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc);
        if (this.e) {
            this.D(n - this.A);
            this.M(n2 - this.H);
        }
        this.M(this.E() - this.b * 8);
        if (this.E() + this.k() < 15) {
            this.M(-this.k() + 15);
        }
        if (this.E() > scaledResolution.getScaledHeight() - 15) {
            this.M(scaledResolution.getScaledHeight() - 13);
        }
        GL11.glPushMatrix();
        GL11.glPushAttrib(1284);
        this.d = this.E();
        if (b) {
            ba.M(this.D(), this.E(), this.e(), 13, ga.M(HUD.red.M(), HUD.green.M(), HUD.blue.M(), 225));
            Ia.M(HUD.F, this.M(), this.D() + 2, this.E() + 2, true, ga.G, 1.0);
            if (!this.B) {
                final int n3 = this.K.M() ? ga.H : ga.M;
                final int n4 = this.D() + 89;
                final int n5 = this.E() + 2;
                final int n6 = 9;
                ba.M(n4, n5, n6, n6, n3);
            }
        }
        if (this.I.M()) {
            final int d = this.D();
            final int n7 = this.E() + 13;
            final int e = this.e();
            final int n8 = this.A() + 4 - 13;
            final int n9 = 0;
            ba.M(d, n7, e, n8, ga.M(n9, n9, n9, 150));
            if (this.B) {
                final int i = 1;
                this.F = 0;
                int n10 = i;
                while (i < this.M() + 1) {
                    final gf m = this.M();
                    ++this.F;
                    final gf gf = m;
                    ++n10;
                    gf.M(n, n2);
                }
            }
        }
        final float n11 = 0.0f;
        GL11.glColor3f(n11, n11, n11);
        GL11.glPopMatrix();
        GL11.glPopAttrib();
    }
    
    public gf M() {
        int n = 0;
        int n2 = 0;
        qe qe = this;
        gf gf;
        while (true) {
            if ((gf = qe.c.get(n2)).D()) {
                if (this.F == 0 || n >= this.F) {
                    break;
                }
                ++n;
            }
            ++n2;
            qe = this;
        }
        return gf;
    }
    
    public void M(final char c, final int n) {
        final Iterator<gf> iterator = this.c.iterator();
        while (iterator.hasNext()) {
            final gf gf;
            if ((gf = iterator.next()).D()) {
                gf.M(c, n);
            }
        }
    }
    
    public int A() {
        return Math.min(this.k() + 14, this.k());
    }
    
    public void D(final int n, final int n2, final int n3) {
        this.D(n, n2);
        qe qe = null;
        Label_0154: {
            if (this.B ? this.M() : (this.M() && (this.D() + 89 > n || n > this.D() + 89 + 9 || this.E() + 2 > n2 || n2 > this.E() + 2 + 10))) {
                if (n3 == 0) {
                    qe = this;
                    this.e = true;
                    this.A = n - this.D();
                    this.H = n2 - this.E();
                    break Label_0154;
                }
                if (n3 == 1) {
                    this.I.M(!this.I.M());
                }
            }
            qe = this;
        }
        if (!qe.B && this.D() + 89 <= n && n <= this.D() + 89 + 9 && this.E() + 2 <= n2 && n2 <= this.E() + 2 + 10) {
            this.K.M(!this.K.M());
        }
        if (n3 == 2) {
            this.M(this.a);
            this.D(this.l);
        }
        final Iterator<gf> iterator = this.c.iterator();
        while (iterator.hasNext()) {
            final gf gf;
            if ((gf = iterator.next()).D()) {
                gf.D(n, n2, n3);
            }
        }
    }
    
    public qe(final int l, final int a, final String m, final Category g, final boolean b) {
        final boolean b2 = true;
        final int g2 = 12;
        final int i = 100;
        final boolean e = false;
        final boolean b3 = false;
        final int n = 0;
        final int n2 = 0;
        super();
        this.A = n2;
        this.H = n2;
        this.F = n;
        this.d = n;
        this.b = (b3 ? 1 : 0);
        this.i = b3;
        this.e = e;
        this.c = Collections.synchronizedList(new ArrayList<gf>());
        this.f = ValueFactory.M(new StringBuilder().insert(0, "Window.").append(m).toString(), "X", l);
        this.k = ValueFactory.M(new StringBuilder().insert(0, "Window.").append(m).toString(), "Y", a);
        this.a = a;
        this.l = l;
        this.L = i;
        this.g = g2;
        this.M = m;
        this.G = g;
        this.I = ValueFactory.M(new StringBuilder().insert(0, "Window.").append(m).toString(), "isOpen", "is window open", b);
        this.B = b2;
    }
    
    public boolean M() {
        return this.i;
    }
    
    public void M(final int n) {
        this.k.M((double)n);
    }
    
    private ME M(final ME me) {
        this.c.add(this.c.indexOf(me.M()) + 1, me);
        me.M().M().add(me);
        return me;
    }
    
    public qe(final int l, final int a, final String m, final boolean b, final boolean b2) {
        final boolean b3 = false;
        final int g = 12;
        final int i = 100;
        final boolean e = false;
        final boolean b4 = false;
        final int n = 0;
        final int n2 = 0;
        super();
        this.A = n2;
        this.H = n2;
        this.F = n;
        this.d = n;
        this.b = (b4 ? 1 : 0);
        this.i = b4;
        this.e = e;
        this.c = Collections.synchronizedList(new ArrayList<gf>());
        this.f = ValueFactory.M(new StringBuilder().insert(0, "Window.").append(m).toString(), "X", l);
        this.k = ValueFactory.M(new StringBuilder().insert(0, "Window.").append(m).toString(), "Y", a);
        this.a = a;
        this.l = l;
        this.L = i;
        this.g = g;
        this.M = m;
        this.G = Category.NONE;
        this.I = ValueFactory.M(new StringBuilder().insert(0, "Window.").append(m).toString(), "isOpen", "is window open", b);
        this.B = b3;
        this.K = ValueFactory.M(new StringBuilder().insert(0, "Window.").append(m).toString(), "isPinned", "is window pinned", b2);
    }
    
    public void D(final int n, final int n2) {
        final int d = this.D();
        final int e = this.E();
        final int n3 = d + this.L;
        final int n4 = e + this.g;
        this.i = (d <= n && n <= n3 && e <= n2 && n2 <= n4);
    }
    
    private zE M(final zE ze) {
        this.c.add(ze);
        xc.M(ze.b);
        return ze;
    }
    
    public int D() {
        return this.f.M();
    }
    
    private void M(final int n, final int n2) {
        final int d = this.D();
        final int e = this.E();
        final int n3 = d + this.L;
        final int n4 = e + this.A();
        this.i = (d <= n && n <= n3 && e <= n2 && n2 <= n4);
    }
    
    public int M() {
        int n = 0;
        final Iterator<gf> iterator = this.c.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().D()) {
                ++n;
            }
        }
        return n;
    }
    
    public void M(final int n, final int n2, final int n3) {
        this.D(n, n2);
        if (this.e) {
            this.e = false;
        }
        final Iterator<gf> iterator = this.c.iterator();
        while (iterator.hasNext()) {
            final gf gf;
            if ((gf = iterator.next()).D()) {
                gf.M(n, n2, n3);
            }
        }
    }
    
    private ED M(final ED ed) {
        this.c.add(this.c.indexOf(ed.M()) + 1, ed);
        ed.M().M().add(ed);
        return ed;
    }
    
    private ie M(final ie ie) {
        this.c.add(this.c.indexOf(ie.M()) + 1, ie);
        ie.M().M().add(ie);
        return ie;
    }
    
    private xf M(final xf xf) {
        this.c.add(this.c.indexOf(xf.M()) + 1, xf);
        xf.M().M().add(xf);
        return xf;
    }
}
