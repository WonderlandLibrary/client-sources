/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;

public class SSpawnMobPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityId;
    private UUID uniqueId;
    private int type;
    private double x;
    private double y;
    private double z;
    private int velocityX;
    private int velocityY;
    private int velocityZ;
    private byte yaw;
    private byte pitch;
    private byte headPitch;

    public SSpawnMobPacket() {
    }

    public SSpawnMobPacket(LivingEntity livingEntity) {
        this.entityId = livingEntity.getEntityId();
        this.uniqueId = livingEntity.getUniqueID();
        this.type = Registry.ENTITY_TYPE.getId(livingEntity.getType());
        this.x = livingEntity.getPosX();
        this.y = livingEntity.getPosY();
        this.z = livingEntity.getPosZ();
        this.yaw = (byte)(livingEntity.rotationYaw * 256.0f / 360.0f);
        this.pitch = (byte)(livingEntity.rotationPitch * 256.0f / 360.0f);
        this.headPitch = (byte)(livingEntity.rotationYawHead * 256.0f / 360.0f);
        double d = 3.9;
        Vector3d vector3d = livingEntity.getMotion();
        double d2 = MathHelper.clamp(vector3d.x, -3.9, 3.9);
        double d3 = MathHelper.clamp(vector3d.y, -3.9, 3.9);
        double d4 = MathHelper.clamp(vector3d.z, -3.9, 3.9);
        this.velocityX = (int)(d2 * 8000.0);
        this.velocityY = (int)(d3 * 8000.0);
        this.velocityZ = (int)(d4 * 8000.0);
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        this.uniqueId = packetBuffer.readUniqueId();
        this.type = packetBuffer.readVarInt();
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.yaw = packetBuffer.readByte();
        this.pitch = packetBuffer.readByte();
        this.headPitch = packetBuffer.readByte();
        this.velocityX = packetBuffer.readShort();
        this.velocityY = packetBuffer.readShort();
        this.velocityZ = packetBuffer.readShort();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeUniqueId(this.uniqueId);
        packetBuffer.writeVarInt(this.type);
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeByte(this.headPitch);
        packetBuffer.writeShort(this.velocityX);
        packetBuffer.writeShort(this.velocityY);
        packetBuffer.writeShort(this.velocityZ);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleSpawnMob(this);
    }

    public int getEntityID() {
        return this.entityId;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public int getEntityType() {
        return this.type;
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

    public int getVelocityX() {
        return this.velocityX;
    }

    public int getVelocityY() {
        return this.velocityY;
    }

    public int getVelocityZ() {
        return this.velocityZ;
    }

    public byte getYaw() {
        return this.yaw;
    }

    public byte getPitch() {
        return this.pitch;
    }

    public byte getHeadPitch() {
        return this.headPitch;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

