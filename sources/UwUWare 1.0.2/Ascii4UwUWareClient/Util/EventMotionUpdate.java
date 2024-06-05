package Ascii4UwUWareClient.Util;

import net.minecraft.client.Minecraft;

public class EventMotionUpdate { double x, y, z;
    float yaw, pitch, lastYaw, lastPitch;
    boolean onGround;
    public EventMotionUpdate(double x, double y, double z, float yaw, float pitch, float lastYaw, float lastPitch, boolean onGround){
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.lastYaw = lastYaw;
        this.lastPitch = lastPitch;
        this.onGround = onGround;

    }



    public boolean onGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;

        Minecraft.getMinecraft().thePlayer.rotationYawHead = yaw;
        Minecraft.getMinecraft().thePlayer.renderYawOffset = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        Minecraft.getMinecraft().thePlayer.rotationPitchHead = pitch;
    }

    public float getLastYaw() {
        return lastYaw;
    }

    public void setLastYaw(float lastYaw) {
        this.lastYaw = lastYaw;
    }

    public float getLastPitch() {
        return lastPitch;
    }

    public void setLastPitch(float lastPitch) {
        this.lastPitch = lastPitch;
    }


}

