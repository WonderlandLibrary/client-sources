package gq.vapu.czfclient.API.Events.World;

import gq.vapu.czfclient.API.Event;
import net.minecraft.client.Minecraft;

import java.util.Random;

public class EventPreUpdate extends Event {
    public static Random rd = new Random();
    public static boolean ground;
    public double x, z;
    public double y;
    private float yaw;
    private float pitch;

    public EventPreUpdate(float yaw, float pitch, double y, boolean ground) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.y = y;
        EventPreUpdate.ground = ground;
    }

    public void setRotations(float[] rotations, boolean random) {
        if (random) {
            yaw = rotations[0] + (float) (EventPreUpdate.rd.nextBoolean() ? Math.random() : -Math.random());
            pitch = rotations[1] + (float) (EventPreUpdate.rd.nextBoolean() ? Math.random() : -Math.random());
        } else {
            yaw = rotations[0];
            pitch = rotations[1];
        }
        Minecraft.getMinecraft().thePlayer.rotationYawHead = yaw;
        Minecraft.getMinecraft().thePlayer.renderYawOffset = yaw;
        setPitch(pitch);
    }

    public float getYaw() {
        return this.yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isOnground() {
        return ground;
    }

    public void setOnground(boolean ground) {
        EventPreUpdate.ground = ground;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
