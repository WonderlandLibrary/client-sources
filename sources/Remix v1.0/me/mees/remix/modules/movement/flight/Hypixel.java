package me.mees.remix.modules.movement.flight;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import pw.stamina.causam.scan.method.model.*;
import java.math.*;
import me.satisfactory.base.events.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class Hypixel extends Mode<Flight>
{
    int counter;
    private int stage;
    private double moveSpeed;
    private double lastDist;
    
    public Hypixel(final Flight parent) {
        super(parent, "Hypixel");
        this.counter = 0;
    }
    
    @Override
    public void onDisable() {
        this.moveSpeed = 0.2873;
        this.mc.thePlayer.motionX = 0.0;
        this.mc.thePlayer.motionZ = 0.0;
        this.stage = 0;
        Timer.timerSpeed = 1.0f;
        this.mc.thePlayer.stepHeight = 0.5f;
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        this.mc.thePlayer.stepHeight = 0.0f;
        final double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
        final double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        ++this.counter;
        if (this.counter != 1) {
            if (this.counter == 2) {
                this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-10, this.mc.thePlayer.posZ);
                this.counter = 0;
            }
        }
        this.mc.thePlayer.motionY = 0.0;
        this.mc.thePlayer.onGround = true;
        if (this.mc.gameSettings.keyBindJump.pressed) {
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 0.4, this.mc.thePlayer.posZ);
        }
    }
    
    public double round(final double value, final int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    @Subscriber
    public void eventMove(final EventMove event) {
        if (!((Flight)this.parent).findSettingByName("Hypixel Boost").booleanValue()) {
            return;
        }
        final MovementInput movementInput = this.mc.thePlayer.movementInput;
        float forward = MovementInput.moveForward;
        float strafe = MovementInput.moveStrafe;
        float yaw = this.mc.thePlayer.rotationYaw;
        final double round = this.round(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3);
        if (this.stage == 1 && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
            this.stage = 2;
            this.moveSpeed = 0.42095;
        }
        else if (this.stage == 2) {
            this.stage = 3;
            this.mc.thePlayer.motionY = 0.399399995803833;
            event.y = 0.399399995803833;
            this.moveSpeed *= 2.149;
        }
        else if (this.stage == 3) {
            this.stage = 4;
            final double difference = 0.83 * (this.lastDist - 0.2873);
            this.moveSpeed = (this.lastDist - difference) * ((Flight)this.parent).findSettingByName("Boost").doubleValue();
        }
        else {
            if (this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0)).size() > 0 || this.mc.thePlayer.isCollidedVertically) {
                this.stage = 1;
            }
            this.moveSpeed = this.lastDist - this.lastDist / 159.0;
        }
        this.moveSpeed = Math.max(this.moveSpeed, 0.2873);
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
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        event.x = forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz;
        event.z = forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx;
    }
}
