package com.client.glowclient.modules;

import com.client.glowclient.*;
import com.client.glowclient.utils.*;

public class ModuleContainer extends Module
{
    @Override
    public void A() {
        if (this.M()) {
            this.D();
            this.e();
        }
        this.g.M(true);
    }
    
    @Override
    public boolean A() {
        return true;
    }
    
    @Override
    public boolean k() {
        return this.g.M();
    }
    
    public ModuleContainer(final Category category, final String s, final boolean b, final int n, final String s2) {
        final String s3 = "2\u007f\u0000x\u0010t\u001d";
        final String s4 = "-u\u001e}\u0015\u007f\u001d";
        super(category, s, s2);
        this.g = ValueFactory.D(s, Ad.M(s4), "Toggles the mod", b);
        this.c = ValueFactory.M(s, Ad.M(s3), "Keybind of the mod", n);
    }
    
    @Override
    public void k() {
        if (this.D()) {
            this.E();
        }
        this.g.M(false);
    }
    
    public final void B() {
        if (this.k()) {
            this.k();
            return;
        }
        this.A();
    }
}
