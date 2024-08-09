/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;

public class SSpawnPlayerPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityId;
    private UUID uniqueId;
    private double x;
    private double y;
    private double z;
    private byte yaw;
    private byte pitch;

    public SSpawnPlayerPacket() {
    }

    public SSpawnPlayerPacket(PlayerEntity playerEntity) {
        this.entityId = playerEntity.getEntityId();
        this.uniqueId = playerEntity.getGameProfile().getId();
        this.x = playerEntity.getPosX();
        this.y = playerEntity.getPosY();
        this.z = playerEntity.getPosZ();
        this.yaw = (byte)(playerEntity.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(playerEntity.rotationPitch * 256.0f / 360.0f);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        this.uniqueId = packetBuffer.readUniqueId();
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeUniqueId(this.uniqueId);
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleSpawnPlayer(this);
    }

    public int getEntityID() {
        return this.entityId;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
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

    public byte getYaw() {
        return this.yaw;
    }

    public byte getPitch() {
        return this.pitch;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

