package ez.h.features.player;

import ez.h.utils.*;
import ez.h.features.movement.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;

public class NoSlowdown extends Feature
{
    OptionMode mode;
    OptionBoolean keepSprint;
    OptionBoolean web;
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        this.setSuffix(this.mode.getMode());
        if (this.web.enabled && NoSlowdown.mc.h.E) {
            NoSlowdown.mc.h.E = (NoSlowdown.mc.h.T % 5 == 0);
            NoSlowdown.mc.h.setMotion(0.9f);
            eventMotion.onGround = NoSlowdown.mc.h.z;
            if (NoSlowdown.mc.h.z && !Utils.isBlockAboveHead()) {
                eventMotion.y += ((NoSlowdown.mc.h.T % 2 == 0) ? 0.05000000074505806 : 0.019999999552965164);
            }
        }
        if ((this.mode.isMode("Matrix2") || this.mode.isMode("Matrix3") || this.mode.isMode("ReallyWorld")) && NoSlowdown.mc.h.cG() && NoSlowdown.mc.h.isMoving() && NoSlowdown.mc.h.L > 0.7) {
            NoSlowdown.mc.h.setMotion(0.96f);
        }
        if ((this.mode.isMode("Matrix3") || this.mode.isMode("ReallyWorld")) && NoSlowdown.mc.h.isMoving() && NoSlowdown.mc.h.B && !NoSlowdown.mc.t.X.i && NoSlowdown.mc.h.cG() && Utils.getSpeed() < 0.217) {
            Strafe.setSpeed(Utils.getSpeed());
        }
    }
    
    @EventTarget
    public void onSlow(final EventSlowdown eventSlowdown) {
        if (this.keepSprint.enabled && !NoSlowdown.mc.h.aV()) {
            NoSlowdown.mc.h.f(true);
        }
        if (!NoSlowdown.mc.h.isMoving()) {
            return;
        }
        if (this.mode.isMode("Matrix")) {
            if (NoSlowdown.mc.h.z) {
                final bub e = NoSlowdown.mc.h.e;
                e.moveForward *= (float)((NoSlowdown.mc.h.T % 4 == 0) ? 0.6 : 1.0);
                final bub e2 = NoSlowdown.mc.h.e;
                e2.a *= (float)((NoSlowdown.mc.h.T % 4 == 0) ? 0.6 : 1.0);
            }
            else {
                final bub e3 = NoSlowdown.mc.h.e;
                e3.moveForward *= (float)0.6;
                final bub e4 = NoSlowdown.mc.h.e;
                e4.a *= (float)0.6;
            }
        }
        eventSlowdown.setCancelled(true);
    }
    
    public NoSlowdown() {
        super("NoSlowdown", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u0437\u0430\u043c\u0435\u0434\u043b\u0435\u043d\u0438\u0435 \u0432\u043e \u0432\u0440\u0435\u043c\u044f \u0438\u0441\u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u043d\u0438\u044f \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u043e\u0432.", Category.MOVEMENT);
        this.mode = new OptionMode(this, "Mode", "ReallyWorld", new String[] { "ReallyWorld", "Matrix3", "Matrix2", "Matrix", "Default" }, 0);
        this.web = new OptionBoolean(this, "Web", true);
        this.keepSprint = new OptionBoolean(this, "Keep Sprint", true);
        this.addOptions(this.mode, this.web, this.keepSprint);
    }
    
    @EventTarget
    public void onPacketSend(final EventPacketSend eventPacketSend) {
        if (eventPacketSend.getPacket() instanceof lq) {
            final lq lq = (lq)eventPacketSend.getPacket();
            if (NoSlowdown.mc.h.cG() && lq.b() == lq.a.d && (!this.keepSprint.enabled || this.mode.isMode("ReallyWorld"))) {
                eventPacketSend.setCancelled(true);
            }
        }
        if (eventPacketSend.getPacket() instanceof lk) {
            final lk lk = (lk)eventPacketSend.getPacket();
            if ((this.mode.isMode("Matrix2") || this.mode.isMode("Matrix3") || this.mode.isMode("ReallyWorld")) && NoSlowdown.mc.h.cG() && NoSlowdown.mc.h.isMoving() && !NoSlowdown.mc.t.X.i) {
                lk.b = ((NoSlowdown.mc.h.T % 2 == 0) ? (lk.b + 6.0E-4) : (lk.b + 2.0E-4));
                lk.f = false;
                NoSlowdown.mc.h.z = false;
            }
        }
    }
}
