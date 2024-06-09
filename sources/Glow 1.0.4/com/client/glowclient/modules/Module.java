package com.client.glowclient.modules;

import com.client.glowclient.utils.*;
import net.minecraft.client.settings.*;
import net.minecraft.client.*;
import com.client.glowclient.events.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.client.registry.*;
import net.minecraftforge.fml.client.*;
import java.util.*;
import net.minecraftforge.common.*;
import com.client.glowclient.*;

public abstract class Module
{
    public BooleanValue g;
    public int l;
    private final String K;
    public CB c;
    public boolean k;
    public static Module H;
    private final String f;
    private final Category M;
    public Timer G;
    public KeyBinding d;
    private boolean L;
    public int A;
    public static Minecraft B;
    public int b;
    
    public final String k() {
        return this.K;
    }
    
    @SubscribeEvent
    public void M(final EventUpdate eventUpdate) {
        this.M();
    }
    
    public void M(final String s, final int n) {
        ClientRegistry.registerKeyBinding(this.d = new KeyBinding(s, (n == -1) ? 0 : n, "GlowClient"));
    }
    
    static {
        Module.B = FMLClientHandler.instance().getClient();
    }
    
    public abstract boolean k();
    
    public void E() {
    }
    
    public Category M() {
        return this.M;
    }
    
    public boolean A() {
        return false;
    }
    
    public void e() {
        final Random random = new Random();
        final int n = 255;
        final int n2 = 255;
        final Random random2 = random;
        this.A = random2.nextInt(255) + 50;
        this.l = random2.nextInt(n2) + 50;
        this.b = random.nextInt(n) + 50;
    }
    
    public void k() {
        if (this.D()) {
            this.E();
        }
    }
    
    public final boolean D() {
        if (this.L) {
            MinecraftForge.EVENT_BUS.unregister((Object)this);
            final boolean b = true;
            this.L = false;
            return b;
        }
        return false;
    }
    
    public int M() {
        return this.c.M();
    }
    
    public void A() {
        if (this.M()) {
            this.D();
            this.e();
        }
    }
    
    public final String A() {
        return this.f;
    }
    
    public final boolean M() {
        if (!Glow.b) {
            return false;
        }
        if (!this.L) {
            MinecraftForge.EVENT_BUS.register((Object)this);
            return this.L = true;
        }
        return false;
    }
    
    public Module(final Category m, final String k, final String f) {
        final boolean i = false;
        final boolean l = false;
        super();
        this.L = l;
        this.k = i;
        this.G = new Timer();
        Module.H = this;
        this.K = k;
        this.f = f;
        this.M = m;
    }
    
    public void D() {
    }
    
    public Module(final Category category, final String s) {
        this(category, s, "");
    }
    
    public void M(final int n) {
        this.c.M(n);
    }
    
    public String D() {
        if (!this.M().equals("") && this.M() != null) {
            return new StringBuilder().insert(0, this.k()).append("§f[§f").append(this.M()).append("§f]").toString();
        }
        return this.k();
    }
    
    public void M() {
    }
    
    public String M() {
        return "";
    }
}
