package client.metaware.impl.event.impl.player;


import client.metaware.impl.event.Event;

public class UpdatePlayerEvent extends Event {

    private EventState state;
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
        this.state = state;
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

    public void setYaw(float yaw) {
        rotating = this.yaw - yaw != 0.0F;
        this.yaw = yaw;
    }

    public void setPitch(float pitch) {
        rotating = this.pitch - pitch != 0.0F;
        this.pitch = pitch;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }

    public void setOnGround(boolean ground) {
        this.ground = ground;
    }

    public boolean isPost(){
        return this.state == EventState.POST;
    }

    public boolean isPre(){
        return this.state == EventState.PRE;
    }

    public float getPrevYaw() {
        return prevYaw;
    }

    public float getPrevPitch() {
        return prevPitch;
    }

    public double getPrevPosX() {
        return prevPosX;
    }

    public double getPrevPosY() {
        return prevPosY;
    }

    public double getPrevPosZ() {
        return prevPosZ;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean isOnGround() {
        return ground;
    }

    public boolean isRotating() {
        return rotating;
    }

    public EventState getState() {
        return this.state;
    }

    public void setState(EventState state) {
        this.state = state;
    }

    public enum EventState{
        PRE, POST
    }
}
