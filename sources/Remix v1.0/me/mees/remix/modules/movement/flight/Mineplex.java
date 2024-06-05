package me.mees.remix.modules.movement.flight;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import me.satisfactory.base.utils.timer.*;
import net.minecraft.util.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.utils.*;
import pw.stamina.causam.scan.method.model.*;

public class Mineplex extends Mode<Flight>
{
    int slot;
    int slotBefore;
    private TimerUtil timer;
    
    public Mineplex(final Flight parent) {
        super(parent, "Mineplex");
        this.slot = 8;
        this.timer = new TimerUtil();
    }
    
    @Override
    public void onEnable() {
        this.slot = 0;
        this.slotBefore = this.mc.thePlayer.inventory.currentItem;
        this.timer.reset();
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.inventory.currentItem = this.slotBefore;
        this.mc.thePlayer.motionX = 0.0;
        this.mc.thePlayer.motionZ = 0.0;
        this.mc.thePlayer.capabilities.setPlayerWalkSpeed(0.1f);
        final Timer timer = this.mc.timer;
        Timer.timerSpeed = 1.0f;
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        MiscellaneousUtil.setSpeed(MiscellaneousUtil.getBaseMoveSpeed() * 1.3);
        if (this.mc.thePlayer.fallDistance > 5.0f && !this.mc.thePlayer.onGround) {
            MiscellaneousUtil.setSpeed(MiscellaneousUtil.getBaseMoveSpeed() * 1.6);
            this.mc.thePlayer.moveEntity(0.0, -1.0, 0.0);
            this.mc.thePlayer.motionY = 1.2;
            this.mc.thePlayer.fallDistance = 0.0f;
        }
    }
}
