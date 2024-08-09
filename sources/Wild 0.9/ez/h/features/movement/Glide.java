package ez.h.features.movement;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import java.util.*;
import ez.h.event.*;
import java.util.concurrent.*;

public class Glide extends Feature
{
    OptionBoolean strafe;
    OptionSlider ySpeed;
    OptionSlider speed;
    OptionMode mode;
    boolean damage;
    
    @Override
    public void updateElements() {
        this.speed.display = this.mode.isMode("ReallyWorld");
        this.ySpeed.display = this.mode.isMode("ReallyWorld");
        this.strafe.display = this.mode.isMode("ReallyWorld");
        super.updateElements();
    }
    
    public Glide() {
        super("Glide", "\u041f\u043b\u0430\u0432\u043d\u043e\u0435 \u043f\u0430\u0434\u0435\u043d\u0438\u0435.", Category.MOVEMENT);
        this.speed = new OptionSlider(this, "Speed", 15.0f, 10.0f, 20.0f, OptionSlider.SliderType.NULL);
        this.ySpeed = new OptionSlider(this, "Y Speed", 0.42f, 0.0f, 1.0f, OptionSlider.SliderType.BPS);
        this.strafe = new OptionBoolean(this, "Strafe", true);
        this.damage = false;
        this.mode = new OptionMode(this, "Mode", "Matrix", new String[] { "Matrix", "Matrix2", "ReallyWorld", "Matrix Slow", "Matrix Fast", "Tick" }, 0);
        this.addOptions(this.mode, this.speed, this.ySpeed, this.strafe);
    }
    
    @Override
    public void onDisable() {
        Glide.mc.h.aR = 0.02f;
        Glide.mc.Y.speed = 1.0f;
        Glide.mc.h.bT = 0.02f;
        super.onDisable();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.mode.getMode());
        if (this.mode.isMode("Matrix")) {
            if (this.damage && Glide.mc.h.L > 0.0f && Glide.mc.h.T % 4 == 0) {
                Glide.mc.h.t = -0.003;
            }
            if (Glide.mc.h.L >= 3.0f) {
                this.damage = true;
                Glide.mc.Y.speed = 2.0f;
                Glide.mc.h.t = 0.0;
                Glide.mc.h.d.a((ht)new lk.a(Glide.mc.h.p, Glide.mc.h.q, Glide.mc.h.r, false));
                Glide.mc.h.d.a((ht)new lk.a(Glide.mc.h.p, Glide.mc.h.q, Glide.mc.h.r, true));
                Glide.mc.h.t = -0.003;
                Glide.mc.Y.speed = 1.0f;
                Glide.mc.h.L = 0.0f;
            }
        }
        if (this.mode.isMode("Matrix2")) {
            if (this.damage && Glide.mc.h.L > 0.0f && Glide.mc.h.T % 3 == 0) {
                Glide.mc.h.setMotion(0.8f);
                Glide.mc.h.t = -0.001;
            }
            if (Glide.mc.h.L >= 2.2) {
                this.damage = true;
                Glide.mc.Y.speed = 10.0f;
                final bud h = Glide.mc.h;
                h.t *= 0.08;
                Glide.mc.h.d.a((ht)new lk.a(Glide.mc.h.p, Glide.mc.h.q, Glide.mc.h.r, true));
                Glide.mc.h.d.a((ht)new lk.a(Glide.mc.h.p, Glide.mc.h.q, Glide.mc.h.r, false));
                Glide.mc.Y.speed = 1.0f;
                Glide.mc.h.L = 0.0f;
            }
        }
        if (this.mode.isMode("Tick")) {
            Glide.mc.h.f(false);
            if (!Glide.mc.h.z && Glide.mc.h.L > 0.0f) {
                if (Glide.mc.h.T % 6 == 0) {
                    Glide.mc.h.t = -0.098;
                }
                if (this.counter.hasReached(800.0f)) {
                    final bud h2 = Glide.mc.h;
                    h2.t *= 1.399999976158142;
                    this.counter.reset();
                }
            }
        }
        if (this.mode.isMode("Matrix Fast")) {
            Glide.mc.h.f(false);
            if (!Glide.mc.h.z && Glide.mc.h.L > 0.0f) {
                if (this.counter.hasReached(MathUtils.nextFloat(550.0f, 1080.0f))) {
                    Glide.mc.h.t = -0.001;
                    Glide.mc.h.d.a((ht)new ll((vg)Glide.mc.h));
                    this.counter.reset();
                }
                if (Glide.mc.h.T % (0x9C ^ 0x97) == 0) {
                    Glide.mc.h.t = -0.1484000015258789;
                }
            }
        }
        if (this.mode.isMode("Matrix Slow") && Glide.mc.h.L > 0.0f) {
            Glide.mc.Y.speed = 0.04f;
            if (this.counter.hasReached(320.0f)) {
                this.counter.reset();
                Glide.mc.h.t = -0.0631331839189382;
                Glide.mc.Y.speed = 2.0f;
            }
        }
        if (this.mode.isMode("ReallyWorld")) {
            if (Glide.mc.h.ao()) {
                return;
            }
            Glide.mc.h.t = -0.10000000149011612;
            if (Glide.mc.t.X.i) {
                Glide.mc.h.t = this.ySpeed.getNum();
            }
            if (this.strafe.enabled) {
                Utils.setMotion(Math.hypot(Glide.mc.h.s, Glide.mc.h.u));
            }
            if (Glide.mc.h.bT < this.speed.getNum() / 20.0f) {
                final bud h3 = Glide.mc.h;
                h3.bT *= 1.01f;
            }
            if (Glide.mc.h.T % (0x4A ^ 0x5E) == 0) {
                if (Glide.mc.h.bT > this.speed.getNum() / 30.0f) {
                    final bud h4 = Glide.mc.h;
                    h4.bT *= 0.8f;
                }
                for (final agr agr : Glide.mc.h.bx.c) {
                    if (agr.d().c() == air.cS) {
                        Glide.mc.c.a(0, agr.e, 0, afw.a, (aed)Glide.mc.h);
                        Glide.mc.c.a(0, 6, 0, afw.a, (aed)Glide.mc.h);
                        Glide.mc.h.d.a((ht)new lq((vg)Glide.mc.h, lq.a.i));
                        Glide.mc.h.d.a((ht)new lq((vg)Glide.mc.h, lq.a.i));
                        Glide.mc.c.a(0, 6, 0, afw.a, (aed)Glide.mc.h);
                        Glide.mc.c.a(0, agr.e, 0, afw.a, (aed)Glide.mc.h);
                        Glide.mc.c.e();
                    }
                }
            }
        }
    }
    
    double nextGaussian(final double n) {
        return ThreadLocalRandom.current().nextGaussian() * n;
    }
}
