package wtf.diablo.client.event.impl.player.motion;

import wtf.diablo.client.event.api.AbstractEvent;

/**
 * Vince this is not supposed to be called UpdateEvent. Update Event would be hooked in the onUpdate method.
 */
public final class MotionEvent extends AbstractEvent {
    private double posX, posY, posZ;
    private boolean onGround;
    private float rotationYaw, rotationPitch;

    public MotionEvent(double posX, double posY, double posZ, float rotationYaw, float rotationPitch, boolean onGround) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.rotationYaw = rotationYaw;
        this.rotationPitch = rotationPitch;
        this.onGround = onGround;
    }

    public void setX(double newPos) {
        this.posX = newPos;
    }

    public void setY(double newPos) {
        this.posY = newPos;
    }

    public void setZ(double newPos) {
        this.posZ = newPos;
    }

    public void setYaw(float newYaw) {
        this.rotationYaw = newYaw;
    }

    public void setPitch(float newPitch) {
        this.rotationPitch = newPitch;
    }

    public void setOnGround(boolean newGround) {
        this.onGround = newGround;
    }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public double getPosZ() {
        return this.posZ;
    }

    public float getRotationYaw() {
        return this.rotationYaw;
    }

    public float getRotationPitch() {
        return this.rotationPitch;
    }

    public boolean isOnGround() {
        return this.onGround;
    }
}


