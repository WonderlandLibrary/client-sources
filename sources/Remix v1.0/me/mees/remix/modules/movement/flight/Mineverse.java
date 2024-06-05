package me.mees.remix.modules.movement.flight;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import me.satisfactory.base.events.*;
import net.minecraft.util.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.potion.*;

public class Mineverse extends Mode<Flight>
{
    public Mineverse(final Flight parent) {
        super(parent, "Mineverse");
    }
    
    @Subscriber
    public void eventMove(final EventMove event) {
        if (this.mc.thePlayer.isSneaking()) {
            event.y = 0.0;
            event.z = 0.0;
            event.x = 0.0;
        }
        else {
            this.mc.thePlayer.setSprinting(true);
            final double speeddd = 1.0;
            event.y = 0.0;
            final MovementInput movementInput = this.mc.thePlayer.movementInput;
            final double n = MovementInput.moveForward * this.getBaseMoveSpeed() * speeddd * Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            final MovementInput movementInput2 = this.mc.thePlayer.movementInput;
            event.setZ(n - MovementInput.moveStrafe * this.getBaseMoveSpeed() * speeddd * Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f)));
            final MovementInput movementInput3 = this.mc.thePlayer.movementInput;
            final double n2 = MovementInput.moveForward * this.getBaseMoveSpeed() * speeddd * Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            final MovementInput movementInput4 = this.mc.thePlayer.movementInput;
            event.setX(n2 + MovementInput.moveStrafe * this.getBaseMoveSpeed() * speeddd * Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f)));
            this.mc.thePlayer.setSprinting(true);
        }
    }
    
    public double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
}
