package com.klintos.twelve.mod;

import java.util.List;

import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.klintos.twelve.mod.value.ValueDouble;

import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;

public class Speed extends Mod
{
    boolean move;
    private ValueDouble speed;
    
    
    
    public Speed() {
        super("Speed", 23, ModCategory.EXPLOITS);
        this.addValue(this.speed = new ValueDouble("Speed", 1.9, 1.0, 5.0, 1));
    }
    
    @EventTarget
    public void onPreUpdate(EventPreUpdate event) {
        final boolean strafe = Speed.mc.gameSettings.keyBindForward.pressed && (Speed.mc.gameSettings.keyBindRight.pressed || Speed.mc.gameSettings.keyBindLeft.pressed);
        final double x = Speed.mc.thePlayer.posX + Speed.mc.thePlayer.motionX * (this.speed.getValue() - (strafe ? 0.06 : 0.0));
        final double z = Speed.mc.thePlayer.posZ + Speed.mc.thePlayer.motionZ * (this.speed.getValue() - (strafe ? 0.06 : 0.0));
        if (this.move && mc.thePlayer.onGround) {
            Speed.mc.thePlayer.setPosition(x, Speed.mc.thePlayer.posY, z);
        }
        this.move = !this.move;
    }
    
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
}
