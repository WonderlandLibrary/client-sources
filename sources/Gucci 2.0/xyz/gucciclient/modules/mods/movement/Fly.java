package xyz.gucciclient.modules.mods.movement;

import xyz.gucciclient.modules.*;
import xyz.gucciclient.values.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.entity.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Fly extends Module
{
    private double maxPosY;
    private NumberValue speed;
    public double x;
    public double y;
    public double z;
    
    public Fly() {
        super("Fly", 0, Category.MOVEMENT);
        this.maxPosY = 0.0;
        this.addValue(this.speed = new NumberValue("Fly speed", 0.5, 0.1, 2.0));
    }
    
    public void EventMove(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getMotionX() {
        return this.x;
    }
    
    public double getMotionY() {
        return this.y;
    }
    
    public double getMotionZ() {
        return this.z;
    }
    
    public void setMotionX(final double motionX) {
        this.x = motionX;
    }
    
    public void setMotionY(final double motionY) {
        this.y = motionY;
    }
    
    public void setMotionZ(final double motionZ) {
        this.z = motionZ;
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.PlayerTickEvent ev3nt) throws Exception {
        double forward = this.mc.thePlayer.movementInput.moveForward;
        double strafe = this.mc.thePlayer.movementInput.moveStrafe;
        if (this.mc.thePlayer.isSneaking()) {
            final EntityPlayerSP thePlayer = this.mc.thePlayer;
            final double n = -0.4;
            thePlayer.motionY = n;
            this.y = n;
        }
        else if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
            final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
            final double n2 = 0.4;
            thePlayer2.motionY = n2;
            this.y = n2;
        }
        else {
            final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
            final double n3 = 0.0;
            thePlayer3.motionY = n3;
            this.y = n3;
        }
        float yaw = this.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            this.x = 0.0;
            if (this.mc.thePlayer.isSneaking()) {
                final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                final double n4 = -0.4;
                thePlayer4.motionY = n4;
                this.y = n4;
            }
            else if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                final EntityPlayerSP thePlayer5 = this.mc.thePlayer;
                final double n5 = 0.4;
                thePlayer5.motionY = n5;
                this.y = n5;
            }
            else {
                final EntityPlayerSP thePlayer6 = this.mc.thePlayer;
                final double n6 = 0.0;
                thePlayer6.motionY = n6;
                this.y = n6;
            }
            this.z = 0.0;
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
            this.x = forward * this.speed.getValue() * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * this.speed.getValue() * Math.sin(Math.toRadians(yaw + 90.0f));
            this.z = forward * this.speed.getValue() * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * this.speed.getValue() * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.maxPosY = this.mc.thePlayer.posY;
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.thePlayer.capabilities.isFlying = false;
    }
}
