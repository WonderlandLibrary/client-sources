package me.mees.remix.modules.movement.flight;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.movement.*;
import net.minecraft.util.*;
import me.satisfactory.base.events.*;
import pw.stamina.causam.scan.method.model.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class GuardianFast extends Mode<Flight>
{
    public double speed;
    
    public GuardianFast(final Flight parent) {
        super(parent, "GuardianFast");
    }
    
    @Override
    public void onDisable() {
        final Timer timer = this.mc.timer;
        Timer.timerSpeed = 1.0f;
        this.mc.thePlayer.motionX = 0.0;
        this.mc.thePlayer.motionZ = 0.0;
        super.onDisable();
    }
    
    @Subscriber
    public void onMotion(final EventMotion event) {
        event.onGround = true;
    }
    
    @Subscriber
    public void onUpdate(final EventMotion event) {
        this.speed = ((Flight)this.parent).findSettingByName("Guardian Speed").doubleValue();
        if (this.mc.gameSettings.keyBindJump.pressed) {
            this.mc.thePlayer.motionY = 2.0;
        }
        if (this.mc.gameSettings.keyBindSneak.pressed) {
            this.mc.thePlayer.motionY = -8.0;
        }
        if (this.mc.thePlayer.isMoving() && !this.mc.gameSettings.keyBindSneak.isPressed() && !this.mc.gameSettings.keyBindJump.isPressed()) {
            if (this.mc.thePlayer.motionY <= -0.45500001311302185) {
                this.mc.thePlayer.motionY = 0.45500001311302185;
                this.mc.thePlayer.setSpeed(this.speed);
            }
            else {
                this.mc.thePlayer.setSpeed((float)Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ));
                if (this.mc.thePlayer.ticksExisted % 40 == 0) {
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1111.0, this.mc.thePlayer.posZ, false));
                }
            }
        }
        else {
            if (this.mc.thePlayer.motionY <= -0.45500001311302185) {
                this.mc.thePlayer.motionY = 0.45500001311302185;
            }
            if (this.mc.gameSettings.keyBindSneak.pressed) {
                this.mc.thePlayer.motionY = -1.0;
            }
        }
    }
    
    public float getDirection() {
        float yaw = this.mc.thePlayer.rotationYawHead;
        final float forward = this.mc.thePlayer.moveForward;
        final float strafe = this.mc.thePlayer.moveStrafing;
        yaw += ((forward < 0.0f) ? 180 : 0);
        if (strafe < 0.0f) {
            yaw += ((forward == 0.0f) ? 90 : ((forward < 0.0f) ? -45 : 45));
        }
        if (strafe > 0.0f) {
            yaw -= ((forward == 0.0f) ? 90 : ((forward < 0.0f) ? -45 : 45));
        }
        return yaw * 0.017453292f;
    }
}
