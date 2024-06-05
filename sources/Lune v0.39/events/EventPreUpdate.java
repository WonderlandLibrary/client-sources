package me.superskidder.lune.events;

import me.superskidder.lune.manager.event.Event;
import net.minecraft.client.Minecraft;

/**
 * @description: Pre
 * @author: Qian_Xia
 * @create: 2020-08-24 14:07
 **/
public class EventPreUpdate extends Event {
    private float yaw;
    private float pitch;
    public double y;
    private boolean ground;
    private boolean cancelled;

    public EventPreUpdate(float yaw, float pitch, double y, boolean ground) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        this.ground = ground;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        Minecraft mc = Minecraft.getMinecraft();
        mc.thePlayer.rotationYawHead = yaw;
        mc.thePlayer.renderYawOffset = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        Minecraft mc = Minecraft.getMinecraft();
        mc.thePlayer.rotationPitchHead = pitch;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isGround() {
        return ground;
    }

    public void setGround(boolean ground) {
        this.ground = ground;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
