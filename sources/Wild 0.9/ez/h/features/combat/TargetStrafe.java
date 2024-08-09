package ez.h.features.combat;

import ez.h.features.another.*;
import ez.h.*;
import ez.h.managers.*;
import java.util.stream.*;
import java.util.*;
import ez.h.event.*;
import ez.h.event.events.*;
import ez.h.utils.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class TargetStrafe extends Feature
{
    OptionBoolean autoJump;
    float strafe;
    OptionBoolean keepTarget;
    OptionBoolean drawRadius;
    OptionBoolean keepDistance;
    aed target;
    OptionBoolean adaptive;
    OptionSlider speed;
    OptionMode motionType;
    OptionSlider range;
    OptionMode point;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.speed.getNum() + " " + this.point.getMode());
        if (TargetStrafe.mc.h.aS() || TargetStrafe.mc.h.F) {
            return;
        }
        if (TargetStrafe.mc.h.ao() || TargetStrafe.mc.h.au() || TargetStrafe.mc.h.au() || TargetStrafe.mc.h.E) {
            return;
        }
        if (this.adaptive.enabled && (this.isInLiquid() || this.isInVoid())) {
            this.strafe = -this.strafe;
        }
        final AntiBot antiBot;
        final List list = (List)TargetStrafe.mc.f.i.stream().filter(aed3 -> {
            antiBot = (AntiBot)Main.getFeatureByName("AntiBot");
            return !antiBot.isEnabled() || antiBot.remove.enabled || !antiBot.isBot(aed3);
        }).filter(bud -> bud != TargetStrafe.mc.h).filter(aed -> !aed.F && aed.cd() > 0.0f).filter(aed2 -> !FriendManager.isFriend(aed2.h_())).filter(vg -> (!KillAura.sorting.isMode("Health") && !KillAura.sorting.isMode("Crosshair")) || TargetStrafe.mc.h.getDistance(vg) <= KillAura.range.getNum()).collect(Collectors.toList());
        if (KillAura.sorting.isMode("Health")) {
            list.sort(Comparator.comparingDouble(vp::cd));
        }
        if (KillAura.sorting.isMode("Distance")) {
            list.sort(Comparator.comparingDouble(vg2 -> TargetStrafe.mc.h.getDistance(vg2)));
        }
        if (KillAura.sorting.isMode("Crosshair")) {
            list.sort(Comparator.comparingDouble(vg3 -> Math.abs(MathUtils.getYawDifference((vg)TargetStrafe.mc.h, vg3))));
        }
        this.target = ((list.size() > 0) ? list.get(0) : null);
        if (this.target == null || this.target.cd() <= 0.0f || this.target.F) {
            return;
        }
        if (TargetStrafe.mc.h.z && this.autoJump.enabled) {
            TargetStrafe.mc.h.fixedJump();
        }
        final String mode = this.point.getMode();
        float n2 = 0.0f;
        switch (mode) {
            case "Backward": {
                n2 = 180.0f;
                break;
            }
            case "Left": {
                n2 = 90.0f;
                break;
            }
            case "Right": {
                n2 = -90.0f;
                break;
            }
            default: {
                n2 = 0.0f;
                break;
            }
        }
        final double[] array = { -Math.sin(Math.toRadians(this.target.v - n2)), Math.cos(Math.toRadians(this.target.v - n2)) };
        if (!this.keepTarget.enabled && TargetStrafe.mc.h.getDistance((vg)this.target) > this.range.getNum() + 0.15f) {
            return;
        }
        final float n3 = MathUtils.Rotations.getDefaultRotations((float)(this.target.p + array[0] * this.range.getNum()), (float)this.target.q, (float)(this.target.r + array[1] * this.range.getNum()))[0];
        if (this.point.isMode("Orbital")) {
            if (TargetStrafe.mc.t.W.i) {
                this.strafe = -1.0f;
            }
            if (TargetStrafe.mc.t.U.i) {
                this.strafe = 1.0f;
            }
            if (this.keepDistance.enabled) {
                this.setMotion(this.getSpeed(), MathUtils.Rotations.getDefaultRotations(this.target)[0], this.strafe, (TargetStrafe.mc.h.getDistance((vg)this.target) > this.range.getNum()) ? 1.0 : -1.0);
            }
            else {
                this.setMotion(this.getSpeed(), MathUtils.Rotations.getDefaultRotations(this.target)[0], this.strafe, (TargetStrafe.mc.h.getDistance((vg)this.target) > this.range.getNum()) ? 1.0 : 0.0);
            }
            TargetStrafe.mc.h.setMotion(0.98f);
        }
        else {
            if (this.motionType.isMode("Motion")) {
                TargetStrafe.mc.h.s = (float)(-Math.sin(Math.toRadians(n3))) * this.speed.getNum();
                TargetStrafe.mc.h.u = Math.cos(Math.toRadians(n3)) * this.speed.getNum();
            }
            if (this.motionType.isMode("Teleport")) {
                TargetStrafe.mc.h.a((double)AnimUtils.lerp((float)TargetStrafe.mc.h.p, (float)(this.target.p + array[0] * this.range.getNum()), this.speed.getNum()), TargetStrafe.mc.h.q, (double)AnimUtils.lerp((float)TargetStrafe.mc.h.r, (float)(this.target.r + array[1] * this.range.getNum()), this.speed.getNum()));
            }
        }
    }
    
    private boolean isInLiquid() {
        if (this.target == null) {
            return false;
        }
        final double radians = Math.toRadians(MathUtils.getYaw(this.target.d()));
        final double n = -Math.sin(radians) * 2.0;
        final double n2 = Math.cos(radians) * 2.0;
        final int n3 = 0;
        return n3 <= 147 + 244 - 305 + 170 && TargetStrafe.mc.f.o(new et(TargetStrafe.mc.h.p + n, TargetStrafe.mc.h.q - n3, TargetStrafe.mc.h.r + n2)).u() instanceof aru;
    }
    
    void setMotion(final double n, final float n2, final double n3, final double n4) {
        double n5 = n4;
        double n6 = n3;
        float n7 = n2;
        if (n4 != 0.0) {
            if (n3 > 0.0) {
                n7 = n2 + ((n4 > 0.0) ? -45 : (0x78 ^ 0x55));
            }
            else if (n3 < 0.0) {
                n7 = n2 + ((n4 > 0.0) ? (0x4A ^ 0x67) : -45);
            }
            n6 = 0.0;
            if (n4 > 0.0) {
                n5 = 1.0;
            }
            else if (n4 < 0.0) {
                n5 = -1.0;
            }
        }
        if (n6 > 0.0) {
            n6 = 1.0;
        }
        else if (n6 < 0.0) {
            n6 = -1.0;
        }
        final double cos = Math.cos(Math.toRadians(n7 + 90.0f));
        final double sin = Math.sin(Math.toRadians(n7 + 90.0f));
        if (this.motionType.isMode("Motion")) {
            TargetStrafe.mc.h.setMotion(0.0f);
            TargetStrafe.mc.h.s = n5 * n * cos + n6 * n * sin;
            TargetStrafe.mc.h.u = n5 * n * sin - n6 * n * cos;
        }
        if (this.motionType.isMode("Teleport")) {
            TargetStrafe.mc.h.a((double)AnimUtils.lerp((float)TargetStrafe.mc.h.p, (float)(TargetStrafe.mc.h.p + n5 * n * cos + n6 * n * sin), this.speed.getNum()), TargetStrafe.mc.h.q, (double)AnimUtils.lerp((float)TargetStrafe.mc.h.r, (float)(TargetStrafe.mc.h.r + n5 * n * sin - n6 * n * cos), this.speed.getNum()));
        }
    }
    
    @Override
    public void updateElements() {
        this.adaptive.display = this.motionType.isMode("Orbital");
        super.updateElements();
    }
    
    @EventTarget
    public void onRender3D(final EventRender3D eventRender3D) {
        if (this.target == null || TargetStrafe.mc.h == null || TargetStrafe.mc.f == null || !this.drawRadius.enabled) {
            return;
        }
        RenderUtils.draw3DCircle((float)(this.target.M + (this.target.p - this.target.M) * TargetStrafe.mc.aj()), (float)(this.target.N + (this.target.q - this.target.N) * TargetStrafe.mc.aj()), (float)(this.target.O + (this.target.r - this.target.O) * TargetStrafe.mc.aj()), this.range.getNum(), 100.0f);
    }
    
    final double getSpeed() {
        double n = this.speed.getNum();
        if (TargetStrafe.mc.h.ca().size() > 0 && TargetStrafe.mc.h.a(uz.a(1))) {
            n *= 1.0 + 0.2 * (TargetStrafe.mc.h.b(uz.a(1)).c() + 1);
        }
        if (TargetStrafe.mc.C() == null || !TargetStrafe.mc.C().b.contains("stormhvh") || TargetStrafe.mc.h.ay > 2) {}
        return n - 0.05;
    }
    
    public TargetStrafe() {
        super("TargetStrafe", "\u0410\u0432\u0442\u043e\u043c\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438 \u043f\u0440\u0435\u0441\u043b\u0435\u0434\u0443\u0435\u0442 \u0438\u0433\u0440\u043e\u043a\u043e\u0432.", Category.COMBAT);
        this.strafe = -1.0f;
        this.speed = new OptionSlider(this, "Speed", 0.2f, 0.0f, 1.0f, OptionSlider.SliderType.BPS);
        this.motionType = new OptionMode(this, "Motion Type", "Motion", new String[] { "Motion", "Teleport" }, 0);
        this.point = new OptionMode(this, "Point", "Orbital", new String[] { "Orbital", "Backward", "Forward", "Left", "Right" }, 0);
        this.drawRadius = new OptionBoolean(this, "Draw Radius", true);
        this.autoJump = new OptionBoolean(this, "Auto Jump", true);
        this.adaptive = new OptionBoolean(this, "Adaptive", true);
        this.range = new OptionSlider(this, "Range", 2.0f, 0.01f, 5.0f, OptionSlider.SliderType.M);
        this.keepDistance = new OptionBoolean(this, "Keep Distance", true);
        this.keepTarget = new OptionBoolean(this, "Keep Target", true);
        this.addOptions(this.motionType, this.drawRadius, this.point, this.speed, this.range, this.autoJump, this.adaptive, this.keepDistance, this.keepTarget);
    }
    
    private boolean isInVoid() {
        if (this.target == null) {
            return false;
        }
        final double radians = Math.toRadians(MathUtils.getYaw(this.target.d()));
        final double n = -Math.sin(radians) * 2.0;
        final double n2 = Math.cos(radians) * 2.0;
        for (int i = 0; i <= 216 + 149 - 170 + 61; ++i) {
            final et et = new et(TargetStrafe.mc.h.p + n, TargetStrafe.mc.h.q - i, TargetStrafe.mc.h.r + n2);
            if (!(TargetStrafe.mc.f.o(et).u() instanceof aom)) {
                return false;
            }
            if (et.q() == 0) {
                return true;
            }
        }
        return !TargetStrafe.mc.h.B && !TargetStrafe.mc.h.z && TargetStrafe.mc.h.L != 0.0f && TargetStrafe.mc.h.t != 0.0 && TargetStrafe.mc.h.ai && !TargetStrafe.mc.h.bO.b && !TargetStrafe.mc.h.ao() && !TargetStrafe.mc.h.m_();
    }
}
