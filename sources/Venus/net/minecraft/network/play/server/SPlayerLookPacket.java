/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.network.play.server;

import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.network.INetHandler;
import net.minecraft.network.IPacket;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class SPlayerLookPacket
implements IPacket<IClientPlayNetHandler> {
    private double x;
    private double y;
    private double z;
    private int entityId;
    private EntityAnchorArgument.Type sourceAnchor;
    private EntityAnchorArgument.Type targetAnchor;
    private boolean isEntity;

    public SPlayerLookPacket() {
    }

    public SPlayerLookPacket(EntityAnchorArgument.Type type, double d, double d2, double d3) {
        this.sourceAnchor = type;
        this.x = d;
        this.y = d2;
        this.z = d3;
    }

    public SPlayerLookPacket(EntityAnchorArgument.Type type, Entity entity2, EntityAnchorArgument.Type type2) {
        this.sourceAnchor = type;
        this.entityId = entity2.getEntityId();
        this.targetAnchor = type2;
        Vector3d vector3d = type2.apply(entity2);
        this.x = vector3d.x;
        this.y = vector3d.y;
        this.z = vector3d.z;
        this.isEntity = true;
    }

    @Override
    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.sourceAnchor = packetBuffer.readEnumValue(EntityAnchorArgument.Type.class);
        this.x = packetBuffer.readDouble();
        this.y = packetBuffer.readDouble();
        this.z = packetBuffer.readDouble();
        if (packetBuffer.readBoolean()) {
            this.isEntity = true;
            this.entityId = packetBuffer.readVarInt();
            this.targetAnchor = packetBuffer.readEnumValue(EntityAnchorArgument.Type.class);
        }
    }

    @Override
    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeEnumValue(this.sourceAnchor);
        packetBuffer.writeDouble(this.x);
        packetBuffer.writeDouble(this.y);
        packetBuffer.writeDouble(this.z);
        packetBuffer.writeBoolean(this.isEntity);
        if (this.isEntity) {
            packetBuffer.writeVarInt(this.entityId);
            packetBuffer.writeEnumValue(this.targetAnchor);
        }
    }

    @Override
    public void processPacket(IClientPlayNetHandler iClientPlayNetHandler) {
        iClientPlayNetHandler.handlePlayerLook(this);
    }

    public EntityAnchorArgument.Type getSourceAnchor() {
        return this.sourceAnchor;
    }

    @Nullable
    public Vector3d getTargetPosition(World world) {
        if (this.isEntity) {
            Entity entity2 = world.getEntityByID(this.entityId);
            return entity2 == null ? new Vector3d(this.x, this.y, this.z) : this.targetAnchor.apply(entity2);
        }
        return new Vector3d(this.x, this.y, this.z);
    }

    @Override
    public void processPacket(INetHandler iNetHandler) {
        this.processPacket((IClientPlayNetHandler)iNetHandler);
    }
}

