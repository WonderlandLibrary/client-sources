package ez.h.features.movement;

import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.utils.*;
import ez.h.event.events.*;

public class NoClip extends Feature
{
    OptionSlider vspeed;
    OptionMode motion;
    OptionSlider speed;
    OptionBoolean onGround;
    OptionBoolean stopMotion;
    
    @EventTarget
    public void onBlockBreak(final EventBlockBreak eventBlockBreak) {
        if (!this.motion.isMode("Matrix")) {
            return;
        }
        if (NoClip.mc.t.Y.i) {
            NoClip.mc.h.b(NoClip.mc.h.p, NoClip.mc.h.q - 3.0E-4, NoClip.mc.h.r);
        }
        if (NoClip.mc.t.T.i) {
            final float n = (float)Math.toRadians(NoClip.mc.h.v);
            NoClip.mc.h.b(NoClip.mc.h.p - rk.a(n) * 3.0E-4, NoClip.mc.h.q, NoClip.mc.h.r + rk.b(n) * 3.0E-4);
        }
    }
    
    public NoClip() {
        super("NoClip", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0432\u0430\u043c \u0445\u043e\u0434\u0438\u0442\u044c \u0447\u0435\u0440\u0435\u0437 \u0441\u0442\u0435\u043d\u044b.", Category.MOVEMENT);
        this.motion = new OptionMode(this, "Motion", "Natural", new String[] { "Matrix", "Natural", "Add" }, 0);
        this.vspeed = new OptionSlider(this, "Vertical Speed", 0.05f, 0.01f, 1.0f, OptionSlider.SliderType.BPS);
        this.speed = new OptionSlider(this, "Speed", 0.2f, 0.01f, 1.0f, OptionSlider.SliderType.BPS);
        this.stopMotion = new OptionBoolean(this, "Stop Motion", false);
        this.onGround = new OptionBoolean(this, "OnGround", true);
        this.addOptions(this.motion, this.vspeed, this.speed, this.stopMotion, this.onGround);
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void updateElements() {
        this.speed.display = this.motion.isMode("Add");
        super.updateElements();
    }
    
    bhe getPos(final aed aed, final float n) {
        return new bhe(aed.p + (float)(-Math.sin(Math.toRadians(aed.v + n))), aed.q, aed.r + (float)Math.cos(Math.toRadians(aed.v + n)));
    }
    
    @EventTarget
    public void onLivingUpdate(final EventLivingUpdate eventLivingUpdate) {
        if (NoClip.mc.h == null || NoClip.mc.f == null) {
            return;
        }
        if (this.motion.isMode("Matrix")) {
            NoClip.mc.h.Q = true;
            if (!NoClip.mc.t.Y.i) {
                NoClip.mc.h.t = 0.0;
            }
            final float n = (float)Math.toRadians(NoClip.mc.h.v);
            if (NoClip.mc.t.T.i) {
                NoClip.mc.c.b(new et(NoClip.mc.h.p + -(rk.a(n) * 0.5), NoClip.mc.h.q, NoClip.mc.h.r + rk.b(n) * 0.5), fa.d);
                NoClip.mc.h.a(ub.a);
            }
            if (NoClip.mc.h.z && NoClip.mc.t.Y.i) {
                NoClip.mc.c.b(new et(NoClip.mc.h.p, NoClip.mc.h.q - 1.0, NoClip.mc.h.r), fa.d);
                NoClip.mc.h.a(ub.a);
            }
        }
        if (this.motion.isMode("Matrix")) {
            return;
        }
        if (this.stopMotion.enabled) {
            NoClip.mc.h.setMotion(0.0f);
        }
        if (NoClip.mc.h.isMoving() && this.motion.isMode("Add")) {
            Utils.setMotion(this.speed.getNum());
        }
        NoClip.mc.h.Q = true;
        NoClip.mc.h.z = this.onGround.enabled;
        NoClip.mc.h.t = 0.0;
        if (NoClip.mc.t.Y.i) {
            NoClip.mc.h.t = -this.vspeed.getNum();
        }
        if (NoClip.mc.t.X.i) {
            NoClip.mc.h.t = this.vspeed.getNum();
        }
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
    }
}
