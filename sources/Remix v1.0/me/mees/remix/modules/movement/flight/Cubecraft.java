package me.mees.remix.modules.movement.flight;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import net.minecraft.util.*;
import me.satisfactory.base.events.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.potion.*;

public class Cubecraft extends Mode<Flight>
{
    public Cubecraft(final Flight parent) {
        super(parent, "Cubecraft");
    }
    
    @Override
    public void onEnable() {
        final Timer timer = this.mc.timer;
        Timer.timerSpeed = 0.1f;
        this.mc.thePlayer.moveEntity(0.0, 1.0, 0.0);
        this.mc.thePlayer.motionX = 0.0;
        this.mc.thePlayer.motionZ = 0.0;
        this.mc.thePlayer.motionY = 0.0;
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.motionX = 0.0;
        this.mc.thePlayer.motionZ = 0.0;
        this.mc.thePlayer.motionY = 0.0;
        this.mc.thePlayer.setSpeed(0.0);
        final Timer timer = this.mc.timer;
        Timer.timerSpeed = 1.0f;
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        final Timer timer = this.mc.timer;
        Timer.timerSpeed = 0.2f;
        if (this.mc.thePlayer != null && this.mc.theWorld != null) {
            this.mc.thePlayer.setSpeed(this.mc.thePlayer.isMoving() ? (this.getBaseMoveSpeed() * 7.7) : 0.0);
        }
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
}
