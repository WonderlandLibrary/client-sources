/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SEntityPacket
implements IPacket<IClientPlayNetHandler> {
    public int entityId;
    public short posX;
    public short posY;
    public short posZ;
    protected byte yaw;
    protected byte pitch;
    protected boolean onGround;
    protected boolean rotating;
    protected boolean isMovePacket;

    public static long func_218743_a(double d) {
        return MathHelper.lfloor(d * 4096.0);
    }

    public static double func_244299_a(long l) {
        return (double)l / 4096.0;
    }

    public Vector3d func_244300_a(Vector3d vector3d) {
        double d = this.posX == 0 ? vector3d.x : SEntityPacket.func_244299_a(SEntityPacket.func_218743_a(vector3d.x) + (long)this.posX);
        double d2 = this.posY == 0 ? vector3d.y : SEntityPacket.func_244299_a(SEntityPacket.func_218743_a(vector3d.y) + (long)this.posY);
        double d3 = this.posZ == 0 ? vector3d.z : SEntityPacket.func_244299_a(SEntityPacket.func_218743_a(vector3d.z) + (long)this.posZ);
        return new Vector3d(d, d2, d3);
    }

    public static Vector3d func_218744_a(long l, long l2, long l3) {
        return new Vector3d(l, l2, l3).scale(2.44140625E-4);
    }

    public SEntityPacket() {
    }

    public SEntityPacket(int n) {
        this.entityId = n;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleEntityMovement(this);
    }

    public String toString() {
        return "Entity_" + super.toString();
    }

    @Nullable
    public Entity getEntity(World world) {
        return world.getEntityByID(this.entityId);
    }

    public byte getYaw() {
        return this.yaw;
    }

    public byte getPitch() {
        return this.pitch;
    }

    public boolean isRotating() {
        return this.rotating;
    }

    public boolean func_229745_h_() {
        return this.isMovePacket;
    }

    public boolean getOnGround() {
        return this.onGround;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    public static class RelativeMovePacket
    extends SEntityPacket {
        public RelativeMovePacket() {
            this.isMovePacket = true;
        }

        public RelativeMovePacket(int n, short s, short s2, short s3, boolean bl) {
            super(n);
            this.posX = s;
            this.posY = s2;
            this.posZ = s3;
            this.onGround = bl;
            this.isMovePacket = true;
        }

        @Override
        public void readPacketData(PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.posX = packetBuffer.readShort();
            this.posY = packetBuffer.readShort();
            this.posZ = packetBuffer.readShort();
            this.onGround = packetBuffer.readBoolean();
        }

        @Override
        public void writePacketData(PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeShort(this.posX);
            packetBuffer.writeShort(this.posY);
            packetBuffer.writeShort(this.posZ);
            packetBuffer.writeBoolean(this.onGround);
        }

        @Override
        public void processPacket(INetHandler iNetHandler) {
            super.processPacket((IClientPlayNetHandler)iNetHandler);
        }
    }

    public static class MovePacket
    extends SEntityPacket {
        public MovePacket() {
            this.rotating = true;
            this.isMovePacket = true;
        }

        public MovePacket(int n, short s, short s2, short s3, byte by, byte by2, boolean bl) {
            super(n);
            this.posX = s;
            this.posY = s2;
            this.posZ = s3;
            this.yaw = by;
            this.pitch = by2;
            this.onGround = bl;
            this.rotating = true;
            this.isMovePacket = true;
        }

        @Override
        public void readPacketData(PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.posX = packetBuffer.readShort();
            this.posY = packetBuffer.readShort();
            this.posZ = packetBuffer.readShort();
            this.yaw = packetBuffer.readByte();
            this.pitch = packetBuffer.readByte();
            this.onGround = packetBuffer.readBoolean();
        }

        @Override
        public void writePacketData(PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeShort(this.posX);
            packetBuffer.writeShort(this.posY);
            packetBuffer.writeShort(this.posZ);
            packetBuffer.writeByte(this.yaw);
            packetBuffer.writeByte(this.pitch);
            packetBuffer.writeBoolean(this.onGround);
        }

        @Override
        public void processPacket(INetHandler iNetHandler) {
            super.processPacket((IClientPlayNetHandler)iNetHandler);
        }
    }

    public static class LookPacket
    extends SEntityPacket {
        public LookPacket() {
            this.rotating = true;
        }

        public LookPacket(int n, byte by, byte by2, boolean bl) {
            super(n);
            this.yaw = by;
            this.pitch = by2;
            this.rotating = true;
            this.onGround = bl;
        }

        @Override
        public void readPacketData(PacketBuffer packetBuffer) throws IOException {
            super.readPacketData(packetBuffer);
            this.yaw = packetBuffer.readByte();
            this.pitch = packetBuffer.readByte();
            this.onGround = packetBuffer.readBoolean();
        }

        @Override
        public void writePacketData(PacketBuffer packetBuffer) throws IOException {
            super.writePacketData(packetBuffer);
            packetBuffer.writeByte(this.yaw);
            packetBuffer.writeByte(this.pitch);
            packetBuffer.writeBoolean(this.onGround);
        }

        @Override
        public void processPacket(INetHandler iNetHandler) {
            super.processPacket((IClientPlayNetHandler)iNetHandler);
        }
    }
}

