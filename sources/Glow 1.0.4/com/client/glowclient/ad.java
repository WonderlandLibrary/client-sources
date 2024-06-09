package com.client.glowclient;

import javax.annotation.*;
import java.io.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;

public class Ad
{
    public final int F;
    public final int I;
    public final int e;
    @Nullable
    public final String a;
    public final int i;
    public final int g;
    public final int l;
    public int K;
    public final File c;
    public final J k;
    public final int H;
    public final int f;
    public final int M;
    public int G;
    public final World d;
    public int L;
    public final EntityPlayer A;
    public final int B;
    public final int b;
    
    public boolean D() {
        return this.L == this.M && this.G == this.i;
    }
    
    public Ad(final J k, final EntityPlayer a, final World d, final File c, @Nullable final String a2, final int l, final int f, final int h, final int b, final int f2, final int g) {
        super();
        this.k = k;
        this.A = a;
        this.d = d;
        this.c = c;
        this.a = a2;
        this.l = l;
        this.f = f;
        this.H = h;
        this.B = b;
        this.F = f2;
        this.g = g;
        this.M = this.l >> 4;
        this.b = this.f >> 4;
        this.i = this.F >> 4;
        this.I = this.g >> 4;
        this.L = this.M;
        this.G = this.i;
        this.e = (this.b - this.M + 1) * (this.I - this.i + 1);
    }
    
    public boolean M() {
        return this.L <= this.b && this.G <= this.I;
    }
    
    public void M() {
        if (!this.M()) {
            return;
        }
        ld.H.debug("Copying chunk at [{},{}] into {}", (Object)this.L, (Object)this.G, (Object)this.c.getName());
        kB.b.M(this.k, this.d, this.L, this.G, this.l, this.f, this.H, this.B, this.F, this.g);
        final Ad ad = this;
        ++ad.K;
        ++ad.L;
        if (ad.L > this.b) {
            final Ad ad2 = this;
            ad2.L = ad2.M;
            ++ad2.G;
        }
    }
}
