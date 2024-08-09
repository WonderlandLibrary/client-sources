/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SEntityTeleportPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityId;
    private double posX;
    private double posY;
    private double posZ;
    private byte yaw;
    private byte pitch;
    private boolean onGround;

    public SEntityTeleportPacket() {
    }

    public SEntityTeleportPacket(Entity entity2) {
        this.entityId = entity2.getEntityId();
        this.posX = entity2.getPosX();
        this.posY = entity2.getPosY();
        this.posZ = entity2.getPosZ();
        this.yaw = (byte)(entity2.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(entity2.rotationPitch * 256.0f / 360.0f);
        this.onGround = entity2.isOnGround();
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        this.posX = packetBuffer.readDouble();
        this.posY = packetBuffer.readDouble();
        this.posZ = packetBuffer.readDouble();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.onGround = packetBuffer.readBoolean();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeDouble(this.posX);
        packetBuffer.writeDouble(this.posY);
        packetBuffer.writeDouble(this.posZ);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeBoolean(this.onGround);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleEntityTeleport(this);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public double getX() {
        return this.posX;
    }

    public double getY() {
        return this.posY;
    }

    public double getZ() {
        return this.posZ;
    }

    public byte getYaw() {
        return this.yaw;
    }

    public byte getPitch() {
        return this.pitch;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

