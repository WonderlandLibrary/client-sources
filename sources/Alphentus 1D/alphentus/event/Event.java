package alphentus.event;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

/**
 * @author avox
 * @since on 29/07/2020.
 */
public class Event extends EventCancellable {

    private final Type type;
    private double x, y, z;
    private float yaw, pitch;
    private boolean onGround;
    private float partialTicks;
    private Packet packet;
    private int key;

    private float strafe;
    private float forward;
    private float friction;

    public Event(Type type, int key) {
        this.type = type;
        this.key = key;
    }

    public Event(Type type, float strafe, float forward, float friction, float yaw) {
        this.type = type;
        this.strafe = strafe;
        this.forward = forward;
        this.friction = friction;
        this.yaw = yaw;
    }

    public Event(Type type, float yaw, float pitch, double x, double y, double z, boolean onGround) {
        this.type = type;
        this.yaw = yaw;
        this.pitch = pitch;
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = onGround;
    }

    public Event(Type type, double x, double y, double z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Event(Type type, float partialTicks) {
        this.type = type;
        this.partialTicks = partialTicks;
    }

    public Event(Type type, Packet packet) {
        this.type = type;
        this.packet = packet;
    }

    public Event(Type type) {
        this.type = type;
    }

    /*
     * Getters and Setters
     */

    public void setKey(int key) {
        this.key = key;
    }

    public float getStrafe() {
        return strafe;
    }

    public void setStrafe(float strafe) {
        this.strafe = strafe;
    }

    public float getForward() {
        return forward;
    }

    public void setForward(float forward) {
        this.forward = forward;
    }

    public float getFriction() {
        return friction;
    }

    public void setFriction(float friction) {
        this.friction = friction;
    }

    public Packet getPacket() {
        return packet;
    }

    public Type getType() {
        return type;
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
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public void setPartialTicks(float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public int getKey() {
        return key;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
