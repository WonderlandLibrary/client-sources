package me.mees.remix.modules.movement.speed;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import me.satisfactory.base.utils.*;
import pw.stamina.causam.scan.method.model.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.util.*;

public class Lowhop extends Mode<Speed>
{
    private int stage;
    private double moveSpeed;
    private double lastDist;
    
    public Lowhop(final Speed parent) {
        super(parent, "Lowhop");
    }
    
    @Override
    public void onEnable() {
        if (this.mc.thePlayer != null) {
            this.moveSpeed = MiscellaneousUtil.getBaseMoveSpeed();
        }
        this.lastDist = 0.0;
        this.stage = 1;
        super.onEnable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate e) {
        final double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
        final double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }
    
    @Subscriber
    public void eventMove(final EventMove event) {
        if (!Base.INSTANCE.getModuleManager().getModByName("Flight").isEnabled()) {
            if (this.mc.thePlayer.moveForward == 0.0f && this.mc.thePlayer.moveStrafing == 0.0f) {
                this.moveSpeed = MiscellaneousUtil.getBaseMoveSpeed();
            }
            if (((Speed)this.parent).round(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3) == ((Speed)this.parent).round(0.4, 3)) {
                event.y = 0.31;
                final Timer timer = this.mc.timer;
                Timer.timerSpeed = 2.0f;
            }
            else if (((Speed)this.parent).round(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3) == ((Speed)this.parent).round(0.71, 3)) {
                event.y = 0.04;
            }
            else if (((Speed)this.parent).round(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3) == ((Speed)this.parent).round(0.75, 3)) {
                event.y = -0.2;
            }
            List collidingList = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, -0.56, 0.0));
            if (collidingList.size() > 0 && ((Speed)this.parent).round(this.mc.thePlayer.posY - (int)this.mc.thePlayer.posY, 3) == ((Speed)this.parent).round(0.55, 3)) {
                event.y = -0.13;
            }
            if (this.stage == 1 && this.mc.thePlayer.isCollidedVertically && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
                this.moveSpeed = 1.89 * MiscellaneousUtil.getBaseMoveSpeed() + 0.05;
            }
            if (this.stage == 2 && this.mc.thePlayer.isCollidedVertically && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
                event.y = 0.4;
                final Timer timer2 = this.mc.timer;
                Timer.timerSpeed = 3.0f;
                this.moveSpeed = MiscellaneousUtil.getBaseMoveSpeed() * 1.8;
            }
            else if (this.stage == 3) {
                final Timer timer3 = this.mc.timer;
                Timer.timerSpeed = 1.0f;
                final double difference = 0.66 * (this.lastDist - MiscellaneousUtil.getBaseMoveSpeed());
                this.moveSpeed = this.lastDist - difference;
            }
            else {
                collidingList = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0));
                if ((collidingList.size() > 0 || this.mc.thePlayer.isCollidedVertically) && this.stage > 0) {
                    if (1.35 * MiscellaneousUtil.getBaseMoveSpeed() - 0.1 > this.moveSpeed) {
                        this.stage = 0;
                    }
                    else {
                        this.stage = ((this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) ? 1 : 0);
                    }
                }
                this.moveSpeed = this.lastDist - this.lastDist / 159.0;
            }
            if (this.stage > 8) {
                this.moveSpeed = MiscellaneousUtil.getBaseMoveSpeed();
            }
            this.moveSpeed = Math.max(this.moveSpeed, MiscellaneousUtil.getBaseMoveSpeed());
            if (this.stage > 0) {
                this.setMoveSpeed(event, this.moveSpeed);
            }
            if (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) {
                ++this.stage;
            }
        }
    }
    
    public void setMoveSpeed(final EventMove event, final double speed) {
        final MovementInput movementInput = this.mc.thePlayer.movementInput;
        double forward = MovementInput.moveForward;
        double strafe = MovementInput.moveStrafe;
        float yaw = this.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.x = 0.0;
            event.x = 0.0;
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.x = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            event.z = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }
}
