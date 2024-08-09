/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.client;

import java.io.IOException;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.IServerPlayNetHandler;

public class CPlayerPacket
implements IPacket<IServerPlayNetHandler> {
    protected double x;
    protected double y;
    protected double z;
    protected float yaw;
    protected float pitch;
    protected boolean onGround;
    protected boolean moving;
    protected boolean rotating;

    public CPlayerPacket() {
    }

    public CPlayerPacket(boolean bl) {
        this.onGround = bl;
    }

    @Override
    public void processPacket(IServerPlayNetHandler iServerPlayNetHandler) {
        iServerPlayNetHandler.processPlayer(this);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.onGround = packetBuffer.readUnsignedByte() != 0;
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeByte(this.onGround ? 1 : 0);
    }

    public double getX(double d) {
        return this.moving ? this.x : d;
    }

    public double getY(double d) {
        return this.moving ? this.y : d;
    }

    public double getZ(double d) {
        return this.moving ? this.z : d;
    }

    public float getYaw(float f) {
        return this.rotating ? this.yaw : f;
    }

    public float getPitch(float f) {
        return this.rotating ? this.pitch : f;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setRotation(float f, float f2) {
        this.yaw = f;
        this.pitch = f2;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IServerPlayNetHandler)iNetHandler);
    }

    public static class RotationPacket
    extends CPlayerPacket {
        public RotationPacket() {
            this.rotating = true;
        }

        public RotationPacket(float f, float f2, boolean bl) {
            this.yaw = f;
            this.pitch = f2;
            this.onGround = bl;
            this.rotating = true;
        }

        @Override
        public void readPacketData(PacketBuffer packetBuffer) throws IOException {
            this.yaw = packetBuffer.readFloat();
            this.pitch = packetBuffer.readFloat();
            super.readPacketData(packetBuffer);
        }

        @Override
        public void writePacketData(PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeFloat(this.yaw);
            packetBuffer.writeFloat(this.pitch);
            super.writePacketData(packetBuffer);
        }

        @Override
        public void processPacket(INetHandler iNetHandler) {
            super.processPacket((IServerPlayNetHandler)iNetHandler);
        }
    }

    public static class PositionRotationPacket
    extends CPlayerPacket {
        public PositionRotationPacket() {
            this.moving = true;
            this.rotating = true;
        }

        public PositionRotationPacket(double d, double d2, double d3, float f, float f2, boolean bl) {
            this.x = d;
            this.y = d2;
            this.z = d3;
            this.yaw = f;
            this.pitch = f2;
            this.onGround = bl;
            this.rotating = true;
            this.moving = true;
        }

        @Override
        public void readPacketData(PacketBuffer packetBuffer) throws IOException {
            this.x = packetBuffer.readDouble();
            this.y = packetBuffer.readDouble();
            this.z = packetBuffer.readDouble();
            this.yaw = packetBuffer.readFloat();
            this.pitch = packetBuffer.readFloat();
            super.readPacketData(packetBuffer);
        }

        @Override
        public void writePacketData(PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeDouble(this.x);
            packetBuffer.writeDouble(this.y);
            packetBuffer.writeDouble(this.z);
            packetBuffer.writeFloat(this.yaw);
            packetBuffer.writeFloat(this.pitch);
            super.writePacketData(packetBuffer);
        }

        @Override
        public void processPacket(INetHandler iNetHandler) {
            super.processPacket((IServerPlayNetHandler)iNetHandler);
        }
    }

    public static class PositionPacket
    extends CPlayerPacket {
        public PositionPacket() {
            this.moving = true;
        }

        public PositionPacket(double d, double d2, double d3, boolean bl) {
            this.x = d;
            this.y = d2;
            this.z = d3;
            this.onGround = bl;
            this.moving = true;
        }

        @Override
        public void readPacketData(PacketBuffer packetBuffer) throws IOException {
            this.x = packetBuffer.readDouble();
            this.y = packetBuffer.readDouble();
            this.z = packetBuffer.readDouble();
            super.readPacketData(packetBuffer);
        }

        @Override
        public void writePacketData(PacketBuffer packetBuffer) throws IOException {
            packetBuffer.writeDouble(this.x);
            packetBuffer.writeDouble(this.y);
            packetBuffer.writeDouble(this.z);
            super.writePacketData(packetBuffer);
        }

        @Override
        public void processPacket(INetHandler iNetHandler) {
            super.processPacket((IServerPlayNetHandler)iNetHandler);
        }
    }
}

