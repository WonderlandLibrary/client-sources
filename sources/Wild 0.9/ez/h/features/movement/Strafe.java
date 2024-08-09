package ez.h.features.movement;

import ez.h.*;
import ez.h.utils.*;
import ez.h.event.*;
import ez.h.event.events.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class Strafe extends Feature
{
    OptionSlider speed;
    final double WALK_SPEED = 0.221;
    float recX;
    float recZ;
    OptionMode mode;
    float motion;
    
    @Override
    public void updateElements() {
        this.speed.display = !this.mode.isMode("Matrix");
        super.updateElements();
    }
    
    public static void setSpeed(final double n) {
        double n2 = Strafe.mc.h.e.moveForward;
        double n3 = Strafe.mc.h.e.a;
        float v = Strafe.mc.h.v;
        if (n2 == 0.0 && n3 == 0.0) {
            Strafe.mc.h.s = 0.0;
            Strafe.mc.h.u = 0.0;
        }
        else {
            if (n2 != 0.0) {
                if (n3 > 0.0) {
                    v += ((n2 > 0.0) ? -45 : (0x8 ^ 0x25));
                }
                else if (n3 < 0.0) {
                    v += ((n2 > 0.0) ? (0xEB ^ 0xC6) : -45);
                }
                n3 = 0.0;
                if (n2 > 0.0) {
                    n2 = 1.0;
                }
                else if (n2 < 0.0) {
                    n2 = -1.0;
                }
            }
            Strafe.mc.h.s = n2 * n * Math.cos(Math.toRadians(v + 90.0f)) + n3 * n * Math.sin(Math.toRadians(v + 90.0f));
            Strafe.mc.h.u = n2 * n * Math.sin(Math.toRadians(v + 90.0f)) - n3 * n * Math.cos(Math.toRadians(v + 90.0f));
        }
    }
    
    @EventTarget
    public void onEvent(final EventMotion eventMotion) {
        if (!this.isEnabled()) {
            return;
        }
        this.setSuffix(this.mode.isMode("Matrix") ? this.mode.getMode() : (this.mode.getMode() + " " + this.speed.getNum()));
        if (Strafe.mc.h.ao() || Strafe.mc.h.au() || Strafe.mc.h.au() || Strafe.mc.h.E || Main.getFeatureByName("TargetStrafe").isEnabled()) {
            return;
        }
        if (Strafe.mc.h.isMoving()) {
            if (this.mode.isMode("Matrix") && Utils.getSpeed() < 0.217f) {
                setSpeed(Utils.getSpeed());
            }
            if (this.mode.isMode("Matrix")) {
                return;
            }
            if (this.mode.isMode("Ground") && !Strafe.mc.h.z) {
                return;
            }
            if (this.mode.isMode("Air") && Strafe.mc.h.z) {
                return;
            }
            if (this.mode.isMode("Falling") && Strafe.mc.h.L <= 0.0f) {
                return;
            }
            setSpeed(this.speed.getNum());
        }
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
        if (!this.mode.isMode("Matrix")) {
            return;
        }
        if (eventPacketSend.getPacket() instanceof kf) {
            if (Strafe.mc.h == null || Strafe.mc.f == null) {
                return;
            }
            eventPacketSend.setCancelled(true);
            this.recX = ((kf)eventPacketSend.getPacket()).b / 8000.0f;
            this.recZ = ((kf)eventPacketSend.getPacket()).d / 8000.0f;
            if (Math.sqrt(this.recX * this.recX + this.recZ * this.recZ) > Utils.getSpeed()) {
                setSpeed(Math.sqrt(this.recX * this.recX + this.recZ * this.recZ));
                Strafe.mc.h.t = ((kf)eventPacketSend.getPacket()).c / 8000.0;
            }
            setSpeed(Utils.getSpeed() * 1.1);
        }
    }
    
    @Override
    public void onDisable() {
        Strafe.mc.h.bT = 0.02f;
        Strafe.mc.h.aR = 0.02f;
        super.onDisable();
    }
    
    public Strafe() {
        super("Strafe", "\u041f\u043e\u0437\u0432\u043e\u043b\u044f\u0435\u0442 \u0431\u044b\u0441\u0442\u0440\u043e \u043f\u0435\u0440\u0435\u043c\u0435\u0449\u0430\u0442\u044c\u0441\u044f \u0432 \u0432\u043e\u0437\u0434\u0443\u0445\u0435", Category.MOVEMENT);
        this.motion = 0.2f;
        this.recX = 0.0f;
        this.recZ = 0.0f;
        this.speed = new OptionSlider(this, "Speed", 0.22f, 0.01f, 1.0f, OptionSlider.SliderType.BPS);
        this.mode = new OptionMode(this, "Mode", "Matrix", new String[] { "Matrix", "Always", "Air", "Ground", "Falling" }, 0);
        this.addOptions(this.mode, this.speed);
    }
}
