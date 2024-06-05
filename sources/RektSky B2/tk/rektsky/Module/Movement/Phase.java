package tk.rektsky.Module.Movement;

import tk.rektsky.Module.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import tk.rektsky.Event.*;
import tk.rektsky.Utils.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.network.play.server.*;

public class Phase extends Module
{
    AxisAlignedBB bb;
    
    public Phase() {
        super("Phase", "Walk through 1 block wall", Category.MOVEMENT);
    }
    
    @Override
    public void onEnable() {
    }
    
    public void yClip(final float d) {
        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, d, this.mc.thePlayer.posZ);
    }
    
    public void teleportUp(final float d) {
        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + d, this.mc.thePlayer.posZ);
    }
    
    public void teleportForward(final float d) {
        final double playerYaw = Math.toRadians(this.mc.thePlayer.rotationYaw);
        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + d * -Math.sin(playerYaw), this.mc.thePlayer.posY, this.mc.thePlayer.posZ + d * Math.cos(playerYaw));
    }
    
    public void teleportForwardPacket(final float d) {
        final double playerYaw = Math.toRadians(this.mc.thePlayer.rotationYaw);
        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX + d * -Math.sin(playerYaw), this.mc.thePlayer.posY, this.mc.thePlayer.posZ + d * Math.cos(playerYaw), true));
    }
    
    public void teleportUpPacket(final float d) {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + d, this.mc.thePlayer.posZ, true));
    }
    
    public void hClip(final Float f) {
        final Double playerYaw = Math.toRadians(this.mc.thePlayer.rotationYaw);
        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX - f * Math.sin(playerYaw), this.mc.thePlayer.posY, this.mc.thePlayer.posZ + f * Math.cos(playerYaw));
    }
    
    public void vClip(final Float f) {
        this.mc.thePlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + f, this.mc.thePlayer.posZ);
    }
    
    public void hClip2(final Float f) {
        final Double playerYaw = Math.toRadians(this.mc.thePlayer.rotationYaw);
        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX - f * Math.sin(playerYaw), this.mc.thePlayer.posY, this.mc.thePlayer.posZ + f * Math.cos(playerYaw), true));
    }
    
    public void vClip2(final Float f) {
        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + f, this.mc.thePlayer.posZ, true));
    }
    
    public void setSpeed(final Float _speed) {
        final Double playerYaw = Math.toRadians(this.mc.thePlayer.rotationYaw);
        this.mc.thePlayer.motionX = _speed * -Math.sin(playerYaw);
        this.mc.thePlayer.motionZ = _speed * Math.cos(playerYaw);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof WorldTickEvent) {
            if (this.enabledTicks < 0 || !this.mc.thePlayer.isCollidedHorizontally || (this.mc.thePlayer.isEntityInsideOpaqueBlock() && !this.mc.thePlayer.isSneaking())) {
                this.mc.timer.timerSpeed = 1.0f;
                return;
            }
            this.mc.timer.timerSpeed = 0.3f;
            this.teleportForwardPacket(2.0f);
            this.mc.thePlayer.setPosition(this.mc.thePlayer.posX - Math.sin(MovementUtil.getDirection()), this.mc.thePlayer.posY, this.mc.thePlayer.posZ + Math.cos(MovementUtil.getDirection()));
        }
        if (event instanceof PacketSentEvent) {
            final double yaw = Math.toRadians(this.mc.thePlayer.rotationYaw);
            if (((PacketSentEvent)event).getPacket() instanceof C03PacketPlayer) {
                ((C03PacketPlayer)((PacketSentEvent)event).getPacket()).x -= Math.sin(yaw) * 1.0E-8;
                ((C03PacketPlayer)((PacketSentEvent)event).getPacket()).z += Math.cos(yaw) * 1.0E-8;
            }
        }
        if (event instanceof PacketReceiveEvent && ((PacketReceiveEvent)event).getPacket() instanceof S12PacketEntityVelocity) {
            ((PacketReceiveEvent)event).setCanceled(true);
        }
    }
    
    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
    }
}
