/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SPlayerPositionLookPacket
implements IPacket<IClientPlayNetHandler> {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private Set<Flags> flags;
    private int teleportId;
    public boolean dismount;

    public SPlayerPositionLookPacket() {
    }

    public SPlayerPositionLookPacket(double d, double d2, double d3, float f, float f2, Set<Flags> set, int n) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.yaw = f;
        this.pitch = f2;
        this.flags = set;
        this.teleportId = n;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.yaw = packetBuffer.readFloat();
        this.pitch = packetBuffer.readFloat();
        this.flags = Flags.unpack(packetBuffer.readUnsignedByte());
        this.teleportId = packetBuffer.readVarInt();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeFloat(this.yaw);
        packetBuffer.writeFloat(this.pitch);
        packetBuffer.writeByte(Flags.pack(this.flags));
        packetBuffer.writeVarInt(this.teleportId);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handlePlayerPosLook(this);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getYaw() {
        return this.yaw;
    }

    public float getPitch() {
        return this.pitch;
    }

    public int getTeleportId() {
        return this.teleportId;
    }

    public Set<Flags> getFlags() {
        return this.flags;
    }

    public void setX(double d) {
        this.x = d;
    }

    public void setY(double d) {
        this.y = d;
    }

    public void setZ(double d) {
        this.z = d;
    }

    public void setYaw(float f) {
        this.yaw = f;
    }

    public void setPitch(float f) {
        this.pitch = f;
    }

    public void setFlags(Set<Flags> set) {
        this.flags = set;
    }

    public void setTeleportId(int n) {
        this.teleportId = n;
    }

    public void setDismount(boolean bl) {
        this.dismount = bl;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }

    public static enum Flags {
        X(0),
        Y(1),
        Z(2),
        Y_ROT(3),
        X_ROT(4);

        private final int bit;

        private Flags(int n2) {
            this.bit = n2;
        }

        private int getMask() {
            return 1 << this.bit;
        }

        private boolean isSet(int n) {
            return (n & this.getMask()) == this.getMask();
        }

        public static Set<Flags> unpack(int n) {
            EnumSet<Flags> enumSet = EnumSet.noneOf(Flags.class);
            for (Flags flags : Flags.values()) {
                if (!flags.isSet(n)) continue;
                enumSet.add(flags);
            }
            return enumSet;
        }

        public static int pack(Set<Flags> set) {
            int n = 0;
            for (Flags flags : set) {
                n |= flags.getMask();
            }
            return n;
        }
    }
}

