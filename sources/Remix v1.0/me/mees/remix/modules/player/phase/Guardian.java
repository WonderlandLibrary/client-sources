package me.mees.remix.modules.player.phase;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.player.*;
import net.minecraft.util.*;
import me.satisfactory.base.events.*;
import me.satisfactory.base.utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import pw.stamina.causam.scan.method.model.*;

public class Guardian extends Mode<Phase>
{
    int counter;
    
    public Guardian(final Phase parent) {
        super(parent, "Guardian");
        this.counter = 0;
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
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        final Timer timer = this.mc.timer;
        Timer.timerSpeed = 1.0f;
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        this.mc.thePlayer.hurtTime = 5;
        final Timer timer = this.mc.timer;
        Timer.timerSpeed = 0.3f;
        if (this.mc.gameSettings.keyBindForward.pressed || this.mc.gameSettings.keyBindBack.pressed || this.mc.gameSettings.keyBindLeft.pressed || this.mc.gameSettings.keyBindRight.pressed) {
            this.mc.thePlayer.motionX = 0.0;
            this.mc.thePlayer.motionZ = 0.0;
            final double speed = MiscellaneousUtil.getBaseMoveSpeed();
            this.mc.thePlayer.onGround = false;
            this.mc.thePlayer.setSpeed(speed);
            this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
            final float yaw = this.mc.thePlayer.rotationYaw;
            this.mc.thePlayer.boundingBox.offsetAndUpdate(0.7 * Math.cos(Math.toRadians(yaw + 450.0f)), 0.0, 0.7 * Math.sin(Math.toRadians(yaw + 450.0f)));
        }
        else {
            this.mc.thePlayer.motionX = 0.0;
            this.mc.thePlayer.motionZ = 0.0;
        }
    }
}
