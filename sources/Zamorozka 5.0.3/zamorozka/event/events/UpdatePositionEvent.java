package zamorozka.event.events;

import zamorozka.event.Event;

public final class UpdatePositionEvent extends Event {

    private final float prevYaw, prevPitch;
    private double posX, posY, posZ;
    private float yaw, pitch;
    private boolean ground;
    private boolean pre;
    private boolean rotating;

    public UpdatePositionEvent(double posX, double posY, double posZ, float yaw, float pitch, float prevYaw, float prevPitch, boolean ground) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.yaw = yaw;
        this.pitch = pitch;
        this.prevYaw = prevYaw;
        this.prevPitch = prevPitch;
        this.ground = ground;
        this.pre = true;
    }

    public boolean isRotating() {
        return rotating;
    }

    public float getPrevYaw() {
        return prevYaw;
    }

    public float getPrevPitch() {
        return prevPitch;
    }

    public boolean isPre() {
        return pre;
    }

    public void setPost() {
        this.pre = false;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        this.rotating = true;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        this.rotating = true;
    }

    public boolean isOnGround() {
        return ground;
    }

    public void setOnGround(boolean ground) {
        this.ground = ground;
    }
}
