package cn.Hanabi.modules.Movement;

import cn.Hanabi.value.*;
import cn.Hanabi.modules.*;
import com.darkmagician6.eventapi.*;
import ClassSub.*;
import cn.Hanabi.events.*;

public class Fly extends Mod
{
    Value<String> mode;
    Value<Boolean> lagback;
    Class142 GlobalHypixel;
    Class131 MotionFly;
    
    
    public Fly() {
        super("Fly", Category.MOVEMENT);
        this.mode = new Value<String>("Fly", "Mode", 0);
        this.lagback = new Value<Boolean>("Fly_LagBackChecks", true);
        this.GlobalHypixel = new Class142();
        this.MotionFly = new Class131();
        this.mode.addValue("Motion");
        this.mode.addValue("Hypixel");
    }
    
    @EventTarget
    public void onPacket(final EventPacket eventPacket) {
        eventPacket.getPacket();
        if (this.mode.isCurrentMode("Hypixel")) {
            this.GlobalHypixel.onPacket(eventPacket);
        }
    }
    
    @EventTarget
    public void onUpdate(final EventPreMotion eventPreMotion) {
        if (this.mode.isCurrentMode("Hypixel")) {
            this.setDisplayName("Hypixel");
            this.GlobalHypixel.onPre(eventPreMotion);
            return;
        }
        if (this.mode.isCurrentMode("Motion")) {
            this.setDisplayName("Motion");
            this.MotionFly.onPre();
        }
    }
    
    @EventTarget
    public void onPullback(final EventPullback eventPullback) {
        if (this.lagback.getValueState()) {
            Class120.sendClientMessage("(LagBackCheck) Fly Disabled", Class84.Class307.WARNING);
            this.set(false);
        }
        if (this.mode.isCurrentMode("Hypixel")) {
            this.GlobalHypixel.onLagback(eventPullback);
        }
    }
    
    @EventTarget
    public void onMove(final EventMove eventMove) {
        if (this.mode.isCurrentMode("Hypixel")) {
            this.GlobalHypixel.onMove(eventMove);
        }
    }
    
    public void onEnable() {
        if (this.mode.isCurrentMode("Hypixel")) {
            this.GlobalHypixel.onEnable();
            return;
        }
        super.onEnable();
    }
    
    public void onDisable() {
        if (this.mode.isCurrentMode("Hypixel")) {
            this.GlobalHypixel.onDisable();
            return;
        }
        super.onDisable();
    }
}
