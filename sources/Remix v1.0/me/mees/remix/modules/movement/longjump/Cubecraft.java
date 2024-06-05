package me.mees.remix.modules.movement.longjump;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import java.math.*;
import pw.stamina.causam.scan.method.model.*;
import me.satisfactory.base.events.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.client.entity.*;
import net.minecraft.potion.*;

public class Cubecraft extends Mode<Longjump>
{
    private int stage;
    private double moveSpeed;
    private double lastDist;
    
    public Cubecraft(final Longjump parent) {
        super(parent, "Cubecraft");
    }
    
    public double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    @Override
    public void onEnable() {
        final Timer timer = this.mc.timer;
        Timer.timerSpeed = 0.3f;
        this.moveSpeed = this.getBaseMoveSpeed();
        this.mc.thePlayer.motionX = 0.0;
        this.mc.thePlayer.motionZ = 0.0;
        this.stage = 0;
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        final Timer timer = this.mc.timer;
        Timer.timerSpeed = 1.0f;
        this.mc.thePlayer.motionY = -0.14;
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        final double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
        final double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }
    
    @Subscriber
    public void eventMove(final EventMove event) {
        if (this.mc.thePlayer.motionY < 0.0) {
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            thePlayer.motionY -= 0.14;
            event.y -= 0.14;
        }
        if (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) {
            final Timer timer = this.mc.timer;
            Timer.timerSpeed = 0.3f;
        }
        else {
            final Timer timer2 = this.mc.timer;
            Timer.timerSpeed = 1.0f;
        }
        final MovementInput movementInput = this.mc.thePlayer.movementInput;
        float forward = MovementInput.moveForward;
        float strafe = MovementInput.moveStrafe;
        float yaw = this.mc.thePlayer.rotationYaw;
        final double round = this.round(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3);
        if (this.stage == 1 && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
            this.stage = 2;
            this.moveSpeed = 1.38 * this.getBaseMoveSpeed() - 0.01;
        }
        else if (this.stage == 2) {
            this.stage = 3;
            this.mc.thePlayer.motionY = 0.424;
            event.y = 0.424;
            this.moveSpeed *= 2.149;
        }
        else if (this.stage == 3) {
            this.stage = 4;
            final double difference = 0.66 * (this.lastDist - this.getBaseMoveSpeed());
            this.moveSpeed = (this.lastDist - difference) * 2.5;
        }
        else {
            if (this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0)).size() > 0 || this.mc.thePlayer.isCollidedVertically) {
                this.stage = 1;
            }
            this.moveSpeed = this.lastDist - this.lastDist / 159.0;
        }
        if (!this.mc.thePlayer.onGround) {
            this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed() * 9.0);
        }
        else {
            this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed() * 0.0);
        }
        if (forward == 0.0f && strafe == 0.0f) {
            event.x = 0.0;
            event.z = 0.0;
        }
        else if (forward != 0.0f) {
            if (strafe >= 1.0f) {
                yaw += ((forward > 0.0f) ? -45 : 45);
                strafe = 0.0f;
            }
            else if (strafe <= -1.0f) {
                yaw += ((forward > 0.0f) ? 45 : -45);
                strafe = 0.0f;
            }
            if (forward > 0.0f) {
                forward = 1.0f;
            }
            else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        if (this.mc.thePlayer.onGround) {
            this.moveSpeed = 0.0;
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        event.x = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
        event.z = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (this.mc.thePlayer != null && this.mc.theWorld != null && this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
}
