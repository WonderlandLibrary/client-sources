package ez.h.features.combat;

import ez.h.event.*;
import ez.h.features.*;
import ez.h.event.events.*;

public class SuperBow extends Feature
{
    boolean shooting;
    boolean isFalling;
    long lastShootTime;
    
    @Override
    public void onEnable() {
        this.isFalling = false;
        this.shooting = false;
        this.lastShootTime = 0L;
        super.onEnable();
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
        if (eventPacketSend.packet instanceof lk) {
            final lk lk = (lk)eventPacketSend.getPacket();
            lk.f = false;
            lk.g = false;
        }
    }
    
    public SuperBow() {
        super("SuperBow", "\u0423\u0432\u0435\u043b\u0438\u0447\u0438\u0432\u0430\u0435\u0442 \u0443\u0440\u043e\u043d \u043e\u0442 \u043b\u0443\u043a\u0430.", Category.ANOTHER);
        this.isFalling = false;
    }
    
    @Override
    public void onDisable() {
        SuperBow.mc.h.F = false;
        super.onDisable();
    }
    
    @EventTarget
    public void onMotion(final EventMotion eventMotion) {
        SuperBow.mc.h.M = SuperBow.mc.h.p;
        SuperBow.mc.h.N = SuperBow.mc.h.q;
        SuperBow.mc.h.O = SuperBow.mc.h.r;
        SuperBow.mc.h.x = SuperBow.mc.h.v;
        SuperBow.mc.h.y = SuperBow.mc.h.w;
        SuperBow.mc.h.ay = 0;
        SuperBow.mc.h.V = 0;
        SuperBow.mc.h.bB = SuperBow.mc.h.bC;
        SuperBow.mc.h.aJ = SuperBow.mc.h.aK;
        SuperBow.mc.h.h(0.0, 0.0, 0.0);
        SuperBow.mc.h.F = true;
    }
}
