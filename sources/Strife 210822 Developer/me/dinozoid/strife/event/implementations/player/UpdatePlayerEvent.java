package me.dinozoid.strife.event.implementations.player;

import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.event.StateEvent;

public class UpdatePlayerEvent extends StateEvent {

    private final float prevYaw, prevPitch;
    private final double prevPosX, prevPosY, prevPosZ;
    private double posX, posY, posZ;
    private float yaw, pitch;
    private boolean ground;
    private boolean rotating;

    public UpdatePlayerEvent(EventState state, float prevYaw, float prevPitch,
                             double prevPosX, double prevPosY, double prevPosZ,
                             double posX, double posY, double posZ,
                             float yaw, float pitch,
                             boolean ground) {
        super(state);
        this.prevYaw = prevYaw;
        this.prevPitch = prevPitch;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.prevPosX = prevPosX;
        this.prevPosY = prevPosY;
        this.prevPosZ = prevPosZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
    }

    public boolean moved() {
        final double xDiff = prevPosX - posX;
        final double yDiff = prevPosY - posY;
        final double zDiff = prevPosZ - posZ;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff) > 1.0E-5D;
    }

    public void yaw(float yaw) {
        rotating = this.yaw - yaw != 0.0F;
        this.yaw = yaw;
    }

    public void pitch(float pitch) {
        rotating = this.pitch - pitch != 0.0F;
        this.pitch = pitch;
    }

    public void posX(double posX) {
        this.posX = posX;
    }

    public void posY(double posY) {
        this.posY = posY;
    }

    public void posZ(double posZ) {
        this.posZ = posZ;
    }

    public void ground(boolean ground) {
        this.ground = ground;
    }

    public float prevYaw() {
        return prevYaw;
    }

    public float prevPitch() {
        return prevPitch;
    }

    public double prevPosX() {
        return prevPosX;
    }

    public double prevPosY() {
        return prevPosY;
    }

    public double prevPosZ() {
        return prevPosZ;
    }

    public double posX() {
        return posX;
    }

    public double posY() {
        return posY;
    }

    public double posZ() {
        return posZ;
    }

    public float yaw() {
        return yaw;
    }

    public float pitch() {
        return pitch;
    }

    public boolean ground() {
        return ground;
    }

    public boolean rotating() {
        return rotating;
    }
}
