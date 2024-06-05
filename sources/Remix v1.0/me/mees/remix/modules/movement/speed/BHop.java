package me.mees.remix.modules.movement.speed;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import net.minecraft.util.*;
import me.satisfactory.base.utils.*;
import net.minecraft.entity.*;
import java.util.*;
import pw.stamina.causam.scan.method.model.*;
import me.satisfactory.base.events.*;

public class BHop extends Mode<Speed>
{
    public static int stage;
    private double moveSpeed;
    private double lastDist;
    
    public BHop(final Speed parent) {
        super(parent, "BHop");
    }
    
    public void setMoveSpeed(final EventMove event, final double speed) {
        final MovementInput movementInput = this.mc.thePlayer.movementInput;
        double forward = MovementInput.moveForward;
        final MovementInput movementInput2 = this.mc.thePlayer.movementInput;
        double strafe = MovementInput.moveStrafe;
        float yaw = this.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
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
            event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
    
    @Override
    public void onEnable() {
        if (this.mc.thePlayer != null) {
            this.moveSpeed = MiscellaneousUtil.getBaseMoveSpeed();
        }
        this.lastDist = 0.0;
        BHop.stage = 1;
    }
    
    @Subscriber
    public void onMove(final EventMove event) {
        if (this.mc.thePlayer.moveForward == 0.0f && this.mc.thePlayer.moveStrafing == 0.0f) {
            this.moveSpeed = MiscellaneousUtil.getBaseMoveSpeed();
        }
        if (BHop.stage == 1 && this.mc.thePlayer.isCollidedVertically && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
            this.moveSpeed = MiscellaneousUtil.getBaseMoveSpeed() - 0.01;
        }
        else if (BHop.stage == 2 && this.mc.thePlayer.isCollidedVertically && (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f)) {
            event.setY(this.mc.thePlayer.motionY = 0.400634);
            this.moveSpeed *= 1.8;
        }
        else if (BHop.stage == 3) {
            final double difference = 0.66 * (this.lastDist - MiscellaneousUtil.getBaseMoveSpeed());
            this.moveSpeed = this.lastDist - difference;
        }
        else {
            final List collidingList = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0));
            if ((collidingList.size() > 0 || this.mc.thePlayer.isCollidedVertically) && BHop.stage > 0) {
                if (1.35 * MiscellaneousUtil.getBaseMoveSpeed() - 0.01 > this.moveSpeed) {
                    BHop.stage = 0;
                }
                else {
                    BHop.stage = ((this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) ? 1 : 0);
                }
            }
        }
        this.moveSpeed = Math.max(this.moveSpeed, MiscellaneousUtil.getBaseMoveSpeed());
        if (BHop.stage > 0) {
            this.setMoveSpeed(event, this.moveSpeed);
        }
        if (this.mc.thePlayer.moveForward != 0.0f || this.mc.thePlayer.moveStrafing != 0.0f) {
            ++BHop.stage;
        }
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate event) {
        final double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
        final double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }
}
