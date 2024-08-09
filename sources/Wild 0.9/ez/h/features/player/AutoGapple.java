package ez.h.features.player;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.event.events.*;
import ez.h.*;
import ez.h.features.combat.*;
import java.util.*;

public class AutoGapple extends Feature
{
    OptionSlider onHealth;
    OptionSlider delay;
    boolean isEating;
    OptionBoolean stormhvh;
    OptionBoolean autoEquip;
    
    @Override
    public void onDisable() {
        if (AutoGapple.mc.h.cG() && AutoGapple.mc.h.cJ().c() == air.ar) {
            AutoGapple.mc.t.ad.k();
        }
        super.onDisable();
    }
    
    public AutoGapple() {
        super("AutoGapple", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0435\u0441\u0442 \u0437\u043e\u043b\u043e\u0442\u044b\u0435 \u044f\u0431\u043b\u043e\u043a\u0438.", Category.PLAYER);
        this.onHealth = new OptionSlider(this, "On Health", 16.0f, 1.0f, 20.0f, OptionSlider.SliderType.NULLINT);
        this.delay = new OptionSlider(this, "Delay", 750.0f, 1.0f, 6000.0f, OptionSlider.SliderType.MS);
        this.stormhvh = new OptionBoolean(this, "Storm HvH", false);
        this.autoEquip = new OptionBoolean(this, "Auto Equip", true);
        this.addOptions(this.onHealth, this.autoEquip, this.delay, this.stormhvh);
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend eventPacketSend) {
        if (eventPacketSend.getPacket() instanceof mb && this.stormhvh.isEnabled() && ((mb)eventPacketSend.getPacket()).a() == ub.b) {
            Debug.executeLaterNotStack(2600L, () -> AutoGapple.mc.h.d.a((ht)new lp(lp.a.f, et.a, fa.c)));
        }
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.onHealth.getNum() + "");
        if (!this.isEnabled()) {
            return;
        }
        if (Main.getFeatureByName("AutoTotem").isEnabled() && AutoGapple.mc.h.cd() + AutoGapple.mc.h.cD() <= AutoTotem.health.getNum() && this.autoEquip.enabled) {
            return;
        }
        final ain c = AutoGapple.mc.h.cp().c();
        final boolean a = AutoGapple.mc.h.dt().a(c);
        if (Main.getFeatureByName("GappleCooldown").isEnabled()) {
            if (AutoGapple.mc.t.ad.e() && this.isEating && (AutoGapple.mc.h.cd() > this.onHealth.getNum() || (a && c instanceof aik))) {
                AutoGapple.mc.t.ad.i = false;
                this.isEating = false;
            }
        }
        else if (AutoGapple.mc.t.ad.e() && AutoGapple.mc.h.cd() > this.onHealth.getNum() && this.isEating) {
            AutoGapple.mc.t.ad.i = false;
            this.isEating = false;
        }
        if (this.counter.hasReached(this.delay.getNum())) {
            if (c instanceof aik) {
                if (Main.getFeatureByName("GappleCooldown").isEnabled()) {
                    if (AutoGapple.mc.h.cd() < this.onHealth.getNum() && !a) {
                        AutoGapple.mc.t.ad.i = true;
                        this.isEating = true;
                    }
                }
                else if (AutoGapple.mc.h.cd() < this.onHealth.getNum()) {
                    AutoGapple.mc.t.ad.i = true;
                    this.isEating = true;
                }
            }
            else if (this.autoEquip.enabled && AutoGapple.mc.h.cd() < this.onHealth.getNum()) {
                AutoGapple.mc.h.d.a((ht)new lq((vg)AutoGapple.mc.h, lq.a.h));
                for (final agr agr : AutoGapple.mc.h.bx.c) {
                    if (agr.e != (0x5D ^ 0x70)) {
                        if (AutoGapple.mc.h.cp().c() == air.ar) {
                            continue;
                        }
                        if (agr.d().c() != air.ar || !this.counter.hasReached(200.0f)) {
                            continue;
                        }
                        AutoGapple.mc.c.a(AutoGapple.mc.h.bx.d, agr.e, 0, afw.a, (aed)AutoGapple.mc.h);
                        AutoGapple.mc.c.a(AutoGapple.mc.h.bx.d, 0x64 ^ 0x49, 0, afw.a, (aed)AutoGapple.mc.h);
                        AutoGapple.mc.c.a(AutoGapple.mc.h.bx.d, agr.e, 0, afw.a, (aed)AutoGapple.mc.h);
                        this.counter.reset();
                    }
                }
            }
            this.counter.reset();
        }
    }
}
