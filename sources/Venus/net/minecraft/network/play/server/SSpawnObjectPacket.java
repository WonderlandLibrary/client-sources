/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;

public class SSpawnObjectPacket
implements IPacket<IClientPlayNetHandler> {
    private int entityId;
    private UUID uniqueId;
    private double x;
    private double y;
    private double z;
    private int speedX;
    private int speedY;
    private int speedZ;
    private int pitch;
    private int yaw;
    private EntityType<?> type;
    private int data;

    public SSpawnObjectPacket() {
    }

    public SSpawnObjectPacket(int n, UUID uUID, double d, double d2, double d3, float f, float f2, EntityType<?> entityType, int n2, Vector3d vector3d) {
        this.entityId = n;
        this.uniqueId = uUID;
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.pitch = MathHelper.floor(f * 256.0f / 360.0f);
        this.yaw = MathHelper.floor(f2 * 256.0f / 360.0f);
        this.type = entityType;
        this.data = n2;
        this.speedX = (int)(MathHelper.clamp(vector3d.x, -3.9, 3.9) * 8000.0);
        this.speedY = (int)(MathHelper.clamp(vector3d.y, -3.9, 3.9) * 8000.0);
        this.speedZ = (int)(MathHelper.clamp(vector3d.z, -3.9, 3.9) * 8000.0);
    }

    public SSpawnObjectPacket(Entity entity2) {
        this(entity2, 0);
    }

    public SSpawnObjectPacket(Entity entity2, int n) {
        this(entity2.getEntityId(), entity2.getUniqueID(), entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), entity2.rotationPitch, entity2.rotationYaw, entity2.getType(), n, entity2.getMotion());
    }

    public SSpawnObjectPacket(Entity entity2, EntityType<?> entityType, int n, BlockPos blockPos) {
        this(entity2.getEntityId(), entity2.getUniqueID(), blockPos.getX(), blockPos.getY(), blockPos.getZ(), entity2.rotationPitch, entity2.rotationYaw, entityType, n, entity2.getMotion());
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.entityId = packetBuffer.readVarInt();
        this.uniqueId = packetBuffer.readUniqueId();
        this.type = Registry.ENTITY_TYPE.getByValue(packetBuffer.readVarInt());
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        this.pitch = packetBuffer.readByte();
        this.yaw = packetBuffer.readByte();
        this.data = packetBuffer.readInt();
        this.speedX = packetBuffer.readShort();
        this.speedY = packetBuffer.readShort();
        this.speedZ = packetBuffer.readShort();
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarInt(this.entityId);
        packetBuffer.writeUniqueId(this.uniqueId);
        packetBuffer.writeVarInt(Registry.ENTITY_TYPE.getId(this.type));
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeByte(this.pitch);
        packetBuffer.writeByte(this.yaw);
        packetBuffer.writeInt(this.data);
        packetBuffer.writeShort(this.speedX);
        packetBuffer.writeShort(this.speedY);
        packetBuffer.writeShort(this.speedZ);
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handleSpawnObject(this);
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

    public double func_218693_g() {
        return (double)this.speedX / 8000.0;
    }

    public double func_218695_h() {
        return (double)this.speedY / 8000.0;
    }

    public double func_218692_i() {
        return (double)this.speedZ / 8000.0;
    }

    public int getPitch() {
        return this.pitch;
    }

    public int getYaw() {
        return this.yaw;
    }

    public EntityType<?> getType() {
        return this.type;
    }

    public int getData() {
        return this.data;
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

