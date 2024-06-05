package net.minecraft.network.play.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

import java.io.IOException;

public class C03PacketPlayer implements Packet<INetHandlerPlayServer> {
    public double x;
    public double z;
    public double y;
    protected float yaw;
    protected float pitch;
    public boolean onGround;
    protected boolean moving;
    protected boolean rotating;
    
    public C03PacketPlayer() {
    }

    public C03PacketPlayer(boolean isOnGround) {
        onGround = isOnGround;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(INetHandlerPlayServer handler) {
        handler.processPlayer(this);
    }

    /**
     * Reads the raw packet data from the data stream.
     */
    public void readPacketData(PacketBuffer buf) throws IOException {
        onGround = buf.readUnsignedByte() != 0;
    }

    /**
     * Writes the raw packet data to the data stream.
     */
    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeByte(onGround ? 1 : 0);
    }

    public double getPositionX() {
        return x;
    }

    public double getPositionY() {
        return y;
    }

    public double getPositionZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean getRotating() {
        return rotating;
    }

    public void setMoving(boolean isMoving) {
        moving = isMoving;
    }

    public static class C04PacketPlayerPosition extends C03PacketPlayer {
        public C04PacketPlayerPosition() {
            moving = true;
        }

        public C04PacketPlayerPosition(double posX, double posY, double posZ, boolean isOnGround) {
            x = posX;
            y = posY;
            z = posZ;
            onGround = isOnGround;
            moving = true;
        }

        public void readPacketData(PacketBuffer buf) throws IOException {
            x = buf.readDouble();
            y = buf.readDouble();
            z = buf.readDouble();
            super.readPacketData(buf);
        }

        public void writePacketData(PacketBuffer buf) throws IOException {
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
            super.writePacketData(buf);
        }
    }

    public static class C05PacketPlayerLook extends C03PacketPlayer {
        public C05PacketPlayerLook() {
            rotating = true;
        }

        public C05PacketPlayerLook(float playerYaw, float playerPitch, boolean isOnGround) {
            yaw = playerYaw;
            pitch = playerPitch;
            onGround = isOnGround;
            rotating = true;
        }

        public void readPacketData(PacketBuffer buf) throws IOException {
            yaw = buf.readFloat();
            pitch = buf.readFloat();
            super.readPacketData(buf);
        }

        public void writePacketData(PacketBuffer buf) throws IOException {
            buf.writeFloat(yaw);
            buf.writeFloat(pitch);
            super.writePacketData(buf);
        }
    }

    public static class C06PacketPlayerPosLook extends C03PacketPlayer {
        public C06PacketPlayerPosLook() {
            moving = true;
            rotating = true;
        }

        public C06PacketPlayerPosLook(double playerX, double playerY, double playerZ, float playerYaw, float playerPitch, boolean playerIsOnGround) {
            x = playerX;
            y = playerY;
            z = playerZ;
            yaw = playerYaw;
            pitch = playerPitch;
            onGround = playerIsOnGround;
            rotating = true;
            moving = true;
        }

        public void readPacketData(PacketBuffer buf) throws IOException {
            x = buf.readDouble();
            y = buf.readDouble();
            z = buf.readDouble();
            yaw = buf.readFloat();
            pitch = buf.readFloat();
            super.readPacketData(buf);
        }

        public void writePacketData(PacketBuffer buf) throws IOException {
            buf.writeDouble(x);
            buf.writeDouble(y);
            buf.writeDouble(z);
            buf.writeFloat(yaw);
            buf.writeFloat(pitch);
            super.writePacketData(buf);
        }
    }
}
