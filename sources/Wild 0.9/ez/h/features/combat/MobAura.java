package ez.h.features.combat;

import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.utils.*;
import ez.h.event.events.*;
import ez.h.event.*;
import java.util.*;

public class MobAura extends Feature
{
    OptionSlider range;
    OptionBoolean crits;
    OptionMode pvp;
    OptionSlider cps;
    vg target;
    List<vg> targets;
    
    float[] getSunriseRotations(final vg vg) {
        final float n = (float)(vg.p - MobAura.mc.h.p);
        final float n2 = (float)(vg.q - (MobAura.mc.h.q + MobAura.mc.h.by()));
        final float n3 = (float)(vg.r - MobAura.mc.h.r);
        final float n4 = (float)(Math.pow(MobAura.mc.t.c * 0.6f + 0.2f, 3.0) * 1.2000000476837158);
        final float n5 = (float)Math.hypot(n, n3);
        final float n6 = (float)(Math.toDegrees(Math.atan2(n3, n)) - 90.0 + (-2.0 + Math.random() * 4.0));
        final float n7 = (float)(Math.toDegrees(-Math.atan2(n2, n5)) - 20.0);
        return new float[] { n6 - n6 % n4, n7 - n7 % n4 };
    }
    
    float[] getCPSDelay(final int n) {
        return new float[] { 1000.0f / n, (float)(1000.0f / n + Math.random() * n) };
    }
    
    boolean isValid(final vg vg) {
        return !vg.F && MobAura.mc.h.getDistance(vg) < this.range.getNum() && (vg instanceof ade || vg instanceof zv || vg instanceof adl);
    }
    
    public MobAura() {
        super("MobAura", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u0430\u0442\u0430\u043a\u0443\u0435\u0442 \u043c\u043e\u0431\u043e\u0432.", Category.COMBAT);
        this.targets = new ArrayList<vg>();
        this.range = new OptionSlider(this, "Range", 3.2f, 0.1f, 6.0f, OptionSlider.SliderType.M);
        this.pvp = new OptionMode(this, "Click Type", "1.9+", new String[] { "1.9+", "1.8" }, 0);
        this.cps = new OptionSlider(this, "CPS", 14.0f, 1.0f, 30.0f, OptionSlider.SliderType.NULLINT);
        this.crits = new OptionBoolean(this, "Crits", true);
        this.addOptions(this.range, this.pvp, this.cps, this.crits);
    }
    
    @Override
    public void updateElements() {
        this.crits.display = this.pvp.isMode("1.9+");
        this.cps.display = this.pvp.isMode("1.8");
    }
    
    void attack(final vg vg) {
        if (this.pvp.isMode("1.9+") && MobAura.mc.h.L < 0.1 && this.crits.enabled) {
            return;
        }
        if (this.pvp.isMode("1.9+")) {
            if (MobAura.mc.h.n(1.0f) < 1.0f || !this.counter.hasReached((float)(long)(Math.random() * 500.0))) {
                return;
            }
        }
        else {
            if (!this.counter.hasReached(MathUtils.nextFloat(this.getCPSDelay((int)this.cps.getNum())[0], this.getCPSDelay((int)this.cps.getNum())[1]))) {
                return;
            }
            this.counter.reset();
        }
        MobAura.mc.h.f(false);
        if (MobAura.mc.h.cO()) {
            MobAura.mc.h.cN();
        }
        MobAura.mc.c.a((aed)MobAura.mc.h, vg);
        this.counter.reset();
        MobAura.mc.h.a(ub.a);
        MobAura.mc.h.ds();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.range.getNum() + " " + this.pvp.getMode());
        this.collectTargets();
        this.sortTargets();
        this.target = (this.targets.isEmpty() ? null : this.targets.get(0));
        if (this.target == null) {
            return;
        }
        eventMotion.setYaw(this.getSunriseRotations(this.target)[0]);
        eventMotion.setPitch(this.getSunriseRotations(this.target)[1]);
        this.attack(this.target);
    }
    
    void collectTargets() {
        this.targets.clear();
        for (final vg vg : MobAura.mc.f.e) {
            if (this.isValid(vg)) {
                this.targets.add(vg);
            }
        }
    }
    
    void sortTargets() {
        this.targets.sort(Comparator.comparingDouble(vg -> MobAura.mc.h.getDistance(vg)));
    }
}
