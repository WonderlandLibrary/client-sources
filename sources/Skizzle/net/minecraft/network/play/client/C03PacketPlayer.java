/*
 * Decompiled with CFR 0.150.
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;

public class C03PacketPlayer
implements Packet {
    public double x;
    public double y;
    public double z;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public boolean moving;
    public boolean rotating;
    private static final String __OBFID = "CL_00001360";

    public C03PacketPlayer() {
    }

    public C03PacketPlayer(boolean p_i45256_1_) {
        this.onGround = p_i45256_1_;
    }

    public void processPacket(INetHandlerPlayServer handler) {
        handler.processPlayer(this);
    }

    @Override
    public void readPacketData(PacketBuffer data) throws IOException {
        this.onGround = data.readUnsignedByte() != 0;
    }

    @Override
    public void writePacketData(PacketBuffer data) throws IOException {
        data.writeByte(this.onGround ? 1 : 0);
    }

    public double getPositionX() {
        return this.x;
    }

    public double getPositionY() {
        return this.y;
    }

    public double getPositionZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean func_149465_i() {
        return this.onGround;
    }

    public boolean func_149466_j() {
        return this.moving;
    }

    public boolean getRotating() {
        return this.rotating;
    }

    public void func_149469_a(boolean p_149469_1_) {
        this.moving = p_149469_1_;
    }

    @Override
    public void processPacket(INetHandler handler) {
        this.processPacket((INetHandlerPlayServer)handler);
    }

    public static class C04PacketPlayerPosition
    extends C03PacketPlayer {
        private static final String __OBFID = "CL_00001361";

        public C04PacketPlayerPosition() {
            this.moving = true;
        }

        public C04PacketPlayerPosition(double x, double y, double z, boolean onGround) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.onGround = onGround;
            this.moving = true;
        }

        @Override
        public void readPacketData(PacketBuffer data) throws IOException {
            this.x = data.readDouble();
            this.y = data.readDouble();
            this.z = data.readDouble();
            super.readPacketData(data);
        }

        @Override
        public void writePacketData(PacketBuffer data) throws IOException {
            data.writeDouble(this.x);
            data.writeDouble(this.y);
            data.writeDouble(this.z);
            super.writePacketData(data);
        }

        @Override
        public void processPacket(INetHandler handler) {
            super.processPacket((INetHandlerPlayServer)handler);
        }
    }

    public static class C05PacketPlayerLook
    extends C03PacketPlayer {
        private static final String __OBFID = "CL_00001363";

        public C05PacketPlayerLook() {
            this.rotating = true;
        }

        public C05PacketPlayerLook(float p_i45255_1_, float p_i45255_2_, boolean p_i45255_3_) {
            this.yaw = p_i45255_1_;
            this.pitch = p_i45255_2_;
            this.onGround = p_i45255_3_;
            this.rotating = true;
        }

        @Override
        public void readPacketData(PacketBuffer data) throws IOException {
            this.yaw = data.readFloat();
            this.pitch = data.readFloat();
            super.readPacketData(data);
        }

        @Override
        public void writePacketData(PacketBuffer data) throws IOException {
            data.writeFloat(this.yaw);
            data.writeFloat(this.pitch);
            super.writePacketData(data);
        }

        @Override
        public void processPacket(INetHandler handler) {
            super.processPacket((INetHandlerPlayServer)handler);
        }
    }

    public static class C06PacketPlayerPosLook
    extends C03PacketPlayer {
        private static final String __OBFID = "CL_00001362";

        public C06PacketPlayerPosLook() {
            this.moving = true;
            this.rotating = true;
        }

        public C06PacketPlayerPosLook(double x, double y, double z, float yaw, float pitch, boolean onGround) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.yaw = yaw;
            this.pitch = pitch;
            this.onGround = onGround;
            this.rotating = true;
            this.moving = true;
        }

        @Override
        public void readPacketData(PacketBuffer data) throws IOException {
            this.x = data.readDouble();
            this.y = data.readDouble();
            this.z = data.readDouble();
            this.yaw = data.readFloat();
            this.pitch = data.readFloat();
            super.readPacketData(data);
        }

        @Override
        public void writePacketData(PacketBuffer data) throws IOException {
            data.writeDouble(this.x);
            data.writeDouble(this.y);
            data.writeDouble(this.z);
            data.writeFloat(this.yaw);
            data.writeFloat(this.pitch);
            super.writePacketData(data);
        }

        @Override
        public void processPacket(INetHandler handler) {
            super.processPacket((INetHandlerPlayServer)handler);
        }
    }
}

